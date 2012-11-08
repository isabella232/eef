/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.eobject;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.AbstractEditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing.ProcessingKind;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EObjectEditingPolicy extends AbstractEditingPolicyWithProcessor {

	/**
	 * @param editingContext the {@link PropertiesEditingContext} which have generated the current policy.
	 */
	public EObjectEditingPolicy(SemanticPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.AbstractEditingPolicyWithProcessor#getPolicyProcessing()
	 */
	@Override
	protected EditingPolicyProcessing getPolicyProcessing() {
		EditingPolicyProcessing processing = new EditingPolicyProcessing();
		PropertiesEditingEvent editingEvent = editingContext.getEditingEvent();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		EStructuralFeature bindingFeature = binding.feature(editingEvent.getAffectedEditor(), editingContext.getEditingComponent().getEditingContext().getOptions().autowire());
		EObject editedObject = (EObject)editingContext.getEditingComponent().getEObject();
		EMFService emfService = editingContext.getServiceRegistry().getService(EMFService.class, editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		EStructuralFeature feature = emfService.mapFeature(editedObject, bindingFeature);
		if (feature != null) {
			processing.target = editedObject;
			processing.feature = feature;
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.SET:
				processing.processingKind = ProcessingKind.SET;
				processing.value = editingEvent.getNewValue();
				break;
			case PropertiesEditingEvent.UNSET:
				processing.processingKind = ProcessingKind.UNSET;
				break;
			case PropertiesEditingEvent.ADD:
				processing.processingKind = ProcessingKind.ADD;
				processing.value = editingEvent.getNewValue();
				break;
			case PropertiesEditingEvent.ADD_MANY:
				processing.processingKind = ProcessingKind.ADD_MANY;
				processing.value = editingEvent.getNewValue();
				break;	
			case PropertiesEditingEvent.REMOVE:
				processing.processingKind = ProcessingKind.REMOVE;
				processing.value = editingEvent.getOldValue();
				break;
			case PropertiesEditingEvent.REMOVE_MANY:
				processing.processingKind = ProcessingKind.REMOVE_MANY;
				processing.value = editingEvent.getOldValue();
				break;
			case PropertiesEditingEvent.MOVE:
				processing.processingKind = ProcessingKind.MOVE;
				processing.oldIndex = (Integer)editingEvent.getOldValue();
				processing.newIndex = (Integer)editingEvent.getNewValue();
				break;
			default:
				processing.processingKind = ProcessingKind.SET;
				processing.value = editingEvent.getNewValue();
				break;
			}
		}
		return processing;
	}

}
