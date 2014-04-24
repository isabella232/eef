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
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy;
import org.eclipse.emf.eef.runtime.internal.util.EEFInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.internal.util.EEFModifierInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.util.EEFInvokerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class DirectEditingPolicyProcessor implements EditingPolicyProcessor {

	private EEFInvokerProvider eefInvokerProvider;

	/**
	 * @param eefInvokerProvider
	 *            the eefInvokerProvider to set
	 */
	public final void setEEFInvokerProvider(EEFInvokerProvider eefInvokerProvider) {
		this.eefInvokerProvider = eefInvokerProvider;
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
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MONO_VALUED_PROPERTY_BINDING__SETTER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(value);
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFModifierInvocationParametersImpl(editingContext, value));
					return null;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					if (value != null) {
						if (value instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
							eObject.eSet(feature, EcoreUtil.createFromString((EDataType) feature.getEType(), (String) value));
						} else {
							eObject.eSet(feature, value);
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
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MONO_VALUED_PROPERTY_BINDING__UNSETTER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFInvocationParametersImpl(editingContext));
					return null;
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
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__ADDER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(newValue);
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFModifierInvocationParametersImpl(editingContext, newValue));
					return null;
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByFeature(org.eclipse.emf.ecore.EStructuralFeature)
				 */
				@Override
				protected Void processByFeature(EStructuralFeature feature) {
					if (newValue != null) {
						if (feature.isMany()) {
							if (newValue instanceof String && !"java.lang.String".equals(feature.getEType().getInstanceTypeName())) {
								((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.createFromString((EDataType) feature.getEType(), (String) newValue));
							} else if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
								EClass newValueClass = (EClass) newValue;
								EClass referenceType = ((EReference) feature).getEReferenceType();
								if (referenceType == newValue || referenceType.isSuperTypeOf(newValueClass)) {
									((Collection<Object>) eObject.eGet(feature)).add(EcoreUtil.create(newValueClass));
								}
							} else {
								if (newValue instanceof EClass && feature instanceof EReference && !(feature.getEType() == EcorePackage.Literals.ECLASS)) {
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
	protected final void performAddMany(final SemanticPropertiesEditingContext editingContext, final EObject eObject, final Collection<?> newValues) {
		try {
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__ADDER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.addAll(newValues);
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFModifierInvocationParametersImpl(editingContext, newValues));
					return null;
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
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__REMOVER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.add(oldValue);
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFModifierInvocationParametersImpl(editingContext, oldValue));
					return null;
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
			new EEFEditingStrategy<Void>(editingContext, EditingModelPackage.Literals.MULTI_VALUED_PROPERTY_BINDING__REMOVER) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					EList<Object> parameters = new BasicEList<Object>();
					parameters.addAll(oldValues);
					eefInvokerProvider.getInvoker(accessor).invoke(editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), accessor, new EEFModifierInvocationParametersImpl(editingContext, oldValues));
					return null;
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
			new EEFEditingStrategy<Void>(editingContext, null) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.internal.util.EEFEditingStrategy#processByAccessor(org.eclipse.emf.eef.runtime.query.JavaBody)
				 */
				@Override
				protected Void processByAccessor(JavaBody accessor) {
					// Unreachable
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
