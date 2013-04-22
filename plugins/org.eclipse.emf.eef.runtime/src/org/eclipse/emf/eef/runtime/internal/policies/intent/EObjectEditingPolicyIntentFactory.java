/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.intent;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectEditingPolicyIntentFactory extends AbstractEEFService<PropertiesEditingContext> implements EditingPolicyIntentFactory {

	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory#createProcessing(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyIntent createProcessing(PropertiesEditingContext editingContext) {
		EditingPolicyIntent.Builder requestBuilder = new EditingPolicyIntent.Builder();
		PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContext) editingContext).getEditingEvent();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		EStructuralFeature bindingFeature = binding.feature(editingEvent.getAffectedEditor(), editingContext.getEditingComponent().getEditingContext().getOptions().autowire());
		EObject editedObject = (EObject)editingContext.getEditingComponent().getEObject();
		EMFService emfService = emfServiceProvider.getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		EStructuralFeature feature = emfService.mapFeature(editedObject, bindingFeature);
		if (feature != null) {
			requestBuilder.setTarget(editedObject);
			requestBuilder.setFeature(feature);
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.SET:
				requestBuilder.setProcessingKind(ProcessingKind.SET);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.UNSET:
				requestBuilder.setProcessingKind(ProcessingKind.UNSET);
				break;
			case PropertiesEditingEvent.EDIT:
				requestBuilder.setProcessingKind(ProcessingKind.EDIT);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.ADD:
				requestBuilder.setProcessingKind(ProcessingKind.ADD);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			case PropertiesEditingEvent.ADD_MANY:
				requestBuilder.setProcessingKind(ProcessingKind.ADD_MANY);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;	
			case PropertiesEditingEvent.REMOVE:
				requestBuilder.setProcessingKind(ProcessingKind.REMOVE);
				requestBuilder.setValue(editingEvent.getOldValue());
				break;
			case PropertiesEditingEvent.REMOVE_MANY:
				requestBuilder.setProcessingKind(ProcessingKind.REMOVE_MANY);
				requestBuilder.setValue(editingEvent.getOldValue());
				break;
			case PropertiesEditingEvent.MOVE:
				requestBuilder.setProcessingKind(ProcessingKind.MOVE);
				requestBuilder.setOldIndex((Integer)editingEvent.getOldValue());
				requestBuilder.setNewIndex((Integer)editingEvent.getNewValue());
				break;
			default:
				requestBuilder.setProcessingKind(ProcessingKind.SET);
				requestBuilder.setValue(editingEvent.getNewValue());
				break;
			}
		}
		return requestBuilder.build();
	}

}
