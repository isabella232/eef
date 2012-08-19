/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewService;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.junit.Test;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FeatureDocumentationTests {

	private static final String ECORE_GENMODEL_URI = "org.eclipse.emf.ecore/model/Ecore.genmodel";
	private static final String ECORE_ECORE_URI = "org.eclipse.emf.ecore/model/Ecore.ecore";
	private static final String SAMPLE_ECLASS_NAME = "Sample";
	private static final String ECORE_DOCUMENTATION = "Ecore Documentation test";
	
	private View eclassView;
	private ElementEditor nameEditor;

	/**
	 * In this test, we create a ecore file, a view repository and we bind them. A feature of the
	 * ecore model has a Ecore documentation. Then, we create a context on an instance of this ecore model
	 * and we use the ViewService to get the Ecore documentation of the feature. 
	 */
	@Test
	public void testEcoreDocumentation() {
		PropertiesEditingContext editingContext = buildEcoreSampleEditingContext();
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		ViewService viewService = EEFRuntimeUI.getPlugin().getViewService(eclassView);
		viewService.setEditingComponent(editingComponent);
		assertSame("Invalid documentation", ECORE_DOCUMENTATION, viewService.getHelpContent(nameEditor));
	}
	
	/**
	 * @return
	 */
	protected PropertiesEditingContext buildEcoreSampleEditingContext() {
		EPackage ecoreSample = generateEcoreSample();
		ViewsRepository viewRepository = generateViewsRepository();
		eclassView = viewRepository.getViews().get(0);
		nameEditor = (ElementEditor)eclassView.getElements().get(0);
		final EClass sampleEClass = (EClass) ecoreSample.getEClassifier(SAMPLE_ECLASS_NAME);
		EObject editedEObject = EcoreUtil.create(sampleEClass);
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		PropertiesEditingProvider pep = new PropertiesEditingProvider() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#getEditingModel()
			 */
			@Override
			protected PropertiesEditingModel getEditingModel() {
				PropertiesEditingModel editingModel = new EditingModelBuilder()
															.bindClass(sampleEClass)
																.withView(eclassView)
															.build();
				EditingOptions editingOptions = EditingModelFactory.eINSTANCE.createEditingOptions();
				editingOptions.setFeatureDocumentationProvider(FeatureDocumentationProvider.ECORE_DOCUMENTATION);
				editingModel.setOptions(editingOptions);
				
				return editingModel;
			}
			
		};
		adapterFactory.addAdapterFactory(pep);
		
		return new EObjectPropertiesEditingContext(adapterFactory, editedEObject);
	}

	private EPackage generateEcoreSample() {
		EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		root.setName("test");
		EClass sampleClass = EcoreFactory.eINSTANCE.createEClass();
		sampleClass.setName(SAMPLE_ECLASS_NAME);
		EAttribute attr = EcoreFactory.eINSTANCE.createEAttribute();
		attr.setName("name");
		attr.setEType(EcorePackage.eINSTANCE.getEString());
		EcoreUtil.setDocumentation(attr, ECORE_DOCUMENTATION);
		sampleClass.getEStructuralFeatures().add(attr);
		root.getEClassifiers().add(sampleClass);
		return root;
		
	}
	

	/**
	 * In this test, we generate a ViewRepository and we bind it to the ecore.ecore file. We involve the
	 * ecore.genmodel model to be able to get the genfeatures property descriptions. Then, we create a EditingContext
	 * on a Ecore model instance and we try to get a genfeature property description via the ViewService.
	 */
	@Test
	public void testGenmodelDocumentation() {
		PropertiesEditingContext editingContext = buildGenmodelEditingContext();
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		ViewService viewService = EEFRuntimeUI.getPlugin().getViewService(eclassView);
		viewService.setEditingComponent(editingComponent);
		String helpContent = viewService.getHelpContent(nameEditor);
		EditingModelEnvironment env = editingComponent.getEditingModelEnvironment();
		EClass eClassEClassFromResourceSet = getEClassEClassFromResourceSet(env.getResourceSet());
		EStructuralFeature eStructuralFeature = eClassEClassFromResourceSet.getEStructuralFeature("name");
		GenFeature genFeature = env.genFeature(eStructuralFeature);
		assertEquals("Invalid documentation", genFeature.getPropertyDescription(), helpContent);
	}

	/**
	 * @return
	 */
	protected PropertiesEditingContext buildGenmodelEditingContext() {
		ViewsRepository viewRepository = generateViewsRepository();
		eclassView = viewRepository.getViews().get(0);
		nameEditor = (ElementEditor)eclassView.getElements().get(0);
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		PropertiesEditingProvider pep = new PropertiesEditingProvider() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#getEditingModel()
			 */
			@Override
			protected PropertiesEditingModel getEditingModel() {
				ResourceSet rset = getEditingModelEnvironment().getResourceSet();
				final Resource genmodelResource = rset.getResource(URI.createPlatformPluginURI(ECORE_GENMODEL_URI, true), true);
				EClass sampleEClass = getEClassEClassFromResourceSet(rset);
				PropertiesEditingModel editingModel = new EditingModelBuilder()
															.bindClass(sampleEClass)
																.withView(eclassView)
															.build();
				editingModel.getInvolvedModels().add(genmodelResource.getContents().get(0));
				return editingModel;
			}
			
		};
		adapterFactory.addAdapterFactory(pep);
		ResourceSet resourceSet = pep.getEditingModelEnvironment().getResourceSet();
		EClass sampleEClass = getEClassEClassFromResourceSet(resourceSet);
		EObject editedEObject = EcoreUtil.create(sampleEClass);
		return new EObjectPropertiesEditingContext(adapterFactory, editedEObject);
	}

	private EClass getEClassEClassFromResourceSet(ResourceSet rset) {
		final Resource ecoreResource = rset.getResource(URI.createPlatformPluginURI(ECORE_ECORE_URI, true), true);
		EPackage ecorePackage = (EPackage) ecoreResource.getContents().get(0);
		EClass sampleEClass = (EClass) ecorePackage.getEClassifier("EClass");
		return sampleEClass;
	}
	

	private ViewsRepository generateViewsRepository() {
		ViewsRepository repo = ViewsFactory.eINSTANCE.createViewsRepository();
		View view = ViewsFactory.eINSTANCE.createView();
		view.setName(SAMPLE_ECLASS_NAME);
		ElementEditor editor = ViewsFactory.eINSTANCE.createElementEditor();
		editor.setName("name");
		Resource resource = new ResourceSetImpl().getResource(URI.createURI("eeftoolkit://SWT.toolkit", false), true);
		Toolkit toolkit = (Toolkit) resource.getContents().get(0);
		Widget widget = toolkit.getWidgets().get(0);
		editor.setRepresentation(widget);
		view.getElements().add(editor);
		repo.getViews().add(view);
		return repo;
	}

}
