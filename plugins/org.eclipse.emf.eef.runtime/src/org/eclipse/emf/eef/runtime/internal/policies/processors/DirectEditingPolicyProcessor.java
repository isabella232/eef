/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import java.util.Collection;

import org.eclipse.emf.common.util.BasicEList;
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
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.MonoValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.MultiValuedPropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DirectEditingPolicyProcessor implements EditingPolicyProcessor {
	
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
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyRequest request) {
		switch (request.getProcessingKind()) {
		case SET:
			performSet(editingContext, request.getTarget(), request.getPropertyBinding(), request.getValue());
			break;
		case UNSET:
			performUnset(editingContext, request.getTarget(), request.getPropertyBinding());
			break;
		case EDIT:
			performEdit(editingContext, request.getTarget(), request.getPropertyBinding(), request.getValue());
			break;
		case ADD:
			performAdd(editingContext, request.getTarget(), request.getPropertyBinding(), request.getValue());
			break;
		case ADD_MANY:
			performAddMany(editingContext, request.getTarget(), request.getPropertyBinding(), (Collection<?>) request.getValue());
			break;
		case REMOVE:
			performRemove(editingContext, request.getTarget(), request.getPropertyBinding(), request.getValue());
			break;
		case REMOVE_MANY:
			performRemoveMany(editingContext, request.getTarget(), request.getPropertyBinding(), (Collection<?>) request.getValue());
			break;
		case MOVE:
			performMove(editingContext, request.getTarget(), request.getPropertyBinding(), request.getOldIndex(), request.getNewIndex());
			break;
		default:
			break;
		}
	}

	protected final void performSet(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Object value) {
		if (propertyBinding instanceof MonoValuedPropertyBinding && ((MonoValuedPropertyBinding) propertyBinding).getSetter() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.add(value);
			((MonoValuedPropertyBinding) propertyBinding).getSetter().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getAdder() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.add(value);
			((MultiValuedPropertyBinding) propertyBinding).getAdder().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (value != null) {
					if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
						eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String)value));
					} else {
						eObject.eSet(feature, value);
					}
				}
			}
		}
	}

	protected final void performUnset(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding) {
		if (propertyBinding instanceof MonoValuedPropertyBinding && ((MonoValuedPropertyBinding) propertyBinding).getUnsetter() != null) {
			((MonoValuedPropertyBinding) propertyBinding).getSetter().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, new BasicEList<Object>());
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				eObject.eUnset(feature);
			}
		}
	}

	protected final void performEdit(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Object value) {
		if (value instanceof EObject) {
			EObject editedElement = (EObject)value;
			PropertiesEditingContextFactory factory = editingContext.getContextFactoryProvider().getEditingContextFactory(editedElement);
			PropertiesEditingContext subPropertiesEditingContext = factory.createPropertiesEditingContext(editingContext, editedElement);
			PropertiesEditingPolicyProvider editingPolicyProvider = editingContext.getBindingManagerProvider().getBindingManager(editedElement).getPolicyProvider();
			//I'm quite confident in this cast 
			PropertiesEditingPolicy subElementEditingPolicy = editingPolicyProvider.getEditingPolicy((SemanticPropertiesEditingContext) subPropertiesEditingContext);
			PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
			editingContext.getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).execute(editingComponent, subElementEditingPolicy, subPropertiesEditingContext);
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performAdd(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Object newValue) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getAdder() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.add(newValue);
			((MultiValuedPropertyBinding) propertyBinding).getAdder().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
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
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performAddMany(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Collection<?> newValues) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getAdder() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.addAll(newValues);
			((MultiValuedPropertyBinding) propertyBinding).getAdder().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
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
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performRemove(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Object oldValue) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getRemover() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.add(oldValue);
			((MultiValuedPropertyBinding) propertyBinding).getRemover().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (feature.isMany()) {
					((Collection<Object>)eObject.eGet(feature)).remove(oldValue);
				} else {
					throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performRemoveMany(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Collection<?> oldValues) {
		if (propertyBinding instanceof MultiValuedPropertyBinding && ((MultiValuedPropertyBinding) propertyBinding).getRemover() != null) {
			EList<Object> parameters = new BasicEList<Object>();
			parameters.addAll(oldValues);
			((MultiValuedPropertyBinding) propertyBinding).getRemover().invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), eObject, parameters);
		} else {
			if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
				if (feature.isMany()) {
					((Collection<Object>)eObject.eGet(feature)).removeAll(oldValues);
				} else {
					throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
				}
			}
		}
	}

	protected final void performMove(PropertiesEditingContext editingContext, EObject eObject, PropertyBinding propertyBinding, Integer oldIndex, Integer newIndex) {
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			EStructuralFeature feature = extractFeature((EStructuralFeatureBinding) propertyBinding, eObject);
			Object currentValue = eObject.eGet(feature);
			if (currentValue instanceof EList<?>) {
				((EList<?>)eObject.eGet(feature)).move(newIndex, oldIndex);
			} else {
				throw new IllegalArgumentException("Cannot _MOVE_ a value in this feature.");
			}
		} else {
			//No way to handle this case for the moment.
		}
	}
	
	private EStructuralFeature extractFeature(EStructuralFeatureBinding propertyBinding, EObject target) {
		EMFService emfService = emfServiceProvider.getEMFService(target.eClass().getEPackage());
		return emfService.mapFeature(target, propertyBinding.getFeature());
	}
}
