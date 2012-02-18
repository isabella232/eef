/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
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
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		eObject.eSet(feature, value);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performUnset(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected void performUnset(EObject eObject, EStructuralFeature feature) {
		eObject.eUnset(feature);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	protected void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).add(newValue);
		} else {
			throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performAddMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	protected void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).addAll(newValues);
		} else {
			throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performRemove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	protected void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).remove(oldValue);
		} else {
			throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performRemoveMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	protected void performRemoveMany(EObject eObject, EStructuralFeature feature, Collection<?> oldValues) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).removeAll(oldValues);
		} else {
			throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.SemanticEditingPolicy#performMove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Integer, java.lang.Integer)
	 */
	protected void performMove(EObject eObject, EStructuralFeature feature, Integer oldIndex, Integer newIndex) {
		Object currentValue = eObject.eGet(feature);
		if (currentValue instanceof EList<?>) {
			((EList<?>)eObject.eGet(feature)).move(newIndex, oldIndex);
		} else {
			throw new IllegalArgumentException("Cannot _MOVE_ a value in this feature.");
		}
	}

}
