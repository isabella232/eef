/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.internal.binding;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.binding.settings.ReflectiveEEFBindingSettings;
import org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.ReflectivePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotifier;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotifierImpl;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.notify.UIPropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedNotification;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedPropertyNotification;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventHandler;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesBindingHandlerImpl implements PropertiesBindingHandler, EventHandler, DefaultService {

	private EventAdmin eventAdmin;

	private EEFBindingSettingsProvider bindingSettingsProvider;
	private PropertiesEditingPolicyProvider editingPolicyProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFNotifierProvider eefNotifierProvider;
	private EEFLockPolicyFactoryProvider lockPolicyFactoryProvider;
	private EEFLockManagerProvider lockManagerProvider;

	private EventTimer eventTimer;

	private List<PropertiesEditingComponent> editingComponents;

	/**
	 * 
	 */
	public PropertiesBindingHandlerImpl() {
		editingComponents = Lists.newArrayList();
	}

	/**
	 * @param eventAdmin the eventAdmin to set
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * @param bindingSettingsProvider the bindingSettingsProvider to set
	 */
	public void setBindingSettingsProvider(EEFBindingSettingsProvider bindingSettingsProvider) {
		this.bindingSettingsProvider = bindingSettingsProvider;
	}

	/**
	 * @param editingPolicyProvider the editingPolicyProvider to set
	 */
	public void setEditingPolicyProvider(PropertiesEditingPolicyProvider editingPolicyProvider) {
		this.editingPolicyProvider = editingPolicyProvider;
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefNotifierProvider the eefNotifierProvider to set
	 */
	public void setEEFNotifierProvider(EEFNotifierProvider eefNotifierProvider) {
		this.eefNotifierProvider = eefNotifierProvider;
	}

	/**
	 * @param lockPolicyFactoryProvider the lockPolicyFactoryProvider to set
	 */
	public void setLockPolicyFactoryProvider(EEFLockPolicyFactoryProvider lockPolicyFactoryProvider) {
		this.lockPolicyFactoryProvider = lockPolicyFactoryProvider;
	}

	/**
	 * @param lockManager the lockManager to set
	 */
	public void setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
		this.lockManagerProvider = lockManagerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#getPolicyProvider()
	 */
	public PropertiesEditingPolicyProvider getPolicyProvider() {
		return editingPolicyProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#createComponent(org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext)
	 */
	public PropertiesEditingComponent createComponent(PropertiesEditingContext editingContext) {
		PropertiesEditingComponent component = null;
		EObject eObject = null;
		if (editingContext instanceof EObjectPropertiesEditingContext) {
			eObject = ((EObjectPropertiesEditingContext) editingContext).getEObject();
		} else if (editingContext instanceof ReflectivePropertiesEditingContext) {
			eObject = ((ReflectivePropertiesEditingContext) editingContext).getEObject();
		}
		if (eObject != null) {
			EMFService emfService = emfServiceProvider.getEMFService(eObject.eClass().getEPackage());
			Notifier highestNotifier = emfService.highestNotifier(eObject);
			initModelChangesNotifierIfNeeded(highestNotifier);
			if (editingContext instanceof EObjectPropertiesEditingContext) {
				EEFBindingSettings<PropertiesEditingModel> bindingSettings = bindingSettingsProvider.getBindingSettings(eObject.eClass().getEPackage());
				component = new PropertiesEditingComponentImpl(bindingSettings, eObject);
			} else if (editingContext instanceof ReflectivePropertiesEditingContext) {
				EObject eefDescription = ((ReflectivePropertiesEditingContext) editingContext).getEObject();
				if (eefDescription instanceof PropertiesEditingModel) {
					ReflectiveEEFBindingSettings<PropertiesEditingModel> bindingSettings = new ReflectiveEEFBindingSettings<PropertiesEditingModel>((PropertiesEditingModel) eefDescription);
					component = new ReflectivePropertiesEditingComponent<PropertiesEditingModel>(bindingSettings, eObject);
				} else {
					// Ok it's ugly, but I assume it's a view. In this bundle, I didn't have knowledge of eef.runtime.ui.views metamodel
					// but, I suppose that I can only use ReflectivePEContext with PEM and View from eef.runtime.ui.views metamodel.
					EObjectView view = EditingModelFactory.eINSTANCE.createEObjectView();
					view.setDefinition(eefDescription);
					ReflectiveEEFBindingSettings<View> bindingSettings = new ReflectiveEEFBindingSettings<View>(view);
					component = new ReflectivePropertiesEditingComponent<View>(bindingSettings, eObject);
				}
			}
			component.setEditingContext(editingContext);
			registerEditingComponentAsEventHandler(component);
			if (!(component instanceof ReflectivePropertiesEditingComponent)) {
				initLockPolicies(component);
			}
		} else {
			component = new NullPropertiesEditingComponent(editingContext);
		}
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void disposeComponent(PropertiesEditingComponent component) {
		unregisterEditingComponent(component);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#notifyChanged(PropertiesEditingComponent, Notification)
	 */
	public void notifyChanged(PropertiesEditingComponent editingComponent, Notification msg) {
		PropertiesEditingModel editingModel = editingComponent.getEditingModel();
		if (msg.getFeature() instanceof EStructuralFeature && editingModel != null) {
			EObject source = editingComponent.getEObject();
			EMFService service = emfServiceProvider.getEMFService(source.eClass().getEPackage());
			EStructuralFeature structuralFeature = service.mapFeature(source, (EStructuralFeature)msg.getFeature());
			EClassBinding binding = editingModel.binding(source);
			Object propertyEditor = binding.propertyEditor(source, structuralFeature, editingComponent.getEditingContext().getOptions().autowire());
			for (Object view : editingComponent.getViews()) {
				if (view != null) {
					//FIXME: inject this ??
					ViewHandlerProvider viewHandlerProvider = editingComponent.getEditingContext().getViewHandlerProvider();
					ViewHandler<?> viewHandler = viewHandlerProvider.getViewHandler(editingComponent.getDescriptorForView(view));
					switch (msg.getEventType()) {
					case Notification.SET:
						try {
							viewHandler.setValue(view, propertyEditor, msg.getNewValue());
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.UNSET:
						try {
							viewHandler.unsetValue(view, propertyEditor);						
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.ADD:
						try {
							viewHandler.addValue(view, propertyEditor, msg.getNewValue());						
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.ADD_MANY:
						try {
							viewHandler.addAllValues(view, propertyEditor, (Collection<?>) msg.getNewValue());						
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.REMOVE:
						try {
							viewHandler.removeValue(view, propertyEditor, msg.getOldValue());						
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.REMOVE_MANY:
						try {
							viewHandler.removeAllValues(view, propertyEditor, (Collection<?>) msg.getOldValue());						
						} catch (ViewHandlingException e) {
							//NOTE: Silent catch
						}
						break;
					case Notification.MOVE:
						try {
							//TODO: find the good index
							int newIndex = 0;
							viewHandler.moveValue(view, propertyEditor, msg.getNewValue(), newIndex );						
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
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public synchronized void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		if (!(editingEvent instanceof UIPropertiesEditingEvent)) {
			PropertiesEditingContext editingContext = editingComponent.getEditingContext();
			EditingContextFactoryProvider contextFactoryProvider = editingContext.getContextFactoryProvider();
			PropertiesEditingContextFactory service = contextFactoryProvider.getEditingContextFactory(editingComponent.getEObject());
			PropertiesEditingContext semanticEditingContext = service.createSemanticPropertiesEditingContext(editingContext, editingEvent);
			PropertiesEditingPolicy editingPolicy = editingPolicyProvider.getEditingPolicy(semanticEditingContext);
			if (editingEvent.delayedChanges()) {
				System.out.println("PropertiesBindingHandlerImpl.firePropertiesChanged() - delayed");
				delayedApplyingPropertiesChanged(editingComponent, editingEvent);
			} else {
				System.out.println("PropertiesBindingHandlerImpl.firePropertiesChanged() - direct");
				if (editingPolicy.validateEditing(semanticEditingContext).canPerform()) {
					System.out.println("Can performed - " + ((DomainAwarePropertiesEditingContext)semanticEditingContext.getParentContext()).getEditingDomain());
					execute(editingComponent, editingPolicy, semanticEditingContext);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#delayedApplyingPropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 * @processing
	 */
	public void delayedApplyingPropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent event) {
		getEventTimer().schedule(editingComponent, event);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#fireLockChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
	 */
	public void fireLockChanged(final PropertiesEditingComponent editingComponent, final EEFLockEvent lockEvent) {
		executeOnViews(editingComponent, new Function<Object, Void>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Function#apply(java.lang.Object)
			 */
			public Void apply(Object view) {
				lockManagerProvider.getLockManager(view).fireLockChange(editingComponent, view, lockEvent);
				return null;
			}

		});

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#execute(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy, org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public void execute(PropertiesEditingComponent editingComponent, PropertiesEditingPolicy editingPolicy, PropertiesEditingContext policyEditingContext) {
		PropertiesEditingEvent editingEvent = null;
		if (policyEditingContext instanceof SemanticPropertiesEditingContext) {
			editingEvent = ((SemanticPropertiesEditingContext) policyEditingContext).getEditingEvent();
			if (editingComponent.getEditingContext().getOptions().validateEditing()) {
				Diagnostic valueDiagnostic = validateValue(editingComponent, editingEvent);
				highlightNotificationResult(editingComponent, editingEvent, valueDiagnostic, true);
				if (valueDiagnostic.getSeverity() != Diagnostic.OK && valueDiagnostic instanceof BasicDiagnostic) {
					editingComponent.propagateEvent(new PropertiesValidationEditingEvent(editingEvent, valueDiagnostic));
					return;
				} 
			}
		}
		editingPolicy.execute(policyEditingContext);
		if (editingEvent != null) {
			editingComponent.propagateEvent(editingEvent);
			if (editingComponent.getEditingContext().getOptions().validateEditing()) {		
				Diagnostic validate = validate(editingComponent);
				highlightNotificationResult(editingComponent, editingEvent, validate, false);
				editingComponent.propagateEvent(new PropertiesValidationEditingEvent(editingEvent, validate));
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#initLockPolicies(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void initLockPolicies(PropertiesEditingComponent editingComponent) {
		List<EEFLockPolicy> result = Lists.newArrayList();
		Collection<EEFLockPolicyFactory> factories = lockPolicyFactoryProvider.getLockPolicyFactories(editingComponent.getEObject());
		for (EEFLockPolicyFactory factory : factories) {
			EEFLockPolicy lockPolicy = factory.createLockPolicy();
			if (editingComponent.enableLockPolicy(lockPolicy)) {
				result.add(lockPolicy);
			}
		}
		editingComponent.setLockPolicies(result);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler#executeOnViews(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, com.google.common.base.Function)
	 */
	public void executeOnViews(PropertiesEditingComponent editingComponent, Function<Object, Void> function) {
		for (Object view : editingComponent.getViews()) {
			function.apply(view);
		}
	}

	/**
	 * @param editingEvent
	 * @param valueDiagnostic
	 * @param notifyEditor
	 * @processing
	 */
	private final void highlightNotificationResult(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent, Diagnostic valueDiagnostic, boolean notifyEditor) {
		if (valueDiagnostic.getSeverity() != Diagnostic.OK) {
			ValidationBasedNotification notification = null;
			if (notifyEditor) {
				notification = new ValidationBasedPropertyNotification(editingEvent.getAffectedEditor(), valueDiagnostic);
			} else {
				notification = new ValidationBasedNotification(valueDiagnostic);
			}
			for (Object view : editingComponent.getViews()) {
				EEFNotifier notifier = eefNotifierProvider.getNotifier(view);
				if (notifier != null) {
					notifier.notify(view, notification);
				}
			}
		} else {
			if (notifyEditor) {
				for (Object view : editingComponent.getViews()) {
					EEFNotifier notifier = eefNotifierProvider.getNotifier(view);
					if (notifier != null) {
						notifier.clearEditorNotification(view, editingEvent.getAffectedEditor());
					}
				}
			} else {
				for (Object view : editingComponent.getViews()) {
					EEFNotifier notifier = eefNotifierProvider.getNotifier(view);
					if (notifier != null) {
						notifier.clearViewNotification(view);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#validate()
	 * @processing
	 */
	public Diagnostic validate(PropertiesEditingComponent editingComponent) {
		Diagnostic validate = Diagnostic.OK_INSTANCE;
		validate = EEFRuntime.getPlugin().getEEFValidator().validate(editingComponent.getEObject());
		return validate;
	}

	/**
	 * Validate the change described by the given event.
	 * @param editingEvent {@link PropertiesEditingEvent} notifying a view change.
	 * @return the {@link Diagnostic} of this validation.
	 * @processing
	 */
	private Diagnostic validateValue(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		Diagnostic ret = Diagnostic.OK_INSTANCE;
		EStructuralFeature feature = editingComponent.getBinding().feature(editingEvent.getAffectedEditor(), editingComponent.getEditingContext().getOptions().autowire());
		// TODO: Skipped test on EEnum to process later.
		if (editingEvent.getNewValue() != null && feature instanceof EAttribute && !(feature.getEType() instanceof EEnum)) {
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
			} catch (NullPointerException e) {
				// A suspicious error occurred (e.g. on Enum) let's go on. 
			}
		}
		return ret;
	}

	/**
	 * @return the {@link EventTimer} used to delay properties event.
	 * @processing
	 */
	private EventTimer getEventTimer() {
		if (eventTimer == null) {
			eventTimer = new EventTimer(this);
		}
		return eventTimer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.Event)
	 * @processing
	 */
	public void handleEvent(Event event) {
		if (event.getProperty("notification") instanceof Notification) {
			Notification notification = (Notification) event.getProperty("notification");
			for (PropertiesEditingComponent editingComponent : Lists.newArrayList(editingComponents)) {
				if (editingComponent.isAffectingEvent(notification)) {
					notifyChanged(editingComponent, notification);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#initModelChangesNotifierIfNeeded(org.eclipse.emf.common.notify.Notifier)
	 */
	public void initModelChangesNotifierIfNeeded(Notifier notifier) {
		Adapter existingAdapter = EcoreUtil.getExistingAdapter(notifier, ModelChangesNotifier.class);
		if (existingAdapter == null) {
			notifier.eAdapters().add(new ModelChangesNotifierImpl(eventAdmin));
		}

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#registerEditingComponentAsEventHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
		editingComponents.add(editingComponent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#unregisterEditingComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
		editingComponents.remove(editingComponent);
	}

}
