/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class SemanticEditingPolicy implements PropertiesEditingPolicy {
	
	private PropertiesEditingEvent editingEvent;
	private PropertiesEditingComponent editingComponent;
	
	/**
	 * @param editingEvent
	 */
	public SemanticEditingPolicy(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		this.editingComponent = editingComponent;
		this.editingEvent = editingEvent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public void execute() {
		EObject editedObject = (EObject) editingComponent.getTarget();
		EClassBinding binding = editingComponent.getEditingContext().getEditingModel().binding(editedObject);
		EStructuralFeature feature = binding.feature(editingEvent.getAffectedEditor());
		if (editingEvent instanceof TypedPropertyChangedEvent) {
			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.SET:
				performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
				break;
			case PropertiesEditingEvent.UNSET:
				performUnset((EObject) editingComponent.getTarget(), feature);								
			case PropertiesEditingEvent.ADD:
				performAdd((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
			case PropertiesEditingEvent.REMOVE:
				performRemove((EObject) editingComponent.getTarget(), feature, editingEvent.getOldValue());				
			default:
				performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
				break;
			}
		} else {
			performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());
		}
	}	


	/**
	 * Sets the given value to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param value value to set.
	 */
	protected abstract void performSet(EObject eObject, EStructuralFeature feature, Object value);

	/**
	 * Unsets the given feature of an EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to unset.
	 */
	protected abstract void performUnset(EObject eObject, EStructuralFeature feature);

	/**
	 * Add the given value to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param newValue new value to add.
	 */
	protected abstract void performAdd(EObject eObject, EStructuralFeature feature, Object newValue);

	/**
	 * Remove a value to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param newValue value to remove.
	 */
	protected abstract void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue);

}
