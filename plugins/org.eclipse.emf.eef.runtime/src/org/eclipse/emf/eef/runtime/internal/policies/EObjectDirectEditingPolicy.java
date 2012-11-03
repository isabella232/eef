/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectDirectEditingPolicy extends EObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public EObjectDirectEditingPolicy(PropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	protected void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
			eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value));
		} else {
			eObject.eSet(feature, value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performUnset(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	protected void performUnset(EObject eObject, EStructuralFeature feature) {
		eObject.eUnset(feature);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	protected void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		if (feature.isMany()) {
			if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
			} else {
				((Collection<Object>)eObject.eGet(feature)).add(newValue);
			}
		} else {
			throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performAddMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	protected void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues) {
		if (feature.isMany()) {
			for (Object newValue : newValues) {
				if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
					((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
				} else {
					((Collection<Object>)eObject.eGet(feature)).add(newValue);
				}				
			}
		} else {
			throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performRemove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
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
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performRemoveMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
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
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#performMove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Integer, java.lang.Integer)
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
