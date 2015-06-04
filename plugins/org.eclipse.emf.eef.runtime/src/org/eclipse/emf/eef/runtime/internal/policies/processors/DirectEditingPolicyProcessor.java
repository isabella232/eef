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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.MultiPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy;
import org.eclipse.emf.eef.runtime.internal.util.EEFInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.internal.util.EEFModifierInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class DirectEditingPolicyProcessor implements EditingPolicyProcessor {

	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyRequest request) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			SemanticPropertiesEditingContext semanticPropertiesEditingContext = (SemanticPropertiesEditingContext) editingContext;
			final EObject eObject = request.getTarget();
			Object newValue = request.getValue();
			switch (request.getProcessingKind()) {
			case SET:
				performSet(semanticPropertiesEditingContext, eObject, newValue);
				break;
			case UNSET:
				performUnset(semanticPropertiesEditingContext, eObject);
				break;
			case EDIT:
				performEdit(semanticPropertiesEditingContext, eObject, newValue);
				break;
			case ADD:
				performAdd(semanticPropertiesEditingContext, eObject, newValue);
				break;
			case ADD_MANY:
				performAddMany(semanticPropertiesEditingContext, eObject, (Collection<?>) newValue);
				break;
			case REMOVE:
				performRemove(semanticPropertiesEditingContext, eObject, newValue);
				break;
			case REMOVE_MANY:
				performRemoveMany(semanticPropertiesEditingContext, eObject, (Collection<?>) newValue);
				break;
			case MOVE:
				performMove(semanticPropertiesEditingContext, eObject, request.getOldIndex(), request.getNewIndex());
				break;
			default:
				break;
			}
		}
	}

	protected final void performSet(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Object value) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MonoPropertyBindingCustomizer.SETTER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, value));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					Object newNewValue = (value == null && feature instanceof EReference && ((EReference)feature).isContainment()) ? defineEObjectToAdd(editingContext, (EReference) feature) : value;
					if (newNewValue != null) {
						if (newNewValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
							eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newNewValue));
						} else {
							eObject.eSet(feature, newNewValue);
						}
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	protected final void performUnset(final SemanticPropertiesEditingContext editingContext, final EObject eObject) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MonoPropertyBindingCustomizer.UNSETTER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFInvocationParametersImpl(editingContext));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					eObject.eUnset(feature);
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	protected void performEdit(SemanticPropertiesEditingContext editingContext, EObject eObject, Object value) {
		// Do nothing in this case.
	}

	@SuppressWarnings("unchecked")
	protected final void performAdd(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Object newValue) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MultiPropertyBindingCustomizer.ADDER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, newValue));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					Object newNewValue = (newValue == null) ? defineEObjectToAdd(editingContext, (EReference) feature) : newValue;
					if (newNewValue != null) {
						if (feature.isMany()) {
							if (newNewValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
								((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newNewValue));
							} else if (newNewValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
								EClass newValueClass = (EClass) newNewValue;
								EClass referenceType = ((EReference) feature).getEReferenceType();
								if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
									((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.create(newValueClass));
								}
							} else {
								if (newNewValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
									((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.create((EClass) newNewValue));
								} else {
									((Collection<Object>) eObject.eGet(feature)).add(newNewValue);
								}
							}
						} else {
							throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
						}
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	protected Object defineEObjectToAdd(SemanticPropertiesEditingContext editingContext, EReference feature) {
		if (((EClass) feature.getEType()).isAbstract()) {
			EObject editedObject = editingContext.getEditingComponent().getEObject();
			Collection<EClass> listOfInstanciableType = emfServiceProvider.getEMFService(editedObject.eClass().getEPackage()).listOfInstanciableType(editingContext.getAdapterFactory(), editedObject, feature);
			if (listOfInstanciableType.size() > 0) {
				return EcoreUtil.create(listOfInstanciableType.iterator().next());
			}

		} else {
			return EcoreUtil.create(((EClass) feature.getEType()));
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected final void performAddMany(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Collection<?> newValues) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MultiPropertyBindingCustomizer.ADDER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, newValues));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					if (newValues != null) {
						if (feature.isMany()) {
							for (Object newValue : newValues) {
								if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
									((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newValue));
								} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
									((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.create((EClass) newValue));
								} else {
									((Collection<Object>) eObject.eGet(feature)).add(newValue);
								}
							}
						} else {
							throw new IllegalArgumentException("Cannot _ADD_ a value to a single feature.");
						}
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performRemove(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Object oldValue) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MultiPropertyBindingCustomizer.REMOVER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, oldValue));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					if (feature.isMany()) {
						((Collection<Object>) eObject.eGet(feature)).remove(oldValue);
					} else {
						throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	@SuppressWarnings("unchecked")
	protected final void performRemoveMany(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Collection<?> oldValues) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, MultiPropertyBindingCustomizer.REMOVER) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					return modifierCustomizer.execute(new EEFModifierInvocationParametersImpl(editingContext, oldValues));
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					if (feature.isMany()) {
						((Collection<Object>) eObject.eGet(feature)).removeAll(oldValues);
					} else {
						throw new IllegalArgumentException("Cannot _REMOVE_ a value to a single feature.");
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}

	protected final void performMove(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Integer oldIndex, final Integer newIndex) {
		try {
			new EEFEditingStrategy<Void, Void>(editingContext, -1) {

				/**
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer)
				 */
				@Override
				protected Void processByAccessor(EEFModifierCustomizer<Void> modifierCustomizer) {
					// TODO Auto-generated method stub
					return null;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					Object currentValue = eObject.eGet(feature);
					if (currentValue instanceof EList<?>) {
						((EList<?>) eObject.eGet(feature)).move(newIndex, oldIndex);
					} else {
						throw new IllegalArgumentException("Cannot _MOVE_ a value in this feature.");
					}
					return null;
				}

			}.process();
		} catch (EditingStrategyNotFoundException e) {
			// Do nothing
		}
	}
}
