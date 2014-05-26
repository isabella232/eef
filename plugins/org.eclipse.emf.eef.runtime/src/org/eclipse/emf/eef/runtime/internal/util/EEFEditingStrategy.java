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
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.binding.MonoPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.MultiPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.util.EMFService;

import com.google.common.base.Strings;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class EEFEditingStrategy<T, U> {

	private final PropertiesEditingContext editingContext;
	private final Object editor;
	private final int accessorKind;

	public EEFEditingStrategy(PropertiesEditingContext editingContext, Object editor, int accessorKind) {
		this.editingContext = editingContext;
		this.editor = editor;
		this.accessorKind = accessorKind;
	}

	public EEFEditingStrategy(SemanticPropertiesEditingContext editingContext, int accessorKind) {
		this(editingContext, editingContext.getEditingEvent().getAffectedEditor(), accessorKind);
	}

	/**
	 * @return the editingContext
	 */
	public final PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * @return the accessorKind
	 */
	public int getAccessorKind() {
		return accessorKind;
	}

	/**
	 * Performs a editing strategy responding to the given
	 * {@link SemanticPropertiesEditingContext}.
	 * 
	 * @return the editing strategy result.
	 * @throws EditingStrategyNotFoundException
	 *             unable a to find a valid editing strategy given the context.
	 */
	public final T process() throws EditingStrategyNotFoundException {
		boolean autowire = editingContext.getOptions().autowire();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		EObject editedObject = editingContext.getEditingComponent().getEObject();
		EMFService emfService = editingContext.getEMFServiceProvider().getEMFService(editedObject.eClass().getEPackage());
		PropertyBinding propertyBinding = binding.propertyBinding(editor, autowire);
		if (propertyBinding != null) {
			EEFModifierCustomizer<U> customization = getCustomization(propertyBinding, accessorKind);
			if (customization != null && !(customization instanceof NullModifierCustomizer)) {
				return processByAccessor(customization);
			} else if (propertyBinding instanceof EStructuralFeatureBinding) {
				EStructuralFeature feature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
				if (!editedObject.eClass().getEAllStructuralFeatures().contains(feature)) {
					EStructuralFeature mappedFeature = emfService.mapFeature(editedObject.eClass(), feature);
					return processByFeature(mappedFeature);
				} else {
					return processByFeature(feature);
				}
			}
		} else {
			if (autowire) {
				EStructuralFeature eStructuralFeature = null;
				if (editor instanceof String) {
					eStructuralFeature = binding.getEClass().getEStructuralFeature((String) editor);
				}
				if (editor instanceof EObject) {
					// Here we don't have an PropertyBinding to help us. We
					// check if the editor is an EObject (for instance an
					// ElementEditor)
					// We looking for an "name" structural feature and if this
					// feature is type of String, we try to associate this name
					// with a structural feature of the handled EClass. For
					// instance if an ElementEditor (which has a "name" feature)
					// is named
					// "active" and the current EClass has a feature named
					// "active", we return this feature.
					EStructuralFeature nameFeature = ((EObject) editor).eClass().getEStructuralFeature("name");
					if (nameFeature != null && "java.lang.String".equals(nameFeature.getEType().getInstanceClassName())) {
						eStructuralFeature = binding.getEClass().getEStructuralFeature((String) ((EObject) editor).eGet(nameFeature));
					}
				}
				if (eStructuralFeature != null) {
					return processByFeature(editingContext.getEMFServiceProvider().getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage()).mapFeature(editingContext.getEditingComponent().getEObject().eClass(), eStructuralFeature));
				}
			}
		}
		throw new EditingStrategyNotFoundException("Unable to find a valid Editing Strategy.");
	}

	/**
	 * @param propertyBinding
	 *            PropertyBinding
	 * @param accessorKind
	 * @return the EEFModifierCustomizer associated to the accessor kind for
	 *         propertyBinding
	 */
	@SuppressWarnings("unchecked")
	private EEFModifierCustomizer<U> getCustomization(PropertyBinding propertyBinding, int accessorKind) {
		EEFModifierCustomizer<?> result = null;
		String bindingCustomizer = propertyBinding.getBindingCustomizer();
		if (!Strings.isNullOrEmpty(bindingCustomizer)) {
			ClassLoader classLoader = editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader();
			try {
				Class<PropertyBindingCustomizer> loadClass = (Class<PropertyBindingCustomizer>) classLoader.loadClass(bindingCustomizer);
				PropertyBindingCustomizer newInstance = loadClass.newInstance();
				switch (accessorKind) {
				case PropertyBindingCustomizer.GETTER:
					result = newInstance.getGetter();
					break;
				case PropertyBindingCustomizer.VALUES_PROVIDER:
					result = newInstance.getValuesProvider();
					break;
				case MonoPropertyBindingCustomizer.SETTER:
					if (newInstance instanceof MonoPropertyBindingCustomizer) {
						result = ((MonoPropertyBindingCustomizer) newInstance).getSetter();
						break;
					}
				case MonoPropertyBindingCustomizer.UNSETTER:
					if (newInstance instanceof MonoPropertyBindingCustomizer) {
						result = ((MonoPropertyBindingCustomizer) newInstance).getUnsetter();
						break;
					}
				case MultiPropertyBindingCustomizer.ADDER:
					if (newInstance instanceof MultiPropertyBindingCustomizer) {
						result = ((MultiPropertyBindingCustomizer) newInstance).getAdder();
						break;
					}
				case MultiPropertyBindingCustomizer.REMOVER:
					if (newInstance instanceof MultiPropertyBindingCustomizer) {
						result = ((MultiPropertyBindingCustomizer) newInstance).getRemover();
						break;
					}
				default:
					result = new NullModifierCustomizer<Object>();
					break;
				}
			} catch (ClassNotFoundException e) {
				result = new NullModifierCustomizer<Object>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (ClassCastException e) {
				result = new NullModifierCustomizer<Object>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (InstantiationException e) {
				result = new NullModifierCustomizer<Object>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			} catch (IllegalAccessException e) {
				result = new NullModifierCustomizer<Object>();
				EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + bindingCustomizer, e));
			}
		}
		return (EEFModifierCustomizer<U>) result;
	}

	/**
	 * Describes the editing strategy via a Java Setter.
	 * 
	 * @param accessor
	 *            the accessor to use.
	 * @return the editing strategy result.
	 */
	protected abstract T processByAccessor(EEFModifierCustomizer<U> modifierCustomizer);

	/**
	 * Describes the editing strategy via an {@link EStructuralFeature}.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature} to use.
	 * @return the editing strategy result.
	 */
	protected abstract T processByFeature(EStructuralFeature feature);

}
