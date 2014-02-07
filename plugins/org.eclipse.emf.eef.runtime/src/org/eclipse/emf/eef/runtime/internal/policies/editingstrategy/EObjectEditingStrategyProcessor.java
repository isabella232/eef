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
package org.eclipse.emf.eef.runtime.internal.policies.editingstrategy;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.query.JavaBody;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EObjectEditingStrategyProcessor<T> {
	
	private final SemanticPropertiesEditingContext editingContext;
	private final EReference settingReference;

	public EObjectEditingStrategyProcessor(SemanticPropertiesEditingContext editingContext, EReference settingReference) {
		this.editingContext = editingContext;
		this.settingReference = settingReference;
	}

	/**
	 * @return the editingContext
	 */
	public final SemanticPropertiesEditingContext getEditingContext() {
		return editingContext;
	}
	
	/**
	 * @return the settingReference
	 */
	public final EReference getSettingReference() {
		return settingReference;
	}

	/**
	 * Performs a editing strategy responding to the given {@link SemanticPropertiesEditingContext}.
	 * @return the editing strategy result.
	 * @throws EditingStrategyNotFoundException unable a to find a valid editing strategy given the context.
	 */
	public final T process() throws EditingStrategyNotFoundException {
		Object view = editingContext.getEditingEvent().getAffectedEditor();
		boolean autowire = editingContext.getOptions().autowire();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		PropertyBinding propertyBinding = binding.propertyBinding(view, autowire);
		if (propertyBinding != null) {
			if (settingReference != null && propertyBinding.eGet(settingReference) != null) {
				return processBySetter((JavaBody<Void>) propertyBinding.eGet(settingReference));
			} else if (propertyBinding instanceof EStructuralFeatureBinding) {
				return processByFeature(((EStructuralFeatureBinding) propertyBinding).getFeature());
			}
		} else {
			if (autowire) {
				EStructuralFeature eStructuralFeature = null;
				if (view instanceof String) {
					eStructuralFeature = binding.getEClass().getEStructuralFeature((String)view);
				}
				if (view instanceof EObject) {
					// Here we dont have an PropertyBinding to help us. We check if the view is an EObject (fon instance an ElementEditor)
					// We looking for an "name" structural feature and if this feature is type of String, we try to associate this name
					// with a structural feature of the handled EClass. For instance if an ElementEditor (which has a "name" feature) is named
					// "active" and the current EClass has a feature named "active", we return this feature.
					EStructuralFeature nameFeature = ((EObject) view).eClass().getEStructuralFeature("name");
					if (nameFeature != null && "java.lang.String".equals(nameFeature.getEType().getInstanceClassName())) {
						eStructuralFeature = binding.getEClass().getEStructuralFeature((String)((EObject) view).eGet(nameFeature));
					}
				}
				if (eStructuralFeature != null) {
					return processByFeature(eStructuralFeature);
				}
			}
		}
		throw new EditingStrategyNotFoundException("Unable to find a valid Editing Strategy.");
	}

	
	/**
	 * Describes the editing strategy via a Java Setter.
	 * @param setter the setter to use.
	 * @return the editing strategy result. 
	 */
	protected abstract T processBySetter(JavaBody<Void> setter);

	/**
	 * Describes the editing strategy via an {@link EStructuralFeature}.
	 * @param feature the {@link EStructuralFeature} to use.
	 * @return the editing strategy result.
	 */
	protected abstract T processByFeature(EStructuralFeature feature);
		
}
