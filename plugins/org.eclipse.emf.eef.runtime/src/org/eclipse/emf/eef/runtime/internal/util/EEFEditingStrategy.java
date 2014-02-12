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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.policies.editingstrategy.EditingStrategyNotFoundException;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.util.EMFService;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EEFEditingStrategy<T> {
	
	private final PropertiesEditingContext editingContext;
	private final Object editor;
	private final EReference accessorReference;

	public EEFEditingStrategy(PropertiesEditingContext editingContext, Object editor, EReference accessorReference) {
		this.editingContext = editingContext;
		this.editor = editor;
		this.accessorReference = accessorReference;
	}
	
	public EEFEditingStrategy(SemanticPropertiesEditingContext editingContext, EReference settingReference) {
		this(editingContext, editingContext.getEditingEvent().getAffectedEditor(), settingReference);
	}		

	/**
	 * @return the editingContext
	 */
	public final PropertiesEditingContext getEditingContext() {
		return editingContext;
	}
	
	/**
	 * @return the accessorReference
	 */
	public final EReference getSettingReference() {
		return accessorReference;
	}

	/**
	 * Performs a editing strategy responding to the given {@link SemanticPropertiesEditingContext}.
	 * @return the editing strategy result.
	 * @throws EditingStrategyNotFoundException unable a to find a valid editing strategy given the context.
	 */
	public final T process() throws EditingStrategyNotFoundException {
		boolean autowire = editingContext.getOptions().autowire();
		EClassBinding binding = editingContext.getEditingComponent().getBinding();
		EObject editedObject = editingContext.getEditingComponent().getEObject();
		EMFService emfService = editingContext.getEMFServiceProvider().getEMFService(editedObject.eClass().getEPackage());
		PropertyBinding propertyBinding = binding.propertyBinding(editor, autowire);
		if (propertyBinding != null) {
			if (accessorReference != null && propertyBinding.eGet(accessorReference) != null) {
				return processByAccessor((JavaBody<Void>) propertyBinding.eGet(accessorReference));
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
					eStructuralFeature = binding.getEClass().getEStructuralFeature((String)editor);
				}
				if (editor instanceof EObject) {
					// Here we don't have an PropertyBinding to help us. We check if the editor is an EObject (for instance an ElementEditor)
					// We looking for an "name" structural feature and if this feature is type of String, we try to associate this name
					// with a structural feature of the handled EClass. For instance if an ElementEditor (which has a "name" feature) is named
					// "active" and the current EClass has a feature named "active", we return this feature.
					EStructuralFeature nameFeature = ((EObject) editor).eClass().getEStructuralFeature("name");
					if (nameFeature != null && "java.lang.String".equals(nameFeature.getEType().getInstanceClassName())) {
						eStructuralFeature = binding.getEClass().getEStructuralFeature((String)((EObject) editor).eGet(nameFeature));
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
	 * @param accessor the accessor to use.
	 * @return the editing strategy result. 
	 */
	protected abstract T processByAccessor(JavaBody<Void> accessor);

	/**
	 * Describes the editing strategy via an {@link EStructuralFeature}.
	 * @param feature the {@link EStructuralFeature} to use.
	 * @return the editing strategy result.
	 */
	protected abstract T processByFeature(EStructuralFeature feature);
		
}
