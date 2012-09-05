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
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.impl.PropertiesEditingContextFactoryImpl;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.ViewHandlerProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceSimpleRegistry;
import org.eclipse.emf.eef.runtime.services.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.impl.PropertyEditorProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceImpl;
import org.eclipse.emf.eef.runtime.ui.internal.services.view.ViewServiceRegistryImpl;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestEnvironmentBuilder {

	public static final boolean FIRST_ECLASS_SAMPLE_ABSTRACTNESS = false;
	public static final String FIRST_ECLASS_SAMPLE_NAME = "EClass1";
	
	public static final String COMPONENT_NAME_KEY = "component.name";
	public static final String PRIORITY_OVER_KEY = "priority.over";
	public static final String REFLECT_VIEW_HANDLER_PROVIDER_NAME = "ReflectViewHandlerProvider";
	public static final String SWT_VIEW_HANDLER_PROVIDER_NAME = "SWTViewHandlerProvider";
	public static final String PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME = "PropertiesEditingViewHandlerProvider";
	
	private ResourceSet resourceSet;
	private ModelChangesNotificationManager notificationManager;

	public ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			}
		return resourceSet;
	}
	
	public ModelChangesNotificationManager getModelChangesNotificationManager() {
		if (notificationManager == null) {
			notificationManager = createNotificationManager();
		}
		return notificationManager;
	}
	

	public EPackage buildEcoreSampleModel() {
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
	
	/**
	 * Build a {@link PropertiesEditingModel} with simple SWT views for the EEF tests.
	 * @return a sample {@link PropertiesEditingModel}.
	 */
	public PropertiesEditingModel buildEditingModelWithSWTViews() {
		return new EditingModelBuilder()
						.bindClass(EcorePackage.Literals.ECLASS).withView(SampleView.class)
						.bindClass(EcorePackage.Literals.EPACKAGE).withView(RootView.class)
						.build();
	}
	
	public PropertiesEditingModel buildEditingModelWithPropertiesEditingViews() {
		List<Toolkit> toolkits = new ArrayList<Toolkit>();
		ResourceSet rset = new ResourceSetImpl();
		Resource resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui/org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit"), true);
		toolkits.add((Toolkit) resource.getContents().get(0));
		resource = rset.getResource(URI.createURI("eeftoolkit:/org.eclipse.emf.eef.runtime.ui/org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit"), true);
		toolkits.add((Toolkit) resource.getContents().get(0));
		List<View> views = buildEcoreViews(toolkits);
		return new EditingModelBuilder()
						.bindClass(EcorePackage.Literals.ECLASS)
							.withView(views.get(0))
							.withView(views.get(1))
							.bindProperty(EcorePackage.Literals.ECLASSIFIER__DEFAULT_VALUE)
								.withEditor(views.get(1).getElements().get(0))
					.build();
	}

	public List<View> buildEcoreViews(List<Toolkit> toolkits) {
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

 	public AdapterFactory buildAdapterFactory() {
		return new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
	}

	public PropertiesEditingContext buildEditingContext(AdapterFactory adapterFactory, final PropertiesEditingProvider editingProvider, EMFServiceProvider emfServiceProvider, EObject eObject) throws PriorityCircularityException {
		return createPropertiesEditingContextFactory(editingProvider, emfServiceProvider).createPropertiesEditingContext(adapterFactory, eObject);
	}

	public PropertiesEditingContextFactory createPropertiesEditingContextFactory(final PropertiesEditingProvider editingProvider, EMFServiceProvider emfServiceProvider) throws PriorityCircularityException {
		PropertiesEditingContextFactory factory = new PropertiesEditingContextFactoryImpl();
		factory.setPropertiesEditingProviderRegistry(createPropertiesEditingProviderRegistry(editingProvider, emfServiceProvider));
		factory.setEMFServiceProvider(emfServiceProvider);
		factory.setNotificationManager(getModelChangesNotificationManager());
		return factory;
	}

	@SuppressWarnings("unchecked")
	public PropertiesEditingProviderRegistry createPropertiesEditingProviderRegistry(final PropertiesEditingProvider editingProvider, EMFServiceProvider emfServiceProvider) throws PriorityCircularityException {
		PropertiesEditingProviderRegistry registry = new PropertiesEditingProviderRegistryImpl();
		registry.setEMFServiceProvider(emfServiceProvider);
		registry.setViewHandlerProviderRegistry(createViewHandlerProviderRegistry());
		registry.setModelChangesNotificationManager(getModelChangesNotificationManager());
		((EEFServiceSimpleRegistry<EPackage, PropertiesEditingProvider>)registry).addService(editingProvider);
		return registry;
	}

	public ViewHandlerProviderRegistry createViewHandlerProviderRegistry() throws PriorityCircularityException {
		ViewHandlerProviderRegistry registry = createEmptyViewHandlerProviderRegistry();
		ViewHandlerProviderRegistryImpl viewHandlerProviderRegistry = (ViewHandlerProviderRegistryImpl) registry; 
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(COMPONENT_NAME_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
		viewHandlerProviderRegistry.addService(new ReflectViewHandlerProvider(), properties);
		properties.put(COMPONENT_NAME_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
		properties.put(PRIORITY_OVER_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
		viewHandlerProviderRegistry.addService(new SWTViewHandlerProvider(), properties);
		properties.put(COMPONENT_NAME_KEY, PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME);
		properties.put(PRIORITY_OVER_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
		PropertiesEditingViewHandlerProvider handler = new PropertiesEditingViewHandlerProvider();
		handler.setEditorProviderRegistry(createEditorProviderRegistry());
		handler.setViewServiceRegistry(createViewServiceRegistry());
		viewHandlerProviderRegistry.addService(handler, properties);
		return viewHandlerProviderRegistry;
	}
	
	public ViewHandlerProviderRegistry createEmptyViewHandlerProviderRegistry() {
		return new ViewHandlerProviderRegistryImpl();
	}


	public ViewServiceRegistry createViewServiceRegistry() {
		ViewServiceRegistry viewServiceRegistry = new ViewServiceRegistryImpl();
		((ViewServiceRegistryImpl) viewServiceRegistry).addService(new ViewServiceImpl());
		return viewServiceRegistry;
	}

	public PropertyEditorProviderRegistry createEditorProviderRegistry() {
		PropertyEditorProviderRegistry editorProviderRegistry = new PropertyEditorProviderRegistryImpl();
		((PropertyEditorProviderRegistryImpl) editorProviderRegistry).addService(new SWTToolkit());
		((PropertyEditorProviderRegistryImpl) editorProviderRegistry).addService(new EMFPropertiesToolkit());
		return editorProviderRegistry;
	}

	public EMFServiceProvider createEMFServiceProvider(Collection<EMFService> emfServices) {
		EMFServiceRegistry emfServiceRegistry = new EMFServiceRegistry();
		for (EMFService emfService : emfServices) {
			emfServiceRegistry.addService(emfService);			
		}
		return emfServiceRegistry;
	}
	
	public Collection<EMFService> createEMFServices() {
		Collection<EMFService> services = new ArrayList<EMFService>();
		services.add(new EMFServiceImpl());
		return services;
	}
	
	
	public ModelChangesNotificationManager createNotificationManager() {
		return new ModelChangesNotificationManagerTest();
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
	
	private final class ModelChangesNotificationManagerTest implements ModelChangesNotificationManager {
		
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
