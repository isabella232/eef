/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.impl.PropertiesEditingContextFactoryImpl;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.internal.services.viewhandler.ViewHandlerProviderRegistryImpl;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestEnvironmentBuilder {

	public static final String COMPONENT_NAME_KEY = "component.name";
	public static final String PRIORITY_OVER_KEY = "priority.over";
	public static final String REFLECT_VIEW_HANDLER_PROVIDER_NAME = "ReflectViewHandlerProvider";
	public static final String SWT_VIEW_HANDLER_PROVIDER_NAME = "SWTViewHandlerProvider";
	public static final String PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME = "PropertiesEditingViewHandlerProvider";
	
	private ResourceSet resourceSet;

	public ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			}
		return resourceSet;
	}

	public EPackage buildEcoreSampleModel() {
		Resource ecoreResource = getResourceSet().createResource(URI.createURI("eef://sample.ecore"));
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName("sample");
		ePackage.setNsPrefix("eef-sample");
		ePackage.setNsURI("http://eef/sample");
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("EClass1");
		eClass.setAbstract(true);
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
		factory.setNotificationManager(createNotificationManager());
		return factory;
	}

	@SuppressWarnings("unchecked")
	public PropertiesEditingProviderRegistry createPropertiesEditingProviderRegistry(final PropertiesEditingProvider editingProvider, EMFServiceProvider emfServiceProvider) throws PriorityCircularityException {
		PropertiesEditingProviderRegistry registry = new PropertiesEditingProviderRegistryImpl();
		registry.setEMFServiceProvider(emfServiceProvider);
		registry.setViewHandlerProviderRegistry(createViewHandlerProviderRegistry());
		registry.setModelChangesNotificationManager(createNotificationManager());
		((EEFServiceRegistry<EPackage, PropertiesEditingProvider>)registry).addService(editingProvider);
		return registry;
	}

	public ViewHandlerProviderRegistryImpl createViewHandlerProviderRegistry() throws PriorityCircularityException {
		ViewHandlerProviderRegistryImpl viewHandlerProviderRegistry = new ViewHandlerProviderRegistryImpl();
		Map<String, String> properties = new HashMap<String, String>();
		properties.put(COMPONENT_NAME_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
		viewHandlerProviderRegistry.addService(new ReflectViewHandlerProvider(), properties);
		properties.put(COMPONENT_NAME_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
		properties.put(PRIORITY_OVER_KEY, REFLECT_VIEW_HANDLER_PROVIDER_NAME);
		viewHandlerProviderRegistry.addService(new SWTViewHandlerProvider(), properties);
		properties.put(COMPONENT_NAME_KEY, PROPERTIES_EDITING_VIEW_HANDLER_PROVIDER_NAME);
		properties.put(PRIORITY_OVER_KEY, SWT_VIEW_HANDLER_PROVIDER_NAME);
		viewHandlerProviderRegistry.addService(new PropertiesEditingViewHandlerProvider(), properties);
		return viewHandlerProviderRegistry;
	}

	public EMFServiceProvider createEMFServiceProvider() {
		EMFServiceRegistry emfServiceRegistry = new EMFServiceRegistry();
		emfServiceRegistry.addService(new EMFServiceImpl());
		return emfServiceRegistry;
	}
	
	public ModelChangesNotificationManager createNotificationManager() {
		return new ModelChangesNotificationManager() {
			
			public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
				
			}
			
			public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
				
			}
			
			public void initModelChangesNotifierIfNeeded(EObject source) {
				
			}
		};
	}

}
