/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

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
		EObject editedObject = (EObject) editingComponent.getTarget();
		EClassBinding binding = editingComponent.getEditingContext().getEditingModel().binding(editedObject);
		EStructuralFeature feature = binding.feature(editingEvent.getAffectedEditor());
		performSet((EObject) editingComponent.getTarget(), feature, editingEvent.getNewValue());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public abstract void performSet(EObject eObject, EStructuralFeature feature, Object value);

}
