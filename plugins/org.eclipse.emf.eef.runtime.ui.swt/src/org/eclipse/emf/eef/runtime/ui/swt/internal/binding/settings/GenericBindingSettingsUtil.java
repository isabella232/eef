/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsRepository;

/**
 * Generic binding settings for EObject.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettingsUtil {

	public static final String PROPERTIES_EDITING_MODEL_NAME = "Binding Settings";
	public static final String PROPERTIES_EDITING_MODEL_ID = "org.eclipse.emf.eef.runtime.ui.swt.genericBindingSetting";

	/**
	 * @param resource
	 * @return viewsRepository
	 */
	public static ViewsRepository getViewsRepository(Resource resource) {
		if (resource.getContents().size() == 2 && resource.getContents().get(1) instanceof ViewsRepository) {
			return (ViewsRepository) resource.getContents().get(1);
		}
		return null;
	}

	/**
	 * @param resource
	 *            Resource
	 * @return propertiesEditingModel
	 */
	public static PropertiesEditingModel getPropertiesEditionModel(Resource resource) {
		if (resource.getContents().size() == 2 && resource.getContents().get(0) instanceof PropertiesEditingModel) {
			return (PropertiesEditingModel) resource.getContents().get(0);
		}
		return null;
	}

	/**
	 * Get genmodel if exists.
	 * 
	 * @param eObject
	 *            EObject
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 */
	@SuppressWarnings("deprecation")
	protected static void bindGenModel(EPackage eObject, PropertiesEditingModel propertiesEditingModel, ResourceSet resourceSet) {
		URI uri = EcorePlugin.getEPackageNsURIToGenModelLocationMap().get(eObject.getNsURI());
		if (uri != null) {
			Resource genModelResource = resourceSet.getResource(uri, true);
			if (!genModelResource.getContents().isEmpty()) {
				propertiesEditingModel.getInvolvedModels().add(genModelResource.getContents().get(0));
			}
		} else {
			// load Ecore resource
			if (eObject.eResource().getURI() != null) {
				resourceSet.getResource(eObject.eResource().getURI(), true);
			}
		}
	}

	/**
	 * Get eObject ePackage
	 * 
	 * @param eObject
	 *            EObject
	 */
	protected static EPackage getEPackageFromResourceSet(EPackage eObject, EditingModelEnvironment editingModelEnvironment) {
		for (Resource resource : editingModelEnvironment.getResourceSet().getResources()) {
			for (EObject content : resource.getContents()) {
				if (content instanceof EPackage) {
					EPackage ePackageFromResourceSet = getEPackageFromResourceSet(((EPackage) content), eObject);
					if (ePackageFromResourceSet != null) {
						return ePackageFromResourceSet;
					}
				}
			}
		}
		return null;
	}

	private static EPackage getEPackageFromResourceSet(EPackage ePackage, EPackage eObject) {
		if (ePackage.getNsURI().equals(eObject.getNsURI())) {
			return ePackage;
		}
		for (EPackage subPackage : ePackage.getESubpackages()) {
			EPackage ePackageFromResourceSet = getEPackageFromResourceSet(subPackage, eObject);
			if (ePackageFromResourceSet != null) {
				return ePackageFromResourceSet;
			}
		}
		return null;
	}

	/**
	 * Init PropertiesEditingModel.
	 * 
	 * @param eClass
	 * @return PropertiesEditingModel Resource
	 */
	public static Resource initPropertiesEditingModel(EPackage ePackage, EMFServiceProvider emfServiceProvider, EditingModelEnvironment editingModelEnvironment) {
		PropertiesEditingModel propertiesEditingModel = null;
		propertiesEditingModel = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		propertiesEditingModel.setId(PROPERTIES_EDITING_MODEL_ID);
		String modelName = PROPERTIES_EDITING_MODEL_NAME;
		if (ePackage.getName() != null) {
			modelName = ePackage.getName() + " " + modelName;
		}
		propertiesEditingModel.setName(modelName);
		propertiesEditingModel.setEMFServiceProvider(emfServiceProvider);
		Resource resource = new ResourceImpl(URI.createURI(ePackage.getNsURI() + ".editingModel"));
		resource.getContents().add(propertiesEditingModel);
		ViewsRepository viewsRepository = ViewsFactory.eINSTANCE.createViewsRepository();
		viewsRepository.setName(ePackage.getName() + " View Repository");
		resource.getContents().add(viewsRepository);

		// bind genmodel if exist
		bindGenModel(ePackage, propertiesEditingModel, editingModelEnvironment.getResourceSet());
		EPackage ePackageFromResourceSet = getEPackageFromResourceSet(ePackage, editingModelEnvironment);
		propertiesEditingModel.getInvolvedModels().add(ePackageFromResourceSet);
		return resource;
	}

}
