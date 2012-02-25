/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
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
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.EditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingComponentImpl extends AdapterImpl implements PropertiesEditingComponent {

	public static final Object FIRE_PROPERTIES_CHANGED_JOB_FAMILY = new Object();
	
	/**
	 * the job that will fire the property changed event
	 */
	protected FirePropertiesChangedJob firePropertiesChangedJob;

	private PropertiesEditingContext editingContext;
	private PropertiesEditingProvider editingProvider;
	private PropertiesEditingModel editingModel;
	private List<ViewHandler<?>> viewHandlers;
	private ViewChangeNotifier viewChangeNotifier;
	private List<EditingListener> listeners;



	/**
	 * @param editingModel model defining the properties editing definition. 
	 */
	public PropertiesEditingComponentImpl(PropertiesEditingProvider editingProvider) {
		this.editingProvider = editingProvider;
		this.listeners = Lists.newArrayList();
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
	 * @return the {@link PropertiesEditingModel} describing the Editing Forms for the given {@link EObject}.
	 */
	private PropertiesEditingModel getEditingModel() {
		if (editingModel == null) {
			editingModel = editingProvider.getEditingModel((EObject) getTarget());
		}
		return editingModel;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBinding()
	 */
	public EClassBinding getBinding() {
		return getEditingModel().binding((EObject) getTarget());
	}


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#addEditingListener(org.eclipse.emf.eef.runtime.notify.EditingListener)
	 */
	public void addEditingListener(EditingListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeEditingListener(org.eclipse.emf.eef.runtime.notify.EditingListener)
	 */
	public void removeEditingListener(EditingListener listener) {
		listeners.remove(listener);		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	public boolean isAdapterForType(Object type) {
		return type == PropertiesEditingComponent.class;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void notifyChanged(Notification msg) {
		if (msg.getFeature() instanceof EStructuralFeature) {
			EStructuralFeature structuralFeature = (EStructuralFeature)msg.getFeature();
			EClassBinding binding = getEditingModel().binding((EObject) getTarget());
			Object propertyEditor = binding.propertyEditor(structuralFeature, editingContext.getOptions().autowire());
			switch (msg.getEventType()) {
			case Notification.SET:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.setValue(propertyEditor, msg.getNewValue());						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.UNSET:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.unsetValue(propertyEditor);						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.ADD:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.addValue(propertyEditor, msg.getNewValue());						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.ADD_MANY:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.addAllValues(propertyEditor, (Collection<?>) msg.getNewValue());						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.REMOVE:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.removeValue(propertyEditor, msg.getOldValue());						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.REMOVE_MANY:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						viewHandler.removeAllValues(propertyEditor, (Collection<?>) msg.getOldValue());						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			case Notification.MOVE:
				try {
					for (ViewHandler<?> viewHandler : viewHandlers) {
						//TODO: find the good index
						int newIndex = 0;
						viewHandler.moveValue(propertyEditor, msg.getNewValue(), newIndex );						
					}
				} catch (ViewHandlingException e) {
					//NOTE: Silent catch
				}
				break;
			default:
				break;
			}
		}			
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
	 * @see org.eclipse.emf.eef.runtime.notify.EditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public synchronized void firePropertiesChanged(PropertiesEditingEvent editingEvent) {
		if (editingContext.getOptions().validateEditing()) {
			Diagnostic valueDiagnostic = validateValue(editingEvent);
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
			propagateEvent(new PropertiesValidationEditingEvent(editingEvent, validate));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#delayedFirePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void delayedFirePropertiesChanged(PropertiesEditingEvent event) {
		if (getFirePropertiesChangedJob().cancel()) {
			getFirePropertiesChangedJob().setEvent(event);
			getFirePropertiesChangedJob().schedule(editingContext.getOptions().delayedFirePropertiesChangedDelay());
		} else {
			try {
				getFirePropertiesChangedJob().join();
				getFirePropertiesChangedJob().setEvent(event);
				getFirePropertiesChangedJob().schedule();
			} catch (InterruptedException e) {
				getFirePropertiesChangedJob().setEvent(null);
			}
		}
	}

	protected FirePropertiesChangedJob getFirePropertiesChangedJob() {
		if (firePropertiesChangedJob == null) {
			firePropertiesChangedJob = new FirePropertiesChangedJob("Fire properties changed...");
		}
		return firePropertiesChangedJob;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewHandlers()
	 */
	public List<ViewHandler<?>> getViewHandlers() {
		viewHandlers = new ArrayList<ViewHandler<?>>();
		ViewHandlerProvider viewHandlerProvider = editingContext.getViewHandlerProvider();
		if (viewHandlerProvider != null) {
			List<Object> associatedViews = getEditingModel().views((EObject) getTarget());
			for (Object associatedView : associatedViews) {
				ViewHandler<?> specifiedHandler = getEditingModel().viewHandler((EObject) getTarget(), associatedView);
				if (specifiedHandler != null) {
					viewHandlers.add(specifiedHandler);
				} else {
					if (viewHandlerProvider.canHandle(associatedView)) {
						ViewHandler<?> handler = viewHandlerProvider.getHandler(associatedView);
						if (handler != null) {
							viewHandlers.add(handler);
						}
					}
				}
			}
		}
		return viewHandlers;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 */
	private void propagateEvent(PropertiesEditingEvent event) {
		event.addHolder(this);
		for (EditingListener listener : listeners) {
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
		validate = EEFRuntime.getPlugin().getEEFValidator().validate((EObject) getTarget());
		return validate;
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

	protected class FirePropertiesChangedJob extends Job {

		private PropertiesEditingEvent fEvent;

		public FirePropertiesChangedJob(String name) {
			super(name);
		}

		@Override
		public boolean belongsTo(Object family) {
			return family == FIRE_PROPERTIES_CHANGED_JOB_FAMILY;
		}

		@Override
		public boolean shouldSchedule() {
			return fEvent != null;
		}

		@Override
		public boolean shouldRun() {
			return fEvent != null;
		}

		@Override
		protected void canceling() {
			super.canceling();
			fEvent = null;
		}

		public void setEvent(PropertiesEditingEvent event) {
			fEvent = event;
		}

		protected IStatus run(IProgressMonitor monitor) {
//			deactivate();
			firePropertiesChanged(fEvent);
//			activate();
			fEvent = null;
			return Status.OK_STATUS;
		}
	}

}
