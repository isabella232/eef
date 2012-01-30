/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SemanticDirectEditingPolicy extends SemanticEditingPolicy {

	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	public SemanticDirectEditingPolicy(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		super(editingComponent, editingEvent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		eObject.eSet(feature, value);
	}

}
