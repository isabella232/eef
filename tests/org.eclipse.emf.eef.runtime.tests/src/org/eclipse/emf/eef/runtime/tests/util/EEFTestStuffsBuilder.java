/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.util;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.eeftests.bindingmodel.Sample;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.reflect.ReflectViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ComposedPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.view.handler.ComposedViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFTestStuffsBuilder {

	public static final String SAMPLE_NAME_INITIALIZATION_EDITING_VIEWS_CONTEXT = "This is a named Sample";
	public static final boolean SAMPLE_ACTIVATION_INITIALIZATION_EDITING_VIEWS_CONTEXT = true;
	private ResourceSet resourceSet;

	/**
	 * Build a {@link PropertiesEditingModel} with simple SWT views for the EEF tests.
	 * @return a sample {@link PropertiesEditingModel}.
	 */
	public PropertiesEditingModel buildEditingModelWithSWTViews() {
		return new EditingModelBuilder()
						.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(SampleView.class)
						.bindClass(BindingmodelPackage.Literals.ROOT).withView(RootView.class)
						.build();
	}
	
	/**
	 * Build a full sample of {@link PropertiesEditingContext} for the EEF tests.
	 * @return a sample {@link PropertiesEditingContext}.
	 */
	public PropertiesEditingContext buildEditingContextWithPropertiesEditingViews() {
		ViewHandlerProvider viewHandlerProvider = buildViewHandlerProvider();
		Toolkit swtToolkit = searchSWTToolkit(viewHandlerProvider);
		List<View> views = buildSampleViews(swtToolkit);
		PropertiesEditingModel editingModel = new EditingModelBuilder()
						.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(views.get(0))
						.build();
		Sample sampleToEdit = BindingmodelFactory.eINSTANCE.createSample();
		sampleToEdit.setName(SAMPLE_NAME_INITIALIZATION_EDITING_VIEWS_CONTEXT);
		sampleToEdit.setActive(SAMPLE_ACTIVATION_INITIALIZATION_EDITING_VIEWS_CONTEXT);
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(sampleToEdit);
		context.setAdapterFactory(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		context.setEditingModel(editingModel);
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}
	
	/**
	 * Build a full sample of {@link PropertiesEditingContext} for the EEF tests.
	 * @return a sample {@link PropertiesEditingContext}.
	 */
	public PropertiesEditingContext buildEditingContextWithPropertiesEditingViewsForEcore() {
		// Creating ViewHandlerProvider
		ViewHandlerProvider viewHandlerProvider = buildViewHandlerProvider();
		
		// Creating views
		List<Toolkit> toolkits = new ArrayList<Toolkit>(2);
		toolkits.add(searchSWTToolkit(viewHandlerProvider));
		toolkits.add(searchEMFPropertiesToolkit(viewHandlerProvider));		
		List<View> views = buildEcoreViews(toolkits);
		
		// Creation Editing Model
		PropertiesEditingModel editingModel = new EditingModelBuilder()
						.bindClass(EcorePackage.Literals.ECLASS)
							.withView(views.get(0))
							.withView(views.get(1))
								.bindProperty(EcorePackage.Literals.ECLASSIFIER__DEFAULT_VALUE, views.get(1).getElements().get(0))
						.build();
		
		// Creating model
		EPackage sampleModel = buildEcoreSampleModel();
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(sampleModel.getEClassifiers().get(0));
		context.setAdapterFactory(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE));
		context.setEditingModel(editingModel);
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}
	
	/**
	 * Build the {@link ViewHandlerProvider} for the EEF tests.
	 * @return a sample {@link ViewHandlerProvider}
	 */
	public ViewHandlerProvider buildViewHandlerProvider() {
		return new ComposedViewHandlerProvider.Builder()
						.addHandler(new PropertiesEditingViewHandlerProvider(buildPropertyEditorProvider()))
						.addHandler(new SWTViewHandlerProvider())
						.addHandler(new ReflectViewHandlerProvider())
						.build();
	}
	


	/**
	 * Build the {@link PropertyEditorProvider} for the EEF tests
	 * @return a sample {@link PropertyEditorProvider}.
	 */
	public PropertyEditorProvider buildPropertyEditorProvider() {
		return new ComposedPropertyEditorProvider.Builder()
						.addPropertyEditorProvider(new SWTToolkit())
						.addPropertyEditorProvider(new EMFPropertiesToolkit())
						.build();
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
	
	public ResourceSet getResourceSet() {
		if (resourceSet == null) {
			resourceSet = new ResourceSetImpl();
			// Register the appropriate resource factory to handle all file extensions.
			//
			resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
				(Resource.Factory.Registry.DEFAULT_EXTENSION, 
				 new XMIResourceFactoryImpl());

			// Register the package to ensure it is available during loading.
			//
			resourceSet.getPackageRegistry().put
				(BindingmodelPackage.eNS_URI, 
				 BindingmodelPackage.eINSTANCE);
	        
		}
		return resourceSet;
	}

	
	private List<View> buildEcoreViews(List<Toolkit> toolkits) {
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

	private List<View> buildSampleViews(Toolkit swtToolkit) {
		List<View> result = new ArrayList<View>();
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample");
		ElementEditor nameEditor = ViewsFactory.eINSTANCE.createElementEditor();
		nameEditor.setName("name");
		nameEditor.setRepresentation(searchWidget(swtToolkit, "Text"));
		sampleView.getElements().add(nameEditor);

		ElementEditor activeEditor = ViewsFactory.eINSTANCE.createElementEditor();
		activeEditor.setName("active");
		activeEditor.setRepresentation(searchWidget(swtToolkit, "Checkbox"));
		sampleView.getElements().add(activeEditor);
		
		result.add(sampleView);
		
		return result;
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
	
	private Toolkit searchSWTToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), SWTToolkit.SWT_TOOLKIT_NAME);
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}
		
		return null;
	}
	
	private Toolkit searchEMFPropertiesToolkit(ViewHandlerProvider provider) {
		PropertiesEditingViewHandlerProvider propertiesEditingViewHandlerProvider = searchPropertiesEditingViewHandlerProvider(provider);
		if (propertiesEditingViewHandlerProvider != null) {
			ToolkitPropertyEditorProvider toolkitPropertyEditorProvider = searchToolkitWithName(propertiesEditingViewHandlerProvider.getPropertyEditorProvider(), EMFPropertiesToolkit.EMF_PROPERTIES);
			if (toolkitPropertyEditorProvider != null) {
				return toolkitPropertyEditorProvider.getModel();
			}
		}
		
		return null;
	}
	
	private PropertiesEditingViewHandlerProvider searchPropertiesEditingViewHandlerProvider(ViewHandlerProvider provider) {
		if (provider instanceof PropertiesEditingViewHandlerProvider) {
			return (PropertiesEditingViewHandlerProvider) provider;
		} else if (provider instanceof ComposedViewHandlerProvider) {
			for (ViewHandlerProvider viewHandlerProvider : ((ComposedViewHandlerProvider) provider).getProviders()) {
				if (viewHandlerProvider instanceof PropertiesEditingViewHandlerProvider) {
					return (PropertiesEditingViewHandlerProvider) viewHandlerProvider;
				}
			}
		}
		return null;
	}
	
	private ToolkitPropertyEditorProvider searchToolkitWithName(PropertyEditorProvider provider, String name) {
		if (provider instanceof ToolkitPropertyEditorProvider) {
			if (name.equals(((ToolkitPropertyEditorProvider) provider).getModel().getName())) {
				return (ToolkitPropertyEditorProvider) provider;
			}
		} else if (provider instanceof ComposedPropertyEditorProvider) {
			for (PropertyEditorProvider propertyEditorProvider : ((ComposedPropertyEditorProvider) provider).getPropertyEditorProviders()) {
				ToolkitPropertyEditorProvider searchToolkitWithName = searchToolkitWithName(propertyEditorProvider, name);
				if (searchToolkitWithName != null) {
					return searchToolkitWithName;
				}
			}
		}
		return null;
	}
	
}
