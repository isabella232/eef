/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.services.editingProvider.AbstractPropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.notify.UIPropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedNotification;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedPropertyNotification;
import org.osgi.service.event.Event;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingComponentImpl implements PropertiesEditingComponent {

	/**
	 * the job that will fire the property changed event
	 */
	private PropertiesEditingProvider editingProvider;
	private EObject source;
	private PropertiesEditingContext editingContext;
	private PropertiesEditingModel editingModel;
	private List<ViewHandler<?>> viewHandlers;
	private List<EEFLockPolicy> lockPolicies;
	private ViewChangeNotifier viewChangeNotifier;
	private List<PropertiesEditingListener> listeners;
	private EventTimer eventTimer;
	
	/**
	 * @param editingProvider {@link AbstractPropertiesEditingProvider} providing this component.
	 * @param source
	 */
	public PropertiesEditingComponentImpl(PropertiesEditingProvider editingProvider, EObject source) {
		this.editingProvider = editingProvider;
		this.source = source;
		this.listeners = Lists.newArrayList();
		this.viewHandlers = Lists.newArrayList();
		this.lockPolicies = initLockPolicies();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEObject()
	 */
	public EObject getEObject() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingContext()
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public void setEditingContext(PropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		return editingProvider.getEditingModelEnvironment();
	}

	/**
	 * @return the {@link PropertiesEditingModel} describing the Editing Forms for the given {@link EObject}.
	 */
	private PropertiesEditingModel getEditingModel() {
		if (editingModel == null) {
			editingModel = editingProvider.getEditingModel(source);
		}
		return editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBinding()
	 */
	public EClassBinding getBinding() {
		PropertiesEditingModel editingModel = getEditingModel();
		if (editingModel != null) {
			return editingModel.binding(source);
		} else {
			return null;
		}
	}
	
	/**
	 * @return the {@link EventTimer} used to delay properties event.
	 */
	private EventTimer getEventTimer() {
		if (eventTimer == null) {
			eventTimer = new EventTimer(this);
		}
		return eventTimer;
	}


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#addEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void addEditingListener(PropertiesEditingListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void removeEditingListener(PropertiesEditingListener listener) {
		listeners.remove(listener);		
	}
	
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getLockPolicies()
	 */
	public Collection<EEFLockPolicy> getLockPolicies() {
		return lockPolicies;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#notifyChanged(Notification)
	 */
	public void notifyChanged(Notification msg) {
		PropertiesEditingModel editingModel = getEditingModel();
		if (msg.getFeature() instanceof EStructuralFeature && editingModel != null) {
			EStructuralFeature structuralFeature = (EStructuralFeature)msg.getFeature();
			EClassBinding binding = editingModel.binding(source);
			Object propertyEditor = binding.propertyEditor(structuralFeature, editingContext.getOptions().autowire());
			for (ViewHandler<?> viewHandler : viewHandlers) {
				switch (msg.getEventType()) {
				case Notification.SET:
					try {
						viewHandler.setValue(propertyEditor, msg.getNewValue());						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.UNSET:
					try {
						viewHandler.unsetValue(propertyEditor);						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.ADD:
					try {
						viewHandler.addValue(propertyEditor, msg.getNewValue());						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.ADD_MANY:
					try {
						viewHandler.addAllValues(propertyEditor, (Collection<?>) msg.getNewValue());						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.REMOVE:
					try {
						viewHandler.removeValue(propertyEditor, msg.getOldValue());						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.REMOVE_MANY:
					try {
						viewHandler.removeAllValues(propertyEditor, (Collection<?>) msg.getOldValue());						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				case Notification.MOVE:
					try {
						//TODO: find the good index
						int newIndex = 0;
						viewHandler.moveValue(propertyEditor, msg.getNewValue(), newIndex );						
					} catch (ViewHandlingException e) {
						//NOTE: Silent catch
					}
					break;
				default:
					break;
				}
			}
		}			
	}


	/**
	 * {@inheritDoc}
	 * @see org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.Event)
	 */
	public void handleEvent(Event event) {
		if (event.getProperty("notification") instanceof Notification) {
			this.notifyChanged((Notification) event.getProperty("notification"));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#fireLockChanged(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
	 */
	public void fireLockChanged(final EEFLockEvent lockEvent) {
		final EEFServiceRegistry serviceRegistry = editingProvider.getServiceRegistry();
		executeOnViewHandlers(new Function<ViewHandler<?>, Void>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Function#apply(java.lang.Object)
			 */
			public Void apply(ViewHandler<?> arg0) {
				Object view = arg0.getView();
				EEFLockManager lockManager = serviceRegistry.getService(EEFLockManager.class, view);
				lockManager.fireLockChange(PropertiesEditingComponentImpl.this, view, lockEvent);
				return null;
			}

		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewChangeNotifier()
	 */
	public ViewChangeNotifier getViewChangeNotifier() {
		if (viewChangeNotifier == null) {
			viewChangeNotifier = new ViewChangeNotifier(this);
		}
		return viewChangeNotifier;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public synchronized void firePropertiesChanged(PropertiesEditingEvent editingEvent) {
		if (shouldProcess(editingEvent)) {
			if (editingEvent.delayedChanges()) {
				delayedApplyingPropertiesChanged(editingEvent);
			} else {
				directApplyingPropertyChanged(editingEvent);
			}
		}
	}

	/**
	 * Defines if an event must be processed by the current {@link PropertiesEditingComponent}.
	 * @param editingEvent {@link PropertiesEditingEvent} to evaluate.
	 * @return <code>true</code> if the event must be processed.
	 */
	protected boolean shouldProcess(PropertiesEditingEvent editingEvent) {
		if (editingEvent instanceof UIPropertiesEditingEvent) {
			return false;
		} else {
			EStructuralFeature feature = getBinding().feature(editingEvent.getAffectedEditor(), editingContext.getOptions().autowire());
			if (feature != null) {
				if (!getEObject().eClass().getEAllStructuralFeatures().contains(feature)) {
					feature = editingContext.getEMFService().mapFeature(getEObject(), feature);
				}
				if (getEObject().eClass().getEAllStructuralFeatures().contains(feature)) {
	 				Object currentValue = getEObject().eGet(feature);
					return (currentValue == null && editingEvent.getNewValue() != null)
							|| (currentValue != null && !currentValue.equals(editingEvent.getNewValue()));
				}
			}
			
		}
		return true;
	}
	
	/**
	 * Applying the model change(s) implied by an event immediately.
	 * @param editingEvent the {@link PropertiesEditingEvent} to process.
	 */
	private void directApplyingPropertyChanged(PropertiesEditingEvent editingEvent) {
		if (editingContext.getOptions().validateEditing()) {
			Diagnostic valueDiagnostic = validateValue(editingEvent);
			if ("test".equals(editingEvent.getNewValue())) {
				valueDiagnostic = new BasicDiagnostic(Diagnostic.ERROR, "", 0, "Cannot be test", null);
			}
			highlightNotificationResult(editingEvent, valueDiagnostic, true);
			if (valueDiagnostic.getSeverity() != Diagnostic.OK && valueDiagnostic instanceof BasicDiagnostic) {
				propagateEvent(new PropertiesValidationEditingEvent(editingEvent, valueDiagnostic));
				return;
			} 
		}
		PropertiesEditingPolicy editingPolicy = editingContext.getEditingPolicy(new SemanticPropertiesEditingContext(this, editingEvent));
		if (editingPolicy != null) {
				editingPolicy.execute();				
		}
		propagateEvent(editingEvent);
		if (editingContext.getOptions().validateEditing()) {		
			Diagnostic validate = validate();
			highlightNotificationResult(editingEvent, validate, false);
			propagateEvent(new PropertiesValidationEditingEvent(editingEvent, validate));
		}
	}

	protected final void highlightNotificationResult(PropertiesEditingEvent editingEvent, Diagnostic valueDiagnostic, boolean notifyEditor) {
		if (valueDiagnostic.getSeverity() != Diagnostic.OK) {
			ValidationBasedNotification notification = null;
			if (notifyEditor) {
				notification = new ValidationBasedPropertyNotification(editingEvent.getAffectedEditor(), valueDiagnostic);
			} else {
				notification = new ValidationBasedNotification(valueDiagnostic);
			}
			for (ViewHandler<?> viewHandler : viewHandlers) {
				EEFNotifier notifier = editingProvider.getServiceRegistry().getService(EEFNotifier.class, (Object)viewHandler.getView());
				if (notifier != null) {
					notifier.notify(viewHandler.getView(), notification);
				}
			}
		} else {
			if (notifyEditor) {
				for (ViewHandler<?> viewHandler : viewHandlers) {
					EEFNotifier notifier = (EEFNotifier) editingProvider.getServiceRegistry().getService(EEFNotifier.class, (Object)viewHandler.getView());
					if (notifier != null) {
						notifier.clearEditorNotification(viewHandler.getView(), editingEvent.getAffectedEditor());
					}
				}
			} else {
				for (ViewHandler<?> viewHandler : viewHandlers) {
					EEFNotifier notifier = (EEFNotifier) editingProvider.getServiceRegistry().getService(EEFNotifier.class, (Object)viewHandler.getView());
					if (notifier != null) {
						notifier.clearViewNotification(viewHandler.getView());
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#delayedApplyingPropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void delayedApplyingPropertiesChanged(PropertiesEditingEvent event) {
		getEventTimer().schedule(event);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#createViewHandler(java.lang.Object)
	 */
	public ViewHandler<?> createViewHandler(Object view) {
		ViewHandler<?> specifiedHandler = getEditingModel().viewHandler(source, view);
		if (specifiedHandler != null) {
			registerViewHandler(specifiedHandler);
			return specifiedHandler;
		} else {
			ViewHandlerProvider viewHandlerProvider = editingProvider.getViewHandlerProvider(view);
			if (viewHandlerProvider != null) {
				ViewHandler<?> handler = viewHandlerProvider.getHandler(this, view);
				if (handler != null) {
					registerViewHandler(handler);
					return handler;
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#createViewHandlers()
	 */
	public Collection<ViewHandler<?>> createViewHandlers() {
		PropertiesEditingModel editingModel = getEditingModel();
		if (editingModel != null) {
			List<ViewHandler<?>> result = Lists.newArrayList();
			for (Object view : editingModel.views(source)) {
				result.add(createViewHandler(view));
			}
			return result;
		}
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#registerViewHandler(org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler)
	 */
	public void registerViewHandler(ViewHandler<?> handler) {
		viewHandlers.add(handler);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#unregisterViewHandler(org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler)
	 */
	public void unregisterViewHandler(ViewHandler<?> handler) {
		viewHandlers.remove(handler);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#executeOnViewHandlers(com.google.common.base.Function)
	 */
	public void executeOnViewHandlers(Function<ViewHandler<?>, Void> function) {
		for (ViewHandler<?> handler : viewHandlers) {
			function.apply(handler);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 */
	private void propagateEvent(PropertiesEditingEvent event) {
		event.addHolder(this);
		for (PropertiesEditingListener listener : listeners) {
			if (!event.hold(listener))
				listener.firePropertiesChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#validate()
	 */
	public Diagnostic validate() {
		Diagnostic validate = Diagnostic.OK_INSTANCE;
		validate = EEFRuntime.getPlugin().getEEFValidator().validate(source);
		return validate;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#dispose()
	 */
	public void dispose() {
		List<ViewHandler<?>> handlers = Lists.newArrayList(viewHandlers);
		for (ViewHandler<?> handler : handlers) {
			handler.dispose();
		}
		editingProvider.disposeComponent(this);
		// Making a blank component to be sure to not reuse it!
		editingProvider = null;
		source = null;
		editingContext = null;
		editingModel = null;
		viewHandlers.clear();
		viewChangeNotifier = null;
		listeners.clear();
	}

	private List<EEFLockPolicy> initLockPolicies() {
		List<EEFLockPolicy> result = Lists.newArrayList();
		Collection<EEFLockPolicyFactory> factories = editingProvider.getServiceRegistry().getAllServices(EEFLockPolicyFactory.class, source);
		for (EEFLockPolicyFactory factory : factories) {
			//TODO: can be optimized
			EEFLockPolicy lockPolicy = factory.createLockPolicy();
			if (editingProvider.validateLockPolicy(lockPolicy)) {
				result.add(lockPolicy);
			}
		}
		return result;
	}

	/**
	 * Validate the change described by the given event.
	 * @param editingEvent {@link PropertiesEditingEvent} notifying a view change.
	 * @return the {@link Diagnostic} of this validation.
	 */
	private Diagnostic validateValue(PropertiesEditingEvent editingEvent) {
		Diagnostic ret = Diagnostic.OK_INSTANCE;
		EStructuralFeature feature = getBinding().feature(editingEvent.getAffectedEditor(), editingContext.getOptions().autowire());
		if (editingEvent.getNewValue() != null && feature instanceof EAttribute) {
			EAttribute attribute = (EAttribute)feature;
			try {
				Object newValue = editingEvent.getNewValue();
				if (newValue instanceof String) {
					newValue = EcoreUtil.createFromString(attribute.getEAttributeType(), (String)newValue);
				}
				ret = Diagnostician.INSTANCE.validate(attribute.getEAttributeType(), newValue);
			} catch (IllegalArgumentException iae) {
				ret = BasicDiagnostic.toDiagnostic(iae);
			} catch (WrappedException we) {
				ret = BasicDiagnostic.toDiagnostic(we);
			}
		}
		return ret;
	}
	
	
}
