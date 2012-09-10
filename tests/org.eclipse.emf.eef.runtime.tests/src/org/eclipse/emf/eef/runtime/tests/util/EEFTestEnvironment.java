/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
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
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.impl.PropertiesEditingContextFactoryImpl;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.services.editingProvider.PropertiesEditingProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.ViewHandlerProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;
import org.eclipse.emf.eef.runtime.tests.views.EClassMockView;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.impl.PropertyEditorProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceRegistryImpl;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestEnvironment {

	public static final boolean FIRST_ECLASS_SAMPLE_ABSTRACTNESS = false;
	public static final String FIRST_ECLASS_SAMPLE_NAME = "EClass1";

	public static final String COMPONENT_NAME_KEY = "component.name";
	public static final String PRIORITY_OVER_KEY = "priority.over";

	public static final String REFLECT_VIEW_HANDLER_PROVIDER_NAME = "ReflectViewHandlerProvider";
	public static final String SWT_VIEW_HANDLER_PROVIDER_NAME = "SWTViewHandlerProvider";
	public static final String PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME = "PropertiesEditingViewHandlerProvider";

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
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingProviders()
	 */
	public Collection<PropertiesEditingProvider> getEditingProviders() {
		return builder.getEditingProviders();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEMFServices()
	 */
	public Collection<EMFService> getEMFServices() {
		return builder.getEMFServices();
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
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingProviderRegistry()
	 */
	public PropertiesEditingProviderRegistry getEditingProviderRegistry() {
		return builder.getEditingProviderRegistry();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditorProviders()
	 */
	public Collection<PropertyEditorProvider> getEditorProviders() {
		return builder.getEditorProviders();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditorProviderRegistry()
	 */
	public PropertyEditorProviderRegistry getEditorProviderRegistry() {
		return builder.getEditorProviderRegistry();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getViewServices()
	 */
	public Collection<ViewService> getViewServices() {
		return builder.getViewServices();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getViewServiceRegistry()
	 */
	public ViewServiceRegistry getViewServiceRegistry() {
		return builder.getViewServiceRegistry();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getViewHandlerProviderRegistry()
	 */
	public ViewHandlerProviderRegistry getViewHandlerProviderRegistry() {
		return builder.getViewHandlerProviderRegistry();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getModelChangesNotificationManager()
	 */
	public ModelChangesNotificationManager getModelChangesNotificationManager() {
		return builder.getModelChangesNotificationManager();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingContextFactory()
	 */
	public PropertiesEditingContextFactory getEditingContextFactory() {
		return builder.getEditingContextFactory();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment.Builder#getEditingContext()
	 */
	public PropertiesEditingContext getEditingContext() {
		return builder.getEditingContext();
	}

	public final static class Builder {

		private ResourceSet resourceSet;

		private EObject sampleModel;
		private EObject editedObject;
		private PropertiesEditingModel editingModel;

		private AdapterFactory adapterFactory;
		private Collection<EMFService> emfServices;
		private EMFServiceProvider emfServiceProvider;
		private Collection<PropertiesEditingProvider> editingProviders;
		private PropertiesEditingProviderRegistry editingProviderRegistry;
		private ModelChangesNotificationManager notificationManager;

		private Collection<PropertyEditorProvider> editorProviders;
		private PropertyEditorProviderRegistry editorProviderRegistry;
		private Collection<ViewService> viewServices;
		private ViewServiceRegistry viewServiceRegistry;
		private ViewHandlerProviderRegistry viewHandlerProviderRegistry;

		private PropertiesEditingContextFactory editingContextFactory;
		private PropertiesEditingContext editingContext;

		public Builder() {
			sampleModel = null;
			editedObject = null;
			editingModel = null;
			adapterFactory = null;
			emfServices = null;
			emfServiceProvider = null;
			editingProviders = null;
			editingProviderRegistry = null;
			notificationManager = null;
			editorProviders = null;
			editorProviderRegistry = null;
			viewServices = null;
			viewServiceRegistry = null;
			viewHandlerProviderRegistry = null;
			editingContextFactory = null;
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

		public Collection<PropertiesEditingProvider> getEditingProviders() {
			if (editingProviders == null) {
				editingProviders = createEditingProviders();
			}
			return editingProviders;
		}

		public Collection<EMFService> getEMFServices() {
			if (emfServices == null) {
				emfServices = createEMFServices();
			}
			return emfServices;
		}

		public EMFServiceProvider getEMFServiceProvider() {
			if (emfServiceProvider == null) {
				emfServiceProvider = createEMFServiceProvider();
			}
			return emfServiceProvider;
		}

		public PropertiesEditingProviderRegistry getEditingProviderRegistry() {
			if (editingProviderRegistry == null) {
				editingProviderRegistry = createPropertiesEditingProviderRegistry();  
			}
			return editingProviderRegistry;
		}

		public Collection<PropertyEditorProvider> getEditorProviders() {
			if (editorProviders == null) {
				editorProviders = createEditorProviders();
			}
			return editorProviders;
		}

		public PropertyEditorProviderRegistry getEditorProviderRegistry() {
			if (editorProviderRegistry == null) {
				editorProviderRegistry = createEditorProviderRegistry();
			}
			return editorProviderRegistry;
		}

		public Collection<ViewService> getViewServices() {
			if (viewServices == null) {
				viewServices = createViewServices();
			}
			return viewServices;
		}

		public ViewServiceRegistry getViewServiceRegistry() {
			if (viewServiceRegistry == null) {
				viewServiceRegistry = createViewServiceRegistry();
			}
			return viewServiceRegistry;
		}

		public ViewHandlerProviderRegistry getViewHandlerProviderRegistry() {
			try {
				if (viewHandlerProviderRegistry == null) {
					viewHandlerProviderRegistry = createViewHandlerProviderRegistry();
				}
				return viewHandlerProviderRegistry;
			} catch (PriorityCircularityException e) {
				// Note: Should not happen...
				return null;
			}
		}

		public ModelChangesNotificationManager getModelChangesNotificationManager() {
			if (notificationManager == null) {
				notificationManager = createNotificationManager();
			}
			return notificationManager;
		}

		public PropertiesEditingContextFactory getEditingContextFactory() {
			if (editingContextFactory == null) {
				editingContextFactory = createPropertiesEditingContextFactory();
			}
			return editingContextFactory;
		}

		public PropertiesEditingContext getEditingContext() {
			if (editingContext == null) {
				editingContext = createEditingContext();
			}
			return editingContext;
		}	

		/**
		 * @param sampleModel the sampleModel to set
		 * @return 
		 */
		public Builder setSampleModel(EObject sampleModel) {
			this.sampleModel = sampleModel;
			return this;
		}
		/**
		 * @param editedObject the editedObject to set
		 * @return 
		 */
		public Builder setEditedObject(EObject editedObject) {
			this.editedObject = editedObject;
			return this;
		}
		/**
		 * @param editingModel the editingModel to set
		 */
		public Builder setEditingModel(PropertiesEditingModel editingModel) {
			this.editingModel = editingModel;
			return this;
		}
		/**
		 * @param adapterFactory the adapterFactory to set
		 */
		public Builder setAdapterFactory(AdapterFactory adapterFactory) {
			this.adapterFactory = adapterFactory;
			return this;
		}
		/**
		 * @param emfServices the emfServices to set
		 */
		public Builder setEMFServices(Collection<EMFService> emfServices) {
			this.emfServices = emfServices;
			return this;
		}
		/**
		 * @param emfServiceProvider the emfServiceProvider to set
		 */
		public Builder setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
			this.emfServiceProvider = emfServiceProvider;
			return this;
		}
		/**
		 * @param editingProviders the editingProviders to set
		 */
		public Builder setEditingProviders(Collection<PropertiesEditingProvider> editingProviders) {
			this.editingProviders = editingProviders;
			return this;
		}
		/**
		 * @param editingProviderRegistry the editingProviderRegistry to set
		 */
		public Builder setEditingProviderRegistry(PropertiesEditingProviderRegistry editingProviderRegistry) {
			this.editingProviderRegistry = editingProviderRegistry;
			return this;
		}
		/**
		 * @param notificationManager the notificationManager to set
		 */
		public Builder setNotificationManager(ModelChangesNotificationManager notificationManager) {
			this.notificationManager = notificationManager;
			return this;
		}
		/**
		 * @param editorProviders the editorProviders to set
		 */
		public Builder setEditorProviders(Collection<PropertyEditorProvider> editorProviders) {
			this.editorProviders = editorProviders;
			return this;
		}
		/**
		 * @param editorProviderRegistry the editorProviderRegistry to set
		 */
		public Builder setEditorProviderRegistry(PropertyEditorProviderRegistry editorProviderRegistry) {
			this.editorProviderRegistry = editorProviderRegistry;
			return this;
		}
		/**
		 * @param viewServices the viewServices to set
		 */
		public Builder setViewServices(Collection<ViewService> viewServices) {
			this.viewServices = viewServices;
			return this;
		}
		/**
		 * @param viewServiceRegistry the viewServiceRegistry to set
		 */
		public Builder setViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
			this.viewServiceRegistry = viewServiceRegistry;
			return this;
		}
		/**
		 * @param viewHandlerProviderRegistry the viewHandlerProviderRegistry to set
		 */
		public Builder setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
			this.viewHandlerProviderRegistry = viewHandlerProviderRegistry;
			return this;
		}
		/**
		 * @param editingContextFactory the editingContextFactory to set
		 */
		public Builder setEditingContextFactory(PropertiesEditingContextFactory editingContextFactory) {
			this.editingContextFactory = editingContextFactory;
			return this;
		}
		/**
		 * @param editingContext the editingContext to set
		 */
		public Builder setEditingContext(PropertiesEditingContext editingContext) {
			this.editingContext = editingContext;
			return this;
		}

		public EEFTestEnvironment build() {
			return new EEFTestEnvironment(this);
		}

		/*==========================================================================
		 * 
		 * Default creation methods
		 * 
		 *========================================================================== */		
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
			return new EditingModelBuilder()
			.bindClass(EcorePackage.Literals.ECLASS)
			.withView(EClassMockView.class)
			.build();
		}


		/**
		 * Build a {@link PropertiesEditingModel} with simple SWT views for the EEF tests.
		 * @return a sample {@link PropertiesEditingModel}.
		 */
		public PropertiesEditingModel createEditingModelWithSWTViews() {
			return new EditingModelBuilder()
			.bindClass(EcorePackage.Literals.ECLASS).withView(SampleView.class)
			.bindClass(EcorePackage.Literals.EPACKAGE).withView(RootView.class)
			.build();
		}

		public PropertiesEditingModel createEditingModelWithPropertiesEditingViews() {
			List<Toolkit> toolkits = new ArrayList<Toolkit>();
			ResourceSet rset = new ResourceSetImpl();
			Resource resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui/org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui/org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			List<View> views = createEcoreViews(toolkits);
			return new EditingModelBuilder()
							.bindClass(EcorePackage.Literals.ECLASS)
								.withView(views.get(0))
								.withView(views.get(1))
									.bindProperty(EcorePackage.Literals.ECLASSIFIER__DEFAULT_VALUE)
										.withEditor(views.get(1).getElements().get(0))
							.build();
		}

		public PropertiesEditingModel createEditingModelWithContainersPropertiesEditingViews() {
			List<Toolkit> toolkits = new ArrayList<Toolkit>();
			ResourceSet rset = new ResourceSetImpl();
			Resource resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui/org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit"), true);
			toolkits.add((Toolkit) resource.getContents().get(0));
			List<View> views = createEcoreViewsWithContainers(toolkits);
			return new EditingModelBuilder()
								.bindClass(EcorePackage.Literals.ECLASS)
									.withView(views.get(0))
							.build();
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
			superTypes.setRepresentation(searchWidget(toolkits.get(1), "EReferenceMultiEditor"));
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

		public Collection<PropertiesEditingProvider> createEditingProviders() {
			List<PropertiesEditingProvider> result = new ArrayList<PropertiesEditingProvider>();
			PropertiesEditingProvider editingProvider = new PropertiesEditingProviderImpl() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl#getEditingModel()
				 */
				@Override
				protected PropertiesEditingModel getEditingModel() {
					return Builder.this.getEditingModel();
				}


			};
			result.add(editingProvider);
			return result;
		}

		public Collection<EMFService> createEMFServices() {
			Collection<EMFService> services = new ArrayList<EMFService>();
			services.add(new EMFServiceImpl());
			return services;
		}

		public EMFServiceProvider createEMFServiceProvider() {
			EMFServiceRegistry emfServiceRegistry = new EMFServiceRegistry();
			for (EMFService emfService : getEMFServices()) {
				emfServiceRegistry.addService(emfService);			
			}
			return emfServiceRegistry;
		}

		public PropertiesEditingProviderRegistry createPropertiesEditingProviderRegistry() {
			PropertiesEditingProviderRegistryImpl registry = new PropertiesEditingProviderRegistryImpl();
			registry.setEMFServiceProvider(getEMFServiceProvider());
			registry.setViewHandlerProviderRegistry(getViewHandlerProviderRegistry());
			registry.setModelChangesNotificationManager(getModelChangesNotificationManager());
			for (PropertiesEditingProvider provider : getEditingProviders()) {			
				registry.addService(provider);
			}
			return registry;
		}

		public Collection<PropertyEditorProvider> createEditorProviders() {
			List<PropertyEditorProvider> result = new ArrayList<PropertyEditorProvider>();
			result.add(new SWTToolkit());
			result.add(new EMFPropertiesToolkit());		
			return result;
		}

		public PropertyEditorProviderRegistry createEditorProviderRegistry() {
			PropertyEditorProviderRegistryImpl editorProviderRegistry = new PropertyEditorProviderRegistryImpl();
			for (PropertyEditorProvider provider : getEditorProviders()) {
				editorProviderRegistry.addService(provider);
			}
			return editorProviderRegistry;
		}	

		public Collection<ViewService> createViewServices() {
			List<ViewService> result = new ArrayList<ViewService>();
			result.add(new ViewServiceImpl());
			return result;
		}

		public ViewServiceRegistry createViewServiceRegistry() {
			ViewServiceRegistryImpl viewServiceRegistry = new ViewServiceRegistryImpl();
			for (ViewService service : getViewServices()) {
				viewServiceRegistry.addService(service);			
			}
			return viewServiceRegistry;
		}

		public ViewHandlerProviderRegistry createEmptyViewHandlerProviderRegistry() {
			return new ViewHandlerProviderRegistryImpl();
		}

		public ViewHandlerProviderRegistry createViewHandlerProviderRegistry() throws PriorityCircularityException {
			ViewHandlerProviderRegistryImpl viewHandlerProviderRegistry = (ViewHandlerProviderRegistryImpl) createEmptyViewHandlerProviderRegistry(); 
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(COMPONENT_NAME_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
			viewHandlerProviderRegistry.addService(new ReflectViewHandlerProvider(), properties);
			properties.put(COMPONENT_NAME_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
			properties.put(PRIORITY_OVER_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
			viewHandlerProviderRegistry.addService(new SWTViewHandlerProvider(), properties);
			properties.put(COMPONENT_NAME_KEY, PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME);
			properties.put(PRIORITY_OVER_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
			PropertiesEditingViewHandlerProvider handler = new PropertiesEditingViewHandlerProvider();
			handler.setEditorProviderRegistry(getEditorProviderRegistry());
			handler.setViewServiceRegistry(getViewServiceRegistry());
			viewHandlerProviderRegistry.addService(handler, properties);
			return viewHandlerProviderRegistry;
		}

		public ModelChangesNotificationManager createNotificationManager() {
			return new ModelChangesNotificationManagerTest();
		}

		public PropertiesEditingContextFactory createPropertiesEditingContextFactory() {
			PropertiesEditingContextFactory factory = new PropertiesEditingContextFactoryImpl();
			factory.setPropertiesEditingProviderRegistry(getEditingProviderRegistry());
			factory.setEMFServiceProvider(getEMFServiceProvider());
			factory.setNotificationManager(getModelChangesNotificationManager());
			return factory;
		}

		public PropertiesEditingContext createEditingContext() {
			return getEditingContextFactory().createPropertiesEditingContext(getAdapterFactory(), getEditedObject());
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

	private static final class ModelChangesNotificationManagerTest implements ModelChangesNotificationManager {
		
		private List<PropertiesEditingComponent> components;

		public ModelChangesNotificationManagerTest() {
			components = new ArrayList<PropertiesEditingComponent>();
		}

		public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
			components.remove(editingComponent);
		}

		public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
			components.add(editingComponent);
		}

		public void initModelChangesNotifierIfNeeded(EObject source) {
			Notifier highestNotifier = new EMFServiceImpl().highestNotifier(source);
			highestNotifier.eAdapters().add(new EContentAdapter() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
				 */
				@Override
				public void notifyChanged(Notification notification) {
					for (PropertiesEditingComponent component : components) {
						component.notifyChanged(notification);
					}
				}
				
			});
		}
	}
}
