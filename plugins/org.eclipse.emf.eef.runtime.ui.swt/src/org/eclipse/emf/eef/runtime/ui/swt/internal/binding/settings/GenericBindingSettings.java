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

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.editingModel.EditingModelEnvironmentImpl;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.util.BindingSettingsBuilder;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitHandler;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
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
	public static final String MULTI_VALUED_EDITOR_WIDGET_NAME = "MultiValued Attribute Editor";
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
	private ToolkitHandler toolkitHandler;

	public static final String PROPERTIES_EDITING_MODEL_NAME = "Binding Settings";
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
		propertiesEditingModel = GenericBindingSettingsUtil.getPropertiesEditionModel(resource);
		viewsRepository = GenericBindingSettingsUtil.getViewsRepository(resource);
		// get bindings
		if (propertiesEditingModel != null && viewsRepository != null) {
			// define EClass and EStruturalFeature bindings if do not exist.
			updatePropertiesEditingModel(eClass, propertiesEditingModel, viewsRepository);
		}
		return propertiesEditingModel;
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
		BindingSettingsBuilder builder = new BindingSettingsBuilder(propertiesEditingModel, viewsRepository, getToolkitHandler(), GROUP_CONTAINER_NAME, TEXT_WIDGET_NAME, TEXTAREA_WIDGET_NAME);
		if (!builder.existEClassBinding(eObject)) {
			// create View
			org.eclipse.emf.eef.views.View createdView = builder.createViewForEClassBinding(eObject);

			// get eClass in environment resource set
			EPackage ePackage = GenericBindingSettingsUtil.getEPackageFromResourceSet(eObject.getEPackage(), getEditingModelEnvironment());
			EClass eClass = getEMFServiceProvider().getEMFService(ePackage).mapEClass(ePackage, eObject);

			// create EClassBinding and link the createdView
			EClassBinding eClassBinding = builder.createEClassBinding(eClass, createdView);

			// bind eobject structural features
			builder.bindEStructuralFeature(eObject, eClassBinding, createdView, getEditingModelEnvironment());
		}

	}

	private ToolkitHandler getToolkitHandler() {
		if (toolkitHandler == null) {
			toolkitHandler = toolkitProvider.createHandler(resourceSet);
		}
		return toolkitHandler;
	}

	/**
	 * @param eObject
	 *            EObject
	 * @return the existing PropertiesEditingModel if exists, else create one.
	 */
	private Resource getPropertiesEditingModel(EClass eObject) {
		String uri = eObject.getEPackage().getNsURI();
		Resource resource = null;
		if (mapURI2PropertiesEditingModelResource.get(uri) == null) {
			resource = GenericBindingSettingsUtil.initPropertiesEditingModel(eObject.getEPackage(), getEMFServiceProvider(), getEditingModelEnvironment());
			getResourceSet().getResources().add(resource);
			mapURI2PropertiesEditingModelResource.put(uri, resource);
		} else {
			resource = mapURI2PropertiesEditingModelResource.get(uri);
			if (!getResourceSet().getResources().contains(resource)) {
				getResourceSet().getResources().add(resource);
			}
			// load ecore in resource if necessary
			if (eObject.eResource().getURI() != null) {
				Resource resource2 = getResourceSet().getResource(eObject.eResource().getURI(), true);
				if (!getResourceSet().getResources().contains(resource2)) {
					getResourceSet().getResources().add(resource2);
				}
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
		return GenericBindingSettingsUtil.getPropertiesEditionModel(mapURI2PropertiesEditingModelResource.get(ePackage.getNsURI()));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#loadClass(java.lang.String)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public Class<PropertyBindingCustomizer> loadClass(String className) {
		Class<PropertyBindingCustomizer> result = null;
		try {
			return (Class<PropertyBindingCustomizer>) EEFRuntimeUISWT.getPlugin().getBundle().loadClass(className);
		} catch (ClassNotFoundException e) {
			EEFRuntimeUISWT.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntimeUISWT.PLUGIN_ID, "Error when loading binding customizer class : " + className, e));
			return null;
		}
	}
}
