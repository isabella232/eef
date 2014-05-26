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
package org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.util.BindingSettingsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.osgi.service.event.EventAdmin;

/**
 * Generic binding settings for EObject.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettings implements EEFBindingSettings<PropertiesEditingModel>, DefaultService {

	/**
	 * Widgets constants
	 */
	public static final String GROUP_CONTAINER_NAME = "Group";
	public static final String TEXT_WIDGET_NAME = "Text";
	public static final String ECOMBO_EDITOR_WIDGET_NAME = "ECombo Editor";
	public static final String ECONTAINEMENT_EDITOR_WIDGET_NAME = "EContainement Editor";
	public static final String EDATE_PICKER_EDITOR_WIDGET_NAME = "EDatePicker Editor";
	public static final String EREFERENCE_EDITOR_WIDGET_NAME = "EReference Editor";
	public static final String SINGLE_CONTAINMENT_EDITOR_WIDGET_NAME = "Single Containment Editor";
	public static final String CHECKBOX_WIDGET_NAME = "Checkbox";
	public static final String COMBO_WIDGET_NAME = "Combo";
	public static final String TEXTAREA_WIDGET_NAME = "Textarea";

	/**
	 * Providers
	 */
	private EMFServiceProvider emfServiceProvider;
	private EditingModelEnvironment editingModelEnvironment;
	private ViewHandlerProvider viewHandlerProvider;
	private EEFToolkitProvider toolkitProvider;

	/**
	 * Properties editing model.
	 */
	private ResourceSet resourceSet;
	private Map<String, Resource> mapURI2PropertiesEditingModelResource = new HashMap<String, Resource>();
	private EventAdmin eventAdmin;

	public static final String PROPERTIES_EDITING_MODEL_NAME = "Generic Binding Settings";
	public static final String PROPERTIES_EDITING_MODEL_ID = "org.eclipse.emf.eef.runtime.ui.swt.genericBindingSetting";

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EPackage element) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return emfServiceProvider;
	}

	/**
	 * @param emfServiceProvider
	 *            EMFServiceProvider
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefToolkitProvider
	 *            EEFToolkitProvider
	 */
	public void setEEFToolkitProvider(EEFToolkitProvider eefToolkitProvider) {
		this.toolkitProvider = eefToolkitProvider;
	}

	/**
	 * @param eventAdmin
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModelEnvironment()
	 */
	public EditingModelEnvironment getEditingModelEnvironment() {
		if (editingModelEnvironment == null) {
			editingModelEnvironment = new EditingModelEnvironmentImpl(eventAdmin);
		}
		return editingModelEnvironment;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingModel getEEFDescription(EObject eObject) {
		return getEEFDescription(eObject.eClass());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEEFDescription(org.eclipse.emf.ecore.EClass)
	 */
	public PropertiesEditingModel getEEFDescription(EClass eClass) {
		PropertiesEditingModel propertiesEditingModel = null;
		ViewsRepository viewsRepository = null;
		initResourceSet();
		// get PropertiesEditingModel if exists, else create one.
		Resource resource = getPropertiesEditingModel(eClass);
		propertiesEditingModel = getPropertiesEditionModel(resource);
		viewsRepository = getViewsRepository(resource);
		// get bindings
		if (propertiesEditingModel != null && viewsRepository != null) {
			// define EClass and EStruturalFeature bindings if do not exist.
			updatePropertiesEditingModel(eClass, propertiesEditingModel, viewsRepository);
		}
		return propertiesEditingModel;
	}

	/**
	 * @param resource
	 * @return viewsRepository
	 */
	public ViewsRepository getViewsRepository(Resource resource) {
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
	public PropertiesEditingModel getPropertiesEditionModel(Resource resource) {
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
	protected void bindGenModel(EClass eObject, PropertiesEditingModel propertiesEditingModel) {
		URI uri = EcorePlugin.getEPackageNsURIToGenModelLocationMap().get(eObject.getEPackage().getNsURI());
		if (uri != null) {
			Resource genModelResource = getResourceSet().getResource(uri, true);
			if (!genModelResource.getContents().isEmpty()) {
				propertiesEditingModel.getInvolvedModels().add(genModelResource.getContents().get(0));
			}

		}
	}

	/**
	 * Defines bindings between EClass/ EStructuralFeatures and
	 * views/representations.
	 * 
	 * @param eObject
	 *            EObject
	 * @param propertiesEditingModel
	 *            PropertiesEditingModel
	 * @param viewsRepository
	 *            ViewsRepository
	 */
	public void updatePropertiesEditingModel(EClass eObject, PropertiesEditingModel propertiesEditingModel, ViewsRepository viewsRepository) {
		// create EClassBinding on eobject with its view
		BindingSettingsBuilder builder = new BindingSettingsBuilder(propertiesEditingModel, viewsRepository, toolkitProvider, GROUP_CONTAINER_NAME, TEXT_WIDGET_NAME, TEXTAREA_WIDGET_NAME);
		if (!builder.existEClassBinding(eObject)) {
			// create View
			org.eclipse.emf.eef.views.View createdView = builder.createViewForEClassBinding(eObject);

			// get eClass in environment resource set
			EPackage ePackage = getEPackageFromResourceSet(eObject);
			EClass eClass = getEMFServiceProvider().getEMFService(ePackage).mapEClass(ePackage, eObject);

			// create EClassBinding and link the createdView
			EClassBinding eClassBinding = builder.createEClassBinding(eClass, createdView);

			// bind eobject structural features
			builder.bindEStructuralFeature(eObject, eClassBinding, createdView, getEditingModelEnvironment());
		}

	}

	/**
	 * Get eObject ePackage
	 * 
	 * @param eObject
	 *            EObject
	 */
	protected EPackage getEPackageFromResourceSet(EClass eObject) {
		for (Resource resource : editingModelEnvironment.getResourceSet().getResources()) {
			for (EObject content : resource.getContents()) {
				if (content instanceof EPackage && ((EPackage) content).getNsURI().equals(eObject.getEPackage().getNsURI())) {
					return (EPackage) content;
				}
			}
		}
		return null;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @return the existing PropertiesEditingModel if exists, else create one.
	 */
	private Resource getPropertiesEditingModel(EClass eObject) {
		PropertiesEditingModel propertiesEditingModel = null;
		Resource resource = null;
		String uri = eObject.getEPackage().getNsURI();
		if (mapURI2PropertiesEditingModelResource.get(uri) == null) {
			propertiesEditingModel = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
			propertiesEditingModel.setId(PROPERTIES_EDITING_MODEL_ID);
			propertiesEditingModel.setName(PROPERTIES_EDITING_MODEL_NAME);
			propertiesEditingModel.setEMFServiceProvider(emfServiceProvider);
			resource = new ResourceImpl(URI.createURI(eObject.getEPackage().getNsURI() + ".editingModel"));
			resource.getContents().add(propertiesEditingModel);
			ViewsRepository viewsRepository = ViewsFactory.eINSTANCE.createViewsRepository();
			resource.getContents().add(viewsRepository);
			getResourceSet().getResources().add(resource);
			mapURI2PropertiesEditingModelResource.put(uri, resource);

			// bind genmodel if exist
			bindGenModel(eObject, propertiesEditingModel);
			EPackage ePackage = getEPackageFromResourceSet(eObject);
			propertiesEditingModel.getInvolvedModels().add(ePackage);
		} else {
			resource = mapURI2PropertiesEditingModelResource.get(uri);
			if (!getResourceSet().getResources().contains(resource)) {
				getResourceSet().getResources().add(resource);
			}
		}
		return resource;
	}

	/**
	 * @return the resource set.
	 */
	private ResourceSet initResourceSet() {
		if (resourceSet == null) {
			resourceSet = getEditingModelEnvironment().getResourceSet();
		}
		return resourceSet;
	}

	/**
	 * @return ResourceSet
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	/**
	 * @return map
	 */
	public Map<String, Resource> getMapURI2PropertiesEditingModel() {
		return mapURI2PropertiesEditingModelResource;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getViewHandler(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public ViewHandler<?> getViewHandler(View view) {
		return viewHandlerProvider.getViewHandler(view);
	}

	/**
	 * @param viewHandlerProvider
	 *            ViewHandlerProvider
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		return false;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#getEditingModel(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingModel getEditingModel(EPackage ePackage) {
		return getPropertiesEditionModel(mapURI2PropertiesEditingModelResource.get(ePackage.getNsURI()));
	}

}
