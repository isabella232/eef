/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.processors;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DirectEditingPolicyProcessor implements EditingPolicyProcessor {

	private EditingPolicyWithProcessor editingPolicy;

	public DirectEditingPolicyProcessor(EditingPolicyWithProcessor editingPolicy) {
		this.editingPolicy = editingPolicy;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing)
	 */
	public void process(EditingPolicyProcessing behavior) {
		switch (behavior.processingKind) {
		case SET:
			performSet(behavior.target, behavior.feature, behavior.value);
			break;
		case UNSET:
			performUnset(behavior.target, behavior.feature);
			break;
		case EDIT:
			performEdit(behavior.target, behavior.feature, behavior.value);
			break;
		case ADD:
			performAdd(behavior.target, behavior.feature, behavior.value);			
			break;
		case ADD_MANY:
			performAddMany(behavior.target, behavior.feature, (Collection<?>) behavior.value);
			break;
		case REMOVE:
			performRemove(behavior.target, behavior.feature, behavior.value);			
			break;
		case REMOVE_MANY:
			performRemoveMany(behavior.target, behavior.feature, (Collection<?>) behavior.value);
			break;
		case MOVE:
			performMove(behavior.target, behavior.feature, behavior.oldIndex, behavior.newIndex);
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		if (value != null) {
			if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value));
			} else {
				eObject.eSet(feature, value);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performUnset(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void performUnset(EObject eObject, EStructuralFeature feature) {
		eObject.eUnset(feature);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	public void performEdit(EObject eObject, EStructuralFeature feature, Object value) {
		if (value instanceof EObject) {
			PropertiesEditingContext editingContext = editingPolicy.getEditingContext();
			EObject editedElement = (EObject)value;
			PropertiesEditingContextFactory factory = editingContext.getServiceRegistry().getService(PropertiesEditingContextFactory.class, editedElement);
			PropertiesEditingPolicy subElementEditingPolicy = editingContext.getEditingPolicy(factory.createPropertiesEditingContext(editingContext, editedElement));
			editingPolicy.getEditingContext().getEditingComponent().execute(subElementEditingPolicy);			
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performAdd(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		if (newValue != null) {
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
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performAddMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues) {
		if (newValues != null) {
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
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performRemove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).remove(oldValue);
		} else {
			throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performRemoveMany(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public void performRemoveMany(EObject eObject, EStructuralFeature feature, Collection<?> oldValues) {
		if (feature.isMany()) {
			((Collection<Object>)eObject.eGet(feature)).removeAll(oldValues);
		} else {
			throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#performMove(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Integer, java.lang.Integer)
	 */
	public void performMove(EObject eObject, EStructuralFeature feature, Integer oldIndex, Integer newIndex) {
		Object currentValue = eObject.eGet(feature);
		if (currentValue instanceof EList<?>) {
			((EList<?>)eObject.eGet(feature)).move(newIndex, oldIndex);
		} else {
			throw new IllegalArgumentException("Cannot _MOVE_ a value in this feature.");
		}
	}
}
