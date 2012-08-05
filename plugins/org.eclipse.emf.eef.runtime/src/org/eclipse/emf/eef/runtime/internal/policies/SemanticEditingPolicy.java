/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
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
		EClassBinding binding = editingComponent.getBinding();
		EStructuralFeature bindingFeature = binding.feature(editingEvent.getAffectedEditor(), editingComponent.getEditingContext().getOptions().autowire());
		EObject editedObject = (EObject)editingComponent.getTarget();
		EStructuralFeature feature = editingComponent.getEditingContext().getEMFHelper().mapFeature(editedObject, bindingFeature);
		if (feature != null) {

			switch (editingEvent.getEventType()) {
			case PropertiesEditingEvent.SET:
				performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
				break;
			case PropertiesEditingEvent.UNSET:
				performUnset((EObject) editingComponent.getTarget(), feature);								
				break;
			case PropertiesEditingEvent.ADD:
				performAdd((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
				break;
			case PropertiesEditingEvent.ADD_MANY:
				performAddMany((EObject) editingComponent.getTarget(), feature, (Collection<?>) editingEvent.getNewValue());				
				break;	
			case PropertiesEditingEvent.REMOVE:
				performRemove((EObject) editingComponent.getTarget(), feature, editingEvent.getOldValue());				
				break;
			case PropertiesEditingEvent.REMOVE_MANY:
				performRemoveMany((EObject) editingComponent.getTarget(), feature, (Collection<?>) editingEvent.getOldValue());				
				break;
			case PropertiesEditingEvent.MOVE:
				performMove((EObject) editingComponent.getTarget(), feature, (Integer)editingEvent.getOldValue(), (Integer)editingEvent.getNewValue());				
				break;
			default:
				performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());				
				break;
			}
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
	 * Add the several values to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param newValues values to add.
	 */
	protected abstract void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues);

	/**
	 * Remove a value to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param newValue value to remove.
	 */
	protected abstract void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue);

	/**
	 * Remove several values to the feature of the EObject.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param newValues value to remove.
	 */
	protected abstract void performRemoveMany(EObject eObject, EStructuralFeature feature, Collection<?> oldValues);

	/**
	 * Moves the object from the old index to the new index.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to edit.
	 * @param oldIndex the position of the object after the move.
	 * @param newIndex the position of the object before the move.
	 */
	protected abstract void performMove(EObject eObject, EStructuralFeature feature, Integer oldIndex, Integer newIndex);
}
