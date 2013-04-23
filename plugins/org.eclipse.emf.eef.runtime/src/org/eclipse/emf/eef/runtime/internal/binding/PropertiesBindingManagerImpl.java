/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.notify.UIPropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedNotification;
import org.eclipse.emf.eef.runtime.view.notify.impl.ValidationBasedPropertyNotification;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesBindingManagerImpl extends AbstractEEFService<PropertiesEditingComponent> implements PropertiesBindingManager, DefaultService {
	
	private PropertiesEditingPolicyProvider editingPolicyProvider;
	private EEFNotifierProvider eefNotifierProvider;

	private EventTimer eventTimer;
	
	/**
	 * @param editingPolicyProvider the editingPolicyProvider to set
	 */
	public void setEditingPolicyProvider(PropertiesEditingPolicyProvider editingPolicyProvider) {
		this.editingPolicyProvider = editingPolicyProvider;
	}

	/**
	 * @param eefNotifierProvider the eefNotifierProvider to set
	 */
	public void setEEFNotifierProvider(EEFNotifierProvider eefNotifierProvider) {
		this.eefNotifierProvider = eefNotifierProvider;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingComponent element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public synchronized void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		if (!(editingEvent instanceof UIPropertiesEditingEvent)) {
			PropertiesEditingContext editingContext = editingComponent.getEditingContext();
			PropertiesEditingContextFactory service = editingContext.getServiceRegistry().getService(PropertiesEditingContextFactory.class, editingComponent.getEObject());
			PropertiesEditingContext semanticEditingContext = service.createSemanticPropertiesEditingContext(editingContext, editingEvent);
			PropertiesEditingPolicy editingPolicy = getEditingPolicy(semanticEditingContext);
			if (editingEvent.delayedChanges()) {
				delayedApplyingPropertiesChanged(editingComponent, editingEvent);
			} else {
				if (editingPolicy.validateEditing(semanticEditingContext).canPerform()) {
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
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager#execute(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy, org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
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
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext editingContext) {
		return editingPolicyProvider.getEditingPolicy(editingContext);
	}

	/**
	 * @param editingEvent
	 * @param valueDiagnostic
	 * @param notifyEditor
	 * @processing
	 */
	public final void highlightNotificationResult(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent, Diagnostic valueDiagnostic, boolean notifyEditor) {
		if (valueDiagnostic.getSeverity() != Diagnostic.OK) {
			ValidationBasedNotification notification = null;
			if (notifyEditor) {
				notification = new ValidationBasedPropertyNotification(editingEvent.getAffectedEditor(), valueDiagnostic);
			} else {
				notification = new ValidationBasedNotification(valueDiagnostic);
			}
			for (ViewHandler<?> viewHandler : editingComponent.getViewHandlers()) {
				EEFNotifier notifier = eefNotifierProvider.getNotifier(viewHandler.getView());
				if (notifier != null) {
					notifier.notify(viewHandler.getView(), notification);
				}
			}
		} else {
			if (notifyEditor) {
				for (ViewHandler<?> viewHandler : editingComponent.getViewHandlers()) {
					EEFNotifier notifier = eefNotifierProvider.getNotifier(viewHandler.getView());
					if (notifier != null) {
						notifier.clearEditorNotification(viewHandler.getView(), editingEvent.getAffectedEditor());
					}
				}
			} else {
				for (ViewHandler<?> viewHandler : editingComponent.getViewHandlers()) {
					EEFNotifier notifier = eefNotifierProvider.getNotifier(viewHandler.getView());
					if (notifier != null) {
						notifier.clearViewNotification(viewHandler.getView());
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

}
