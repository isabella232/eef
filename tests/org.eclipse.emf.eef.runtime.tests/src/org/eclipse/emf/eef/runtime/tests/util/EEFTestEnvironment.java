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
package org.eclipse.emf.eef.runtime.tests.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.BindingHandlerProviderImpl;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl;
import org.eclipse.emf.eef.runtime.internal.binding.settings.EEFBindingSettingsProviderImpl;
import org.eclipse.emf.eef.runtime.internal.context.EditingContextFactoryProviderImpl;
import org.eclipse.emf.eef.runtime.internal.context.PropertiesEditingContextFactoryImpl;
import org.eclipse.emf.eef.runtime.internal.policies.PropertiesEditingPolicyProviderImpl;
import org.eclipse.emf.eef.runtime.internal.policies.processors.DirectEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.internal.policies.processors.DomainEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.internal.policies.processors.EditingPolicyProcessorProviderImpl;
import org.eclipse.emf.eef.runtime.internal.policies.request.EObjectEditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.internal.policies.request.EReferenceEditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.internal.policies.request.EditingPolicyRequestFactoryProviderImpl;
import org.eclipse.emf.eef.runtime.internal.util.EEFEditingServiceImpl;
import org.eclipse.emf.eef.runtime.internal.util.EEFEditingServiceProviderImpl;
import org.eclipse.emf.eef.runtime.internal.util.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.internal.util.EMFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.internal.view.handle.ViewHandlerProviderImpl;
import org.eclipse.emf.eef.runtime.internal.view.lock.impl.EEFLockManagerProviderImpl;
import org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl.EEFLockPolicyFactoryProviderImpl;
import org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl.EMFEditAwareLockPolicyFactory;
import org.eclipse.emf.eef.runtime.internal.view.notify.EEFNotifierProviderImpl;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessorProvider;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactoryProvider;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.internal.util.ViewServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandler;
import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.EEFToolkitProviderImpl;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.logging.E3EEFLogger;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.resources.E3ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.util.PlatformAwareViewServiceImpl;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.handle.editingview.PlatformAwarePropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesPlatformAwareToolkit;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.SWTPlatformAwareToolkit;
import org.eclipse.emf.eef.runtime.ui.swt.e3.viewer.E3EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.WizardEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request.EReferenceBatchWizardEditingPolicyRequest;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request.EReferenceDirectWizardEditingPolicyRequest;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request.EReferenceLiveWizardEditingPolicyRequest;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.lock.EditingViewLockManager;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify.EditingViewNotifier;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.runtime.view.lock.impl.NullLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EEFTestEnvironment {

	public static final String TESTS_EDITING_MODEL_ID = "org.eclipse.emf.eef.runtime.tests.model";
	public static final boolean FIRST_ECLASS_SAMPLE_ABSTRACTNESS = false;
	public static final String FIRST_ECLASS_SAMPLE_NAME = "EClass1";

	public static final String COMPONENT_NAME_KEY = "component.name";
	public static final String OBJECTCLASS_KEY = "objectClass";
	public static final String PRIORITY_OVER_KEY = "priority.over";

	public static final String REFLECT_VIEW_HANDLER_PROVIDER_NAME = "ReflectViewHandler";
	public static final String SWT_VIEW_HANDLER_PROVIDER_NAME = "SWTViewHandler";
	public static final String PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME = "PropertiesEditingViewHandler";

	private Builder builder;

	private EEFTestEnvironment(Builder builder) {
		this.builder = builder;
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getSampleModel()
	 */
	public EObject getSampleModel() {
		return builder.getSampleModel();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditedObject()
	 */
	public EObject getEditedObject() {
		return builder.getEditedObject();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		return builder.getAdapterFactory();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		return builder.getEditingModel();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return builder.getEMFServiceProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingContext()
	 */
	public PropertiesEditingContext getEditingContext() {
		return builder.getEditingContext();
	}

	public ViewServiceProvider getViewServiceProvider() {
		return builder.getViewServiceProvider();
	}

	public final static class Builder {

		private ResourceSet resourceSet;

		private EObject sampleModel;
		private EObject editedObject;
		private PropertiesEditingModel editingModel;
		private AdapterFactory adapterFactory;

		private EditingContextFactoryProvider contextFactoryProvider;
		private EMFServiceProvider emfServiceProvider;
		private ViewServiceProvider viewServiceProvider;
		private EEFEditingServiceProvider eefEditingServiceProvider;
		private EEFNotifierProvider eefNotifierProvider;
		private EEFLockPolicyFactoryProvider lockPolicyFactoryProvider;
		private EEFLockManagerProvider lockManagerProvider;
		private PropertiesEditingPolicyProvider editingPolicyProvider;
		private EditingPolicyRequestFactoryProvider policyRequestFactoryProvider;
		private EditingPolicyProcessorProvider policyProcessorProvider;

		private EEFToolkitProvider eefToolkitProvider;

		private PropertiesEditingContext editingContext;

		private EEFBindingSettingsProvider bindingSettingsProvider;
		private Collection<EEFServiceDescriptor<EEFBindingSettings>> bindingSettings;

		private BindingHandlerProvider bindingHandlerProvider;
		private ViewHandlerProvider viewHandlerProvider;

		private EditUIProvidersFactory editUIProvidersFactory;
		private ImageManager imageManager;

		private EEFLogger logger;

		public Builder() {
			sampleModel = null;
			editedObject = null;
			editingModel = null;
			adapterFactory = null;
			editingContext = null;
		}

		public ResourceSet getResourceSet() {
			if (resourceSet == null) {
				resourceSet = new ResourceSetImpl();
			}
			return resourceSet;
		}

		public EObject getSampleModel() {
			if (sampleModel == null) {
				sampleModel = createEcoreSampleModel();
			}
			return sampleModel;
		}

		public EObject getEditedObject() {
			if (editedObject == null) {
				editedObject = createEditedObject();
			}
			return editedObject;
		}

		public AdapterFactory getAdapterFactory() {
			if (adapterFactory == null) {
				adapterFactory = createAdapterFactory();
			}
			return adapterFactory;
		}

		public PropertiesEditingModel getEditingModel() {
			if (editingModel == null) {
				editingModel = createEditingModel();
			}
			return editingModel;
		}

		public EditingContextFactoryProvider getContextFactoryProvider() {
			if (contextFactoryProvider == null) {
				contextFactoryProvider = createEditingContextFactoryProvider();
			}
			return contextFactoryProvider;
		}

		public ViewHandlerProvider getViewHandlerProvider() {
			if (viewHandlerProvider == null) {
				viewHandlerProvider = createViewHandlerProvider();
			}
			return viewHandlerProvider;
		}

		public EMFServiceProvider getEMFServiceProvider() {
			if (emfServiceProvider == null) {
				emfServiceProvider = createEMFServiceProvider();
			}
			return emfServiceProvider;
		}

		public EEFEditingServiceProvider getEEFEditingServiceProvider() {
			if (eefEditingServiceProvider == null) {
				eefEditingServiceProvider = createEEFEditingServiceProvider();
			}
			return eefEditingServiceProvider;
		}

		public Collection<EEFServiceDescriptor<EEFBindingSettings>> getBindingSettings() {
			if (bindingSettings == null) {
				bindingSettings = createBindingSettings();
			}
			return bindingSettings;
		}

		public ViewServiceProvider getViewServiceProvider() {
			if (viewServiceProvider == null) {
				viewServiceProvider = createViewServiceProvider();
			}
			return viewServiceProvider;
		}

		public EEFNotifierProvider getEEFNotifierProvider() {
			if (eefNotifierProvider == null) {
				eefNotifierProvider = createEEFNotifierProvider();
			}
			return eefNotifierProvider;
		}

		public EEFLockPolicyFactoryProvider getLockPolicyFactoryProvider() {
			if (lockPolicyFactoryProvider == null) {
				lockPolicyFactoryProvider = createLockPolicyFactoryProvider();
			}
			return lockPolicyFactoryProvider;
		}

		public EEFLockManagerProvider getLockManagerProvider() {
			if (lockManagerProvider == null) {
				lockManagerProvider = createLockManagerProvider();
			}
			return lockManagerProvider;
		}

		public EEFLogger getLogger() {
			if (logger == null) {
				logger = createLogger();
			}
			return logger;
		}

		public EEFToolkitProvider getEEFToolkitProvider() {
			if (eefToolkitProvider == null) {
				eefToolkitProvider = createEEFToolkitProvider();
			}
			return eefToolkitProvider;
		}

		/**
		 * @param eefNotifierProvider
		 *            the eefNotifierProvider to set
		 */
		public void setEEFNotifierProvider(EEFNotifierProvider eefNotifierProvider) {
			this.eefNotifierProvider = eefNotifierProvider;
		}

		public PropertiesEditingPolicyProvider getEditingPolicyProvider() {
			if (editingPolicyProvider == null) {
				editingPolicyProvider = createPolicyProvider().iterator().next().service;
			}
			return editingPolicyProvider;
		}

		public EditingPolicyRequestFactoryProvider getPolicyRequestFactoryProvider() {
			if (policyRequestFactoryProvider == null) {
				policyRequestFactoryProvider = createPolicyRequestFactoryProvider();
			}
			return policyRequestFactoryProvider;
		}

		public EditingPolicyProcessorProvider getPolicyProcessorProvider() {
			if (policyProcessorProvider == null) {
				policyProcessorProvider = createPolicyProcessorProvider();
			}
			return policyProcessorProvider;
		}

		public EEFBindingSettingsProvider getBindingSettingsProvider() {
			if (bindingSettingsProvider == null) {
				bindingSettingsProvider = createBindingSettingsProvider();
			}
			return bindingSettingsProvider;
		}

		public BindingHandlerProvider getBindingHandlerProvider() {
			if (bindingHandlerProvider == null) {
				bindingHandlerProvider = createBindingHandlerProvider();
			}
			return bindingHandlerProvider;
		}

		public EditUIProvidersFactory getEditUIProvidersFactory() {
			if (editUIProvidersFactory == null) {
				editUIProvidersFactory = createProviderFactory();
			}
			return editUIProvidersFactory;
		}

		public ImageManager getImageManager() {
			if (imageManager == null) {
				imageManager = createImageManager();
			}
			return imageManager;
		}

		public PropertiesEditingContext getEditingContext() {
			if (editingContext == null) {
				editingContext = createEditingContext();
			}
			return editingContext;
		}

		/**
		 * @param sampleModel
		 *            the sampleModel to set
		 * @return
		 */
		public Builder setSampleModel(EObject sampleModel) {
			this.sampleModel = sampleModel;
			return this;
		}

		/**
		 * @param editedObject
		 *            the editedObject to set
		 * @return
		 */
		public Builder setEditedObject(EObject editedObject) {
			this.editedObject = editedObject;
			return this;
		}

		/**
		 * @param editingModel
		 *            the editingModel to set
		 */
		public Builder setEditingModel(PropertiesEditingModel editingModel) {
			this.editingModel = editingModel;
			return this;
		}

		/**
		 * @param adapterFactory
		 *            the adapterFactory to set
		 */
		public Builder setAdapterFactory(AdapterFactory adapterFactory) {
			this.adapterFactory = adapterFactory;
			return this;
		}

		/**
		 * @param bindingSettings
		 *            the bindingSettings to set
		 */
		public Builder setBindingSettings(Collection<EEFServiceDescriptor<EEFBindingSettings>> bindingSettings) {
			this.bindingSettings = bindingSettings;
			return this;
		}

		/**
		 * @param factoryProvider
		 */
		public Builder setLockPoliciesFactoryProvider(EEFLockPolicyFactoryProvider factoryProvider) {
			this.lockPolicyFactoryProvider = factoryProvider;
			return this;
		}

		public Builder setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
			this.lockManagerProvider = lockManagerProvider;
			return this;
		}

		/**
		 * @param editingContext
		 *            the editingContext to set
		 */
		public Builder setEditingContext(PropertiesEditingContext editingContext) {
			this.editingContext = editingContext;
			return this;
		}

		public EEFTestEnvironment build() {
			return new EEFTestEnvironment(this);
		}

		/*
		 * ======================================================================
		 * ====
		 * 
		 * Default creation methods
		 * 
		 * ======================================================================
		 * ====
		 */
		public EPackage createEcoreSampleModel() {
			Resource ecoreResource = getResourceSet().createResource(URI.createURI("eef://sample.ecore"));
			EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
			ePackage.setName("sample");
			ePackage.setNsPrefix("eef-sample");
			ePackage.setNsURI("http://eef/sample");
			EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			eClass.setName(FIRST_ECLASS_SAMPLE_NAME);
			eClass.setAbstract(FIRST_ECLASS_SAMPLE_ABSTRACTNESS);
			ePackage.getEClassifiers().add(eClass);
			EClass eClass2 = EcoreFactory.eINSTANCE.createEClass();
			eClass2.setName("EClass2");
			ePackage.getEClassifiers().add(eClass2);
			eClass.getESuperTypes().add(eClass2);
			EClass eClass3 = EcoreFactory.eINSTANCE.createEClass();
			eClass3.setName("EClass3");
			ePackage.getEClassifiers().add(eClass3);
			eClass.getESuperTypes().add(eClass3);
			EClass eClass4 = EcoreFactory.eINSTANCE.createEClass();
			eClass4.setName("EClass4");
			ePackage.getEClassifiers().add(eClass4);
			ecoreResource.getContents().add(ePackage);

			Resource ecoreResource2 = getResourceSet().createResource(URI.createURI("eef://sample2.ecore"));
			EPackage ePackage2 = EcoreFactory.eINSTANCE.createEPackage();
			ePackage2.setName("sample2");
			ePackage2.setNsPrefix("eef-sample2");
			ePackage2.setNsURI("http://eef/sample2");
			EClass eClass5 = EcoreFactory.eINSTANCE.createEClass();
			eClass5.setName("EClass5");
			eClass5.setAbstract(true);
			ePackage2.getEClassifiers().add(eClass5);
			ecoreResource2.getContents().add(ePackage2);

			return ePackage;
		}

		public EObject createEditedObject() {
			EObject sampleModel = getSampleModel();
			if (sampleModel instanceof EPackage) {
				return ((EPackage) sampleModel).getEClassifiers().get(0);
			} else {
				return sampleModel;
			}
		}

		public AdapterFactory createAdapterFactory() {
			return new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}

		public PropertiesEditingModel createEditingModel() {
			return new EditingModelBuilder(TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS).withView(EClassMockView.class).build();
		}

		/**
		 * Build a {@link PropertiesEditingModel} with simple SWT views for the
		 * EEF tests.
		 * 
		 * @return a sample {@link PropertiesEditingModel}.
		 */
		public PropertiesEditingModel createEditingModelWithSWTViews() {
			return new EditingModelBuilder(TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS).withView(SampleView.class).bindClass(EcorePackage.Literals.EPACKAGE).withView(RootView.class).build();
		}

		public PropertiesEditingModel createEditingModelWithPropertiesEditingViews() {
			List<Toolkit> toolkits = new ArrayList<Toolkit>();
			ResourceSet rset = new ResourceSetImpl();
			Resource resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui.swt/org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui.swt/org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			List<View> views = createEcoreViews(toolkits);
			return new EditingModelBuilder(TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS).withView(views.get(0)).withView(views.get(1)).bindProperty(EcorePackage.Literals.ECLASSIFIER__DEFAULT_VALUE).withEditor(views.get(1).getElements().get(0)).build();
		}

		public PropertiesEditingModel createEditingModelWithContainersPropertiesEditingViews() {
			List<Toolkit> toolkits = new ArrayList<Toolkit>();
			ResourceSet rset = new ResourceSetImpl();
			Resource resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui.swt/org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			List<View> views = createEcoreViewsWithContainers(toolkits);
			return new EditingModelBuilder(TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS).withView(views.get(0)).build();
		}

		public List<View> createEcoreViewsWithContainers(List<Toolkit> toolkits) {
			List<View> result = new ArrayList<View>();
			View eClassView = ViewsFactory.eINSTANCE.createView();
			eClassView.setName("EClass");
			Container container1 = ViewsFactory.eINSTANCE.createContainer();
			container1.setName("Container1");
			Widget groupWidget = searchWidget(toolkits.get(0), "Group");
			container1.setRepresentation(groupWidget);
			Container subContainer = ViewsFactory.eINSTANCE.createContainer();
			subContainer.setName("SubContainer");
			subContainer.setRepresentation(groupWidget);
			container1.getElements().add(subContainer);
			ElementEditor nameEditor = ViewsFactory.eINSTANCE.createElementEditor();
			nameEditor.setName("name");
			nameEditor.setRepresentation(searchWidget(toolkits.get(0), "Text"));
			subContainer.getElements().add(nameEditor);
			eClassView.getElements().add(container1);
			Container container2 = ViewsFactory.eINSTANCE.createContainer();
			container2.setName("Container2");
			container2.setRepresentation(groupWidget);
			ElementEditor abstractEditor = ViewsFactory.eINSTANCE.createElementEditor();
			abstractEditor.setName("abstract");
			abstractEditor.setRepresentation(searchWidget(toolkits.get(0), "Checkbox"));
			container2.getElements().add(abstractEditor);
			eClassView.getElements().add(container2);
			result.add(eClassView);
			return result;
		}

		// TODO: Robustness on widget finding ...
		public List<View> createEcoreViews(List<Toolkit> toolkits) {
			List<View> result = new ArrayList<View>();
			View eClassView = ViewsFactory.eINSTANCE.createView();
			eClassView.setName("EClass");
			ElementEditor nameEditor = ViewsFactory.eINSTANCE.createElementEditor();
			nameEditor.setName("name");
			nameEditor.setRepresentation(searchWidget(toolkits.get(0), "Text"));
			eClassView.getElements().add(nameEditor);
			ElementEditor abstractEditor = ViewsFactory.eINSTANCE.createElementEditor();
			abstractEditor.setName("abstract");
			abstractEditor.setRepresentation(searchWidget(toolkits.get(0), "Checkbox"));
			eClassView.getElements().add(abstractEditor);
			ElementEditor superTypes = ViewsFactory.eINSTANCE.createElementEditor();
			superTypes.setName("eSuperTypes");
			superTypes.setRepresentation(searchWidget(toolkits.get(1), "EReference Editor"));
			eClassView.getElements().add(superTypes);
			result.add(eClassView);
			View eClassInstanceView = ViewsFactory.eINSTANCE.createView();
			eClassInstanceView.setName("Instance");
			ElementEditor instanceTypeName = ViewsFactory.eINSTANCE.createElementEditor();
			instanceTypeName.setName("instance Type Name");
			instanceTypeName.setRepresentation(searchWidget(toolkits.get(0), "Text"));
			eClassInstanceView.getElements().add(instanceTypeName);
			result.add(eClassInstanceView);
			return result;
		}

		public Collection<EEFServiceDescriptor<EMFService>> createEMFServices() {
			Collection<EEFServiceDescriptor<EMFService>> services = new ArrayList<EEFServiceDescriptor<EMFService>>();
			EEFServiceDescriptor<EMFService> descriptor = new EEFServiceDescriptor<EMFService>("emfservice.default", new EMFServiceImpl());
			services.add(descriptor);
			return services;
		}

		public Collection<EEFServiceDescriptor<EEFEditingService>> createEEFEditingServices() {
			Collection<EEFServiceDescriptor<EEFEditingService>> services = new ArrayList<EEFServiceDescriptor<EEFEditingService>>();
			EEFEditingServiceImpl service = new EEFEditingServiceImpl();
			service.setEMFServiceProvider(getEMFServiceProvider());
			EEFServiceDescriptor<EEFEditingService> descriptor = new EEFServiceDescriptor<EEFEditingService>(EEFEditingServiceImpl.class.getName(), service);
			services.add(descriptor);
			return services;
		}

		public Collection<EEFServiceDescriptor<EditingPolicyRequestFactory>> createPolicyRequestFactories() {
			Collection<EEFServiceDescriptor<EditingPolicyRequestFactory>> services = new ArrayList<EEFServiceDescriptor<EditingPolicyRequestFactory>>();
			EObjectEditingPolicyRequestFactory eObjectEditingPolicyRequestFactory = new EObjectEditingPolicyRequestFactory();
			EEFServiceDescriptor<EditingPolicyRequestFactory> descriptor = new EEFServiceDescriptor<EditingPolicyRequestFactory>(EObjectEditingPolicyRequestFactory.class.getName(), eObjectEditingPolicyRequestFactory);
			services.add(descriptor);
			EReferenceEditingPolicyRequestFactory eReferenceEditingPolicyRequestFactory = new EReferenceEditingPolicyRequestFactory();
			eReferenceEditingPolicyRequestFactory.setEMFServiceProvider(getEMFServiceProvider());
			eReferenceEditingPolicyRequestFactory.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			descriptor = new EEFServiceDescriptor<EditingPolicyRequestFactory>(EReferenceEditingPolicyRequestFactory.class.getName(), eReferenceEditingPolicyRequestFactory, EObjectEditingPolicyRequestFactory.class.getName());
			services.add(descriptor);
			EReferenceDirectWizardEditingPolicyRequest eReferenceDirectWizardEditingPolicyRequest = new EReferenceDirectWizardEditingPolicyRequest();
			eReferenceDirectWizardEditingPolicyRequest.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			descriptor = new EEFServiceDescriptor<EditingPolicyRequestFactory>(EReferenceDirectWizardEditingPolicyRequest.class.getName(), eReferenceDirectWizardEditingPolicyRequest, EReferenceEditingPolicyRequestFactory.class.getName());
			services.add(descriptor);
			EReferenceBatchWizardEditingPolicyRequest eReferenceBatchWizardEditingPolicyRequest = new EReferenceBatchWizardEditingPolicyRequest();
			eReferenceBatchWizardEditingPolicyRequest.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			descriptor = new EEFServiceDescriptor<EditingPolicyRequestFactory>(EReferenceBatchWizardEditingPolicyRequest.class.getName(), eReferenceBatchWizardEditingPolicyRequest, EReferenceEditingPolicyRequestFactory.class.getName());
			services.add(descriptor);
			EReferenceLiveWizardEditingPolicyRequest eReferenceLiveWizardEditingPolicyRequest = new EReferenceLiveWizardEditingPolicyRequest();
			eReferenceLiveWizardEditingPolicyRequest.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			descriptor = new EEFServiceDescriptor<EditingPolicyRequestFactory>(EReferenceLiveWizardEditingPolicyRequest.class.getName(), eReferenceLiveWizardEditingPolicyRequest, EReferenceEditingPolicyRequestFactory.class.getName());
			services.add(descriptor);
			return services;
		}

		public Collection<EEFServiceDescriptor<EditingPolicyProcessor>> createPolicyProcessors() {
			Collection<EEFServiceDescriptor<EditingPolicyProcessor>> services = new ArrayList<EEFServiceDescriptor<EditingPolicyProcessor>>();
			EEFServiceDescriptor<EditingPolicyProcessor> descriptor = new EEFServiceDescriptor<EditingPolicyProcessor>(DirectEditingPolicyProcessor.class.getName(), new DirectEditingPolicyProcessor());
			services.add(descriptor);
			descriptor = new EEFServiceDescriptor<EditingPolicyProcessor>(DomainEditingPolicyProcessor.class.getName(), new DomainEditingPolicyProcessor(), DirectEditingPolicyProcessor.class.getName());
			services.add(descriptor);
			descriptor = new EEFServiceDescriptor<EditingPolicyProcessor>(WizardEditingPolicyProcessor.class.getName(), new WizardEditingPolicyProcessor(), DomainEditingPolicyProcessor.class.getName(), DirectEditingPolicyProcessor.class.getName());
			services.add(descriptor);
			return services;
		}

		public Collection<EEFServiceDescriptor<EEFLockPolicyFactory>> createLockPolicyFactories() {
			Collection<EEFServiceDescriptor<EEFLockPolicyFactory>> services = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<EEFLockPolicyFactory>>();
			EEFServiceDescriptor<EEFLockPolicyFactory> descriptor = new EEFServiceDescriptor<EEFLockPolicyFactory>(EMFEditAwareLockPolicyFactory.class.getName(), new EMFEditAwareLockPolicyFactory());
			services.add(descriptor);
			return services;
		}

		public Collection<EEFServiceDescriptor<ViewService>> createViewServices() {
			Collection<EEFServiceDescriptor<ViewService>> result = new ArrayList<EEFServiceDescriptor<ViewService>>();
			PlatformAwareViewServiceImpl viewService = new PlatformAwareViewServiceImpl();
			viewService.setEMFServiceProvider(getEMFServiceProvider());
			viewService.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			result.add(new EEFServiceDescriptor<ViewService>("viewservice.default", viewService));
			return result;
		}

		public Collection<EEFServiceDescriptor<EEFToolkit<Composite>>> createEditorProviders() {
			Collection<EEFServiceDescriptor<EEFToolkit<Composite>>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<EEFToolkit<Composite>>>();
			SWTPlatformAwareToolkit swtToolkit = new SWTPlatformAwareToolkit();
			swtToolkit.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			swtToolkit.setEditUIProvidersFactory(getEditUIProvidersFactory());
			result.add(new EEFServiceDescriptor<EEFToolkit<Composite>>("toolkitservice.swt", swtToolkit));
			EMFPropertiesPlatformAwareToolkit emfPropertiesToolkit = new EMFPropertiesPlatformAwareToolkit();
			emfPropertiesToolkit.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			emfPropertiesToolkit.setEditUIProvidersFactory(getEditUIProvidersFactory());
			emfPropertiesToolkit.setImageManager(getImageManager());
			result.add(new EEFServiceDescriptor<EEFToolkit<Composite>>("toolkitservice.emfproperties", emfPropertiesToolkit));
			return result;
		}

		public Collection<EEFServiceDescriptor<EEFBindingSettings>> createBindingSettings() {
			Collection<EEFServiceDescriptor<EEFBindingSettings>> result = new ArrayList<EEFServiceDescriptor<EEFBindingSettings>>();
			EEFBindingSettingsImpl bindingSettings = new EEFBindingSettingsImpl() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl#getEditingModel()
				 */
				@Override
				protected PropertiesEditingModel getEditingModel() {
					PropertiesEditingModel editingModel = Builder.this.getEditingModel();
					Resource resource = getResourceSet().createResource(URI.createURI("file://model.editingModel"));
					resource.getContents().add(editingModel);
					return editingModel;
				}

			};
			bindingSettings.setEMFServiceProvider(getEMFServiceProvider());
			// bindingSettings.setViewHandlerProvider(getViewHandlerProvider());
			result.add(new EEFServiceDescriptor<EEFBindingSettings>("propertieseditingprovider.default", bindingSettings));
			return result;
		}

		public ViewHandlerProvider createViewHandlerProvider() {
			ViewHandlerProviderImpl result = new ViewHandlerProviderImpl();
			for (EEFServiceDescriptor<ViewHandler<?>> eefServiceDescriptor : createVHandlerFactories()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					if (eefServiceDescriptor.hasPriorityOver != null && !eefServiceDescriptor.hasPriorityOver.isEmpty()) {
						Iterator<String> iterator = eefServiceDescriptor.hasPriorityOver.iterator();
						StringBuilder sb = new StringBuilder(iterator.next());
						while (iterator.hasNext()) {
							sb.append(',');
							sb.append(iterator.next());
						}
						properties.put(EEFTestEnvironment.PRIORITY_OVER_KEY, sb.toString());
					}
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public Collection<EEFServiceDescriptor<ViewHandler<?>>> createVHandlerFactories() {
			Collection<EEFServiceDescriptor<ViewHandler<?>>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<ViewHandler<?>>>();
			ReflectViewHandler<?> service = new ReflectViewHandler<Object>();
			service.setLockManagerProvider(getLockManagerProvider());
			service.setLogger(getLogger());
			EEFServiceDescriptor<ViewHandler<?>> desc = new EEFServiceDescriptor<ViewHandler<?>>(REFLECT_VIEW_HANDLER_PROVIDER_NAME, service);
			result.add(desc);
			SWTViewHandler service2 = new SWTViewHandler();
			service2.setLockManagerProvider(getLockManagerProvider());
			service2.setLogger(getLogger());
			desc = new EEFServiceDescriptor<ViewHandler<?>>(SWT_VIEW_HANDLER_PROVIDER_NAME, service2, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
			result.add(desc);
			PlatformAwarePropertiesEditingViewHandler service3 = new PlatformAwarePropertiesEditingViewHandler();
			service3.setViewServiceProvider(getViewServiceProvider());
			service3.setEEFToolkitProvider(getEEFToolkitProvider());
			service3.setLockManagerProvider(getLockManagerProvider());
			service3.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			service3.setLogger(getLogger());
			service3.setEMFServiceProvider(getEMFServiceProvider());
			desc = new EEFServiceDescriptor<ViewHandler<?>>(PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME, service3, SWT_VIEW_HANDLER_PROVIDER_NAME);
			result.add(desc);
			return result;
		}

		public Collection<EEFServiceDescriptor<EEFNotifier>> createNotifiers() {
			Collection<EEFServiceDescriptor<EEFNotifier>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<EEFNotifier>>();
			EEFServiceDescriptor<EEFNotifier> desc = new EEFServiceDescriptor<EEFNotifier>("notifier.default", new EditingViewNotifier());
			result.add(desc);
			return result;
		}

		public Collection<EEFServiceDescriptor<EEFLockManager>> createLockManagers() {
			Collection<EEFServiceDescriptor<EEFLockManager>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<EEFLockManager>>();
			EditingViewLockManager lockManager = new EditingViewLockManager();
			lockManager.setEEFNotifierProvider(getEEFNotifierProvider());
			lockManager.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			EEFServiceDescriptor<EEFLockManager> desc = new EEFServiceDescriptor<EEFLockManager>("lockmanager.editingview", lockManager);
			result.add(desc);
			desc = new EEFServiceDescriptor<EEFLockManager>("lockmanager.default", new NullLockManager());
			result.add(desc);
			return result;
		}

		public Collection<EEFServiceDescriptor<PropertiesEditingPolicyProvider>> createPolicyProvider() {
			Collection<EEFServiceDescriptor<PropertiesEditingPolicyProvider>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<PropertiesEditingPolicyProvider>>();
			PropertiesEditingPolicyProviderImpl policyProvider = new PropertiesEditingPolicyProviderImpl();
			policyProvider.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
			policyProvider.setEditingPolicyRequestFactoryProvider(getPolicyRequestFactoryProvider());
			policyProvider.setEditingPolicyProcessorProvider(getPolicyProcessorProvider());
			EEFServiceDescriptor<PropertiesEditingPolicyProvider> desc = new EEFServiceDescriptor<PropertiesEditingPolicyProvider>("propertieseditingpolicyprovider.default", policyProvider);
			result.add(desc);
			return result;
		}

		public Collection<EEFServiceDescriptor<PropertiesEditingContextFactory>> createContextFactory() {
			Collection<EEFServiceDescriptor<PropertiesEditingContextFactory>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<PropertiesEditingContextFactory>>();
			PropertiesEditingContextFactoryImpl contextFactory = new PropertiesEditingContextFactoryImpl();
			contextFactory.setEMFServiceProvider(getEMFServiceProvider());
			contextFactory.setBindingManagerProvider(getBindingHandlerProvider());
			contextFactory.setViewHandlerProvider(getViewHandlerProvider());
			EEFServiceDescriptor<PropertiesEditingContextFactory> desc = new EEFServiceDescriptor<PropertiesEditingContextFactory>("propertieseditingcontextfactory.default", contextFactory);
			result.add(desc);
			return result;
		}

		public Collection<EEFServiceDescriptor<EEFEditingService>> createEditingService() {
			Collection<EEFServiceDescriptor<EEFEditingService>> result = new ArrayList<EEFTestEnvironment.EEFServiceDescriptor<EEFEditingService>>();
			EEFEditingServiceImpl service = new EEFEditingServiceImpl();
			service.setEMFServiceProvider(getEMFServiceProvider());
			EEFServiceDescriptor<EEFEditingService> desc = new EEFServiceDescriptor<EEFEditingService>("eefeditingservice.default", service);
			result.add(desc);
			return result;
		}

		public EditUIProvidersFactory createProviderFactory() {
			return new E3EditUIProvidersFactory();
		}

		public ImageManager createImageManager() {
			return new E3ImageManager();
		}

		public EMFServiceProvider createEMFServiceProvider() {
			EMFServiceProviderImpl result = new EMFServiceProviderImpl();
			for (EEFServiceDescriptor<EMFService> eefServiceDescriptor : createEMFServices()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		private ViewServiceProvider createViewServiceProvider() {
			ViewServiceProviderImpl result = new ViewServiceProviderImpl();
			for (EEFServiceDescriptor<ViewService> eefServiceDescriptor : createViewServices()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public EEFEditingServiceProvider createEEFEditingServiceProvider() {
			EEFEditingServiceProviderImpl result = new EEFEditingServiceProviderImpl();
			for (EEFServiceDescriptor<EEFEditingService> eefServiceDescriptor : createEEFEditingServices()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public EEFNotifierProvider createEEFNotifierProvider() {
			EEFNotifierProviderImpl result = new EEFNotifierProviderImpl();
			for (EEFServiceDescriptor<EEFNotifier> eefServiceDescriptor : createNotifiers()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public EEFLockPolicyFactoryProvider createLockPolicyFactoryProvider() {
			EEFLockPolicyFactoryProviderImpl factory = new EEFLockPolicyFactoryProviderImpl();
			for (EEFServiceDescriptor<EEFLockPolicyFactory> eefServiceDescriptor : createLockPolicyFactories()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					factory.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					// Shouldn't occur
				}
			}
			return factory;
		}

		public EEFLockManagerProvider createLockManagerProvider() {
			EEFLockManagerProviderImpl managerProvider = new EEFLockManagerProviderImpl();
			for (EEFServiceDescriptor<EEFLockManager> eefServiceDescriptor : createLockManagers()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					managerProvider.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					// Shouldn't occur
				}
			}
			return managerProvider;
		}

		public EEFLogger createLogger() {
			return new E3EEFLogger();
		}

		public EEFToolkitProvider createEEFToolkitProvider() {
			EEFToolkitProviderImpl result = new EEFToolkitProviderImpl();

			for (EEFServiceDescriptor<EEFToolkit<Composite>> eefServiceDescriptor : createEditorProviders()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					// Shouldn't occur
				}
			}
			return result;
		}

		public EditingPolicyRequestFactoryProvider createPolicyRequestFactoryProvider() {
			EditingPolicyRequestFactoryProviderImpl result = new EditingPolicyRequestFactoryProviderImpl();
			for (EEFServiceDescriptor<EditingPolicyRequestFactory> eefServiceDescriptor : createPolicyRequestFactories()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public EditingPolicyProcessorProvider createPolicyProcessorProvider() {
			EditingPolicyProcessorProviderImpl result = new EditingPolicyProcessorProviderImpl();
			for (EEFServiceDescriptor<EditingPolicyProcessor> eefServiceDescriptor : createPolicyProcessors()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					if (eefServiceDescriptor.hasPriorityOver != null && !eefServiceDescriptor.hasPriorityOver.isEmpty()) {
						properties.put(EEFTestEnvironment.PRIORITY_OVER_KEY, eefServiceDescriptor.hasPriorityOver.get(0));
					}
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		private EEFBindingSettingsProvider createBindingSettingsProvider() {
			EEFBindingSettingsProviderImpl result = new EEFBindingSettingsProviderImpl();
			for (EEFServiceDescriptor<EEFBindingSettings> eefServiceDescriptor : getBindingSettings()) {
				try {
					Map<String, String> properties = new HashMap<String, String>();
					properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
					result.addService(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		public BindingHandlerProvider createBindingHandlerProvider() {
			BindingHandlerProviderImpl result = new BindingHandlerProviderImpl();
			try {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, PropertiesBindingHandlerImpl.class.getName());
				PropertiesBindingHandlerImpl service = new PropertiesBindingHandlerImpl();
				service.setEMFServiceProvider(getEMFServiceProvider());
				service.setEEFEditingServiceProvider(getEEFEditingServiceProvider());
				service.setEditingPolicyProvider(getEditingPolicyProvider());
				service.setEEFNotifierProvider(getEEFNotifierProvider());
				service.setLockPolicyFactoryProvider(getLockPolicyFactoryProvider());
				service.setLockManagerProvider(getLockManagerProvider());
				service.setBindingSettingsProvider(getBindingSettingsProvider());
				service.setEventAdmin(new EEFTestsEventAdmin(service));
				service.setViewHandlerProvider(getViewHandlerProvider());
				result.addService(service, properties);
			} catch (PriorityCircularityException e) {
				e.printStackTrace();
			}
			return result;
		}

		public EditingContextFactoryProvider createEditingContextFactoryProvider() {
			EditingContextFactoryProviderImpl result = new EditingContextFactoryProviderImpl();
			for (EEFServiceDescriptor<PropertiesEditingContextFactory> eefServiceDescriptor : createContextFactory()) {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put(EEFTestEnvironment.COMPONENT_NAME_KEY, eefServiceDescriptor.name);
				try {
					result.addContextFactory(eefServiceDescriptor.service, properties);
				} catch (PriorityCircularityException e) {
					e.printStackTrace();
				}
			}

			return result;
		}

		public PropertiesEditingContext createEditingContext() {
			return getContextFactoryProvider().getEditingContextFactory(getEditedObject()).createPropertiesEditingContext(getAdapterFactory(), getEditedObject());
		}

		private Widget searchWidget(Toolkit toolkit, String name) {
			TreeIterator<EObject> eAllContents = toolkit.eAllContents();
			while (eAllContents.hasNext()) {
				EObject next = eAllContents.next();
				if (next instanceof Widget && name.equals(((Widget) next).getName())) {
					return (Widget) next;
				}
			}
			return null;
		}

	}

	public static class EEFServiceDescriptor<T extends EEFService<?>> {

		private T service;
		private String name;
		private List<String> hasPriorityOver;

		public EEFServiceDescriptor(String name, T service) {
			this.name = name;
			this.service = service;
			this.hasPriorityOver = new ArrayList<String>();
		}

		public EEFServiceDescriptor(String name, T service, String... hasPriorityOver) {
			this.service = service;
			this.name = name;
			this.hasPriorityOver = Arrays.asList(hasPriorityOver);
		}

	}

	public static final class EEFTestsEventAdmin implements EventAdmin {

		private PropertiesBindingHandlerImpl owner;

		/**
		 * @param owner
		 */
		public EEFTestsEventAdmin(PropertiesBindingHandlerImpl owner) {
			this.owner = owner;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.osgi.service.event.EventAdmin#postEvent(org.osgi.service.event.Event)
		 */
		public void postEvent(Event event) {
			owner.handleEvent(event);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.osgi.service.event.EventAdmin#sendEvent(org.osgi.service.event.Event)
		 */
		public void sendEvent(Event event) {
		}

	}

}
