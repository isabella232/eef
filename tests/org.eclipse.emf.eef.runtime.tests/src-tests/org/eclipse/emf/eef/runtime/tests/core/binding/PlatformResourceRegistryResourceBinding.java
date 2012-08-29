/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.AbstractPropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestStuffsBuilder;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PlatformResourceRegistryResourceBinding {

	private PropertiesEditingContext editingContext;
	private Resource platformEcoreResource;
	
	@Before
	public void setUp() {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		platformEcoreResource = new ResourceSetImpl().getResource(URI.createPlatformPluginURI("org.eclipse.emf.ecore/model/Ecore.ecore", true), true);
		adapterFactory.addAdapterFactory(new AbstractPropertiesEditingProvider() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.binding.AbstractPropertiesEditingProvider#initSpecificEditingModel()
			 */
			@Override
			protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
				List<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
				PropertiesEditingModel pem = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
				pem.setName("Test");
				EClassBinding binding = EditingModelFactory.eINSTANCE.createEClassBinding();
				binding.setEClass(getEClassFromEcoreFile());
				pem.getBindings().add(binding);
				result.add(pem);
				return result;
			}
			
			
		});
		editingContext = new EObjectPropertiesEditingContext(adapterFactory, EcoreFactory.eINSTANCE.createEClass());
	}

	/**
	 * This test checks that the default {@link EMFService} is able to make the correspondence of
	 * a EPackage loaded via Ecore file and the EPackage in the registry. 
	 */
	@Test
	public void testPlatformRegistryMapper() {
		EMFService emfService = editingContext.getEMFService();
		EClass eClassifier = getEClassFromEcoreFile();
		EClass eClass = ((EObject)editingContext.getEditingComponent().getTarget()).eClass();
		boolean equals = emfService.equals(eClassifier, eClass);
		assertTrue("Platform <> Registry equivalence doesn't work properly", equals);
	}

	// 4 Tests à faire
	//		- On fait un binding model en faisant référence au Ecore.ecore et on tente d'éditer une EClass issue du registry
	@Test
	public void testEcoreFileBindingRegistryEClassEditing() {
		EClass eClassToBind = getEClassFromEcoreFile();
		EClass eClassToEdit = EcoreFactory.eINSTANCE.createEClass();
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the registry Ecore metamodel cannot be edited by a binding model configured with an EClass from the Ecore file", 
				prepareEditingContext.getEditingComponent());
		
	}

	//		- On fait un binding model en faisant référence au Ecore.ecore et on tente d'éditer une EClass créée dynamiquement par Ecore.ecore
	@Test
	public void testEcoreFileBindingEcoreFileEClassEditing() {
		EClass eClassToBind = getEClassFromEcoreFile();
		EObject eClassToEdit = EcoreUtil.create(getEClassFromEcoreFile());
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the Ecore file cannot be edited by a binding model configured with an EClass from the Ecore file", 
				prepareEditingContext.getEditingComponent());
		
	}

	//		- On fait un binding model en faisant référence au registry et on tente d'éditer une EClass issue du registry
	@Test
	public void testRegistryBindingRegistryEClassEditing() {
		EClass eClassToBind = EcoreFactory.eINSTANCE.createEClass();
		EClass eClassToEdit = EcoreFactory.eINSTANCE.createEClass();
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the Ecore metamodel registry cannot be edited by a binding model configured with an EClass from Ecore metamodel registry ", 
				prepareEditingContext.getEditingComponent());
		
	}
	
	//		- On fait un binding model en faisant référence au registry et on tente d'éditer une EClass créée dynamiquement par Ecore.ecore
	@Test
	public void testRegistryBindingEcoreFileEClassEditing() {
		EClass eClassToBind = EcoreFactory.eINSTANCE.createEClass();
		EObject eClassToEdit = EcoreUtil.create(getEClassFromEcoreFile());
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the Ecore file cannot be edited by a binding model configured with an EClass from the Ecore file", 
				prepareEditingContext.getEditingComponent());
		
	}

	private EClass getEClassFromEcoreFile() {
		return (EClass) ((EPackage)platformEcoreResource.getContents().get(0)).getEClassifier("EClass");
	}
	
	/**
	 * This method prepare a {@link PropertiesEditingContext} with these settings :
	 * 		- a simple {@link PropertiesEditingModel} bind the <code>eClassToBind</code> to the {@link SampleView}
	 * 		- the {@link PropertiesEditingContext} is focused on the <code>eClassToEdit</code>
	 * @param eClassToBind EClass to use in the {@link PropertiesEditingModel}
	 * @param eClassToEdit EClass to edit with the {@link PropertiesEditingContext}
	 * @return the {@link PropertiesEditingContext}
	 */
	private PropertiesEditingContext prepareEditingContext(EClass eClassToBind, EObject eClassToEdit) {
		final PropertiesEditingModel editingModel = new EditingModelBuilder().bindClass(eClassToBind)
												.withView(SampleView.class)
														.bindProperty(eClassToBind.getEStructuralFeature("name"))
															.withEditor("name")
					.build();
		AbstractPropertiesEditingProvider provider = new AbstractPropertiesEditingProvider() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.binding.AbstractPropertiesEditingProvider#initSpecificEditingModel()
			 */
			@Override
			protected Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
				List<PropertiesEditingModel> result = new ArrayList<PropertiesEditingModel>();
				result.add(editingModel);
				return result;
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.binding.AbstractPropertiesEditingProvider#initViewHandlerProvider()
			 */
			@Override
			protected ViewHandlerProvider initViewHandlerProvider() {
				return new EEFTestStuffsBuilder().buildViewHandlerProvider();
			}
			
		};
		
		PropertiesEditingContext context = new EObjectPropertiesEditingContext(provider, eClassToEdit);
		return context;
	}
	
}
