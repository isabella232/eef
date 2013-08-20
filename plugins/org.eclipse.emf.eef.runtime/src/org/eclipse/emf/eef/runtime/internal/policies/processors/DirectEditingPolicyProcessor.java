/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DirectEditingPolicyProcessor implements EditingPolicyProcessor {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior) {
		switch (behavior.getProcessingKind()) {
		case SET:
			performSet(behavior.getTarget(), behavior.getFeature(), behavior.getValue());
			break;
		case UNSET:
			performUnset(behavior.getTarget(), behavior.getFeature());
			break;
		case EDIT:
			performEdit(editingContext, behavior.getTarget(), behavior.getFeature(), behavior.getValue());
			break;
		case ADD:
			performAdd(behavior.getTarget(), behavior.getFeature(), behavior.getValue());
			break;
		case ADD_MANY:
			performAddMany(behavior.getTarget(), behavior.getFeature(), (Collection<?>) behavior.getValue());
			break;
		case REMOVE:
			performRemove(behavior.getTarget(), behavior.getFeature(), behavior.getValue());
			break;
		case REMOVE_MANY:
			performRemoveMany(behavior.getTarget(), behavior.getFeature(), (Collection<?>) behavior.getValue());
			break;
		case MOVE:
			performMove(behavior.getTarget(), behavior.getFeature(), behavior.getOldIndex(), behavior.getNewIndex());
			break;
		default:
			break;
		}
	}

	protected final void performSet(EObject eObject, EStructuralFeature feature, Object value) {
		if (value != null) {
			if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
				eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value));
			} else {
				eObject.eSet(feature, value);
			}
		}
	}

	protected final void performUnset(EObject eObject, EStructuralFeature feature) {
		eObject.eUnset(feature);
	}

	protected final void performEdit(PropertiesEditingContext editingContext, EObject eObject, EStructuralFeature feature, Object value) {
		if (value instanceof EObject) {
			EObject editedElement = (EObject)value;
			PropertiesEditingContextFactory factory = editingContext.getContextFactoryProvider().getEditingContextFactory(editedElement);
			PropertiesEditingContext subPropertiesEditingContext = factory.createPropertiesEditingContext(editingContext, editedElement);
			PropertiesEditingPolicyProvider editingPolicyProvider = editingContext.getBindingManagerProvider().getBindingManager(editedElement).getPolicyProvider();
			PropertiesEditingPolicy subElementEditingPolicy = editingPolicyProvider.getEditingPolicy(subPropertiesEditingContext);
			PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
			editingContext.getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).execute(editingComponent, subElementEditingPolicy, subPropertiesEditingContext);
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performAdd(EObject eObject, EStructuralFeature feature, Object newValue) {
		if (newValue != null) {
			if (feature.isMany()) {
				if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
					((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
				} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
					EClass newValueClass = (EClass) newValue;
					EClass referenceType = ((EReference)feature).getEReferenceType();
					if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
						((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.create(newValueClass));				
					}
				} else {
					if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
						((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.create((EClass) newValue));						
					} else {
						((Collection<Object>)eObject.eGet(feature)).add(newValue);
					}
				}
			} else {
				throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performAddMany(EObject eObject, EStructuralFeature feature, Collection<?> newValues) {
		if (newValues != null) {
			if (feature.isMany()) {
				for (Object newValue : newValues) {
					if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
						((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String)newValue));
					} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
						((Collection<Object>)eObject.eGet(feature)).add(EcoreUtil.create((EClass) newValue));						
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
	protected final void performRemove(EObject eObject, EStructuralFeature feature, Object oldValue) {
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
	protected final void performRemoveMany(EObject eObject, EStructuralFeature feature, Collection<?> oldValues) {
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
	protected final void performMove(EObject eObject, EStructuralFeature feature, Integer oldIndex, Integer newIndex) {
		Object currentValue = eObject.eGet(feature);
		if (currentValue instanceof EList<?>) {
			((EList<?>)eObject.eGet(feature)).move(newIndex, oldIndex);
		} else {
			throw new IllegalArgumentException("Cannot _MOVE_ a value in this feature.");
		}
	}
}
