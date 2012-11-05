/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
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
	public void setUp() throws PriorityCircularityException {
		platformEcoreResource = new ResourceSetImpl().getResource(URI.createPlatformPluginURI("org.eclipse.emf.ecore/model/Ecore.ecore", true), true);
		PropertiesEditingModel pem = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		pem.setName("Test");
		EClassBinding binding = EditingModelFactory.eINSTANCE.createEClassBinding();
		binding.setEClass(getEClassFromEcoreFile());
		pem.getBindings().add(binding);
		EEFTestEnvironment env = new EEFTestEnvironment.Builder()
																.setEditingModel(pem)
																.setEditedObject(EcoreFactory.eINSTANCE.createEClass())
															.build();
		editingContext = env.getEditingContext();
	}
	
	/**
	 * This test checks that the default {@link EMFService} is able to make the correspondence of
	 * a EPackage loaded via Ecore file and the EPackage in the registry. 
	 */
	@Test
	public void testPlatformRegistryMapper() {
		EMFService emfService = editingContext.getServiceRegistry().getService(EMFService.class, getEClassFromEcoreFile().eClass().getEPackage());
		EClass eClassifier = getEClassFromEcoreFile();
		EClass eClass = ((EObject)editingContext.getEditingComponent().getEObject()).eClass();
		boolean equals = emfService.equals(eClassifier, eClass);
		assertTrue("Platform <> Registry equivalence doesn't work properly", equals);
	}

	// 4 Tests à faire
	//		- On fait un binding model en faisant référence au Ecore.ecore et on tente d'éditer une EClass issue du registry
	@Test
	public void testEcoreFileBindingRegistryEClassEditing() throws PriorityCircularityException {
		EClass eClassToBind = getEClassFromEcoreFile();
		EClass eClassToEdit = EcoreFactory.eINSTANCE.createEClass();
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the registry Ecore metamodel cannot be edited by a binding model configured with an EClass from the Ecore file", 
				prepareEditingContext.getEditingComponent());
		
	}

	//		- On fait un binding model en faisant référence au Ecore.ecore et on tente d'éditer une EClass créée dynamiquement par Ecore.ecore
	@Test
	public void testEcoreFileBindingEcoreFileEClassEditing() throws PriorityCircularityException {
		EClass eClassToBind = getEClassFromEcoreFile();
		EObject eClassToEdit = EcoreUtil.create(getEClassFromEcoreFile());
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the Ecore file cannot be edited by a binding model configured with an EClass from the Ecore file", 
				prepareEditingContext.getEditingComponent());
		
	}

	//		- On fait un binding model en faisant référence au registry et on tente d'éditer une EClass issue du registry
	@Test
	public void testRegistryBindingRegistryEClassEditing() throws PriorityCircularityException {
		EClass eClassToBind = EcorePackage.Literals.ECLASS;
		EClass eClassToEdit = EcoreFactory.eINSTANCE.createEClass();
		PropertiesEditingContext prepareEditingContext = prepareEditingContext(eClassToBind, eClassToEdit);
		assertNotNull("An EClass created from the Ecore metamodel registry cannot be edited by a binding model configured with an EClass from Ecore metamodel registry ", 
				prepareEditingContext.getEditingComponent());
		
	}
	
	//		- On fait un binding model en faisant référence au registry et on tente d'éditer une EClass créée dynamiquement par Ecore.ecore
	@Test
	public void testRegistryBindingEcoreFileEClassEditing() throws PriorityCircularityException {
		EClass eClassToBind = EcorePackage.Literals.ECLASS;
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
	 * @throws PriorityCircularityException 
	 */
	private PropertiesEditingContext prepareEditingContext(EClass eClassToBind, EObject eClassToEdit) throws PriorityCircularityException {
		final PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID).bindClass(eClassToBind)
												.withView(SampleView.class)
														.bindProperty(eClassToBind.getEStructuralFeature("name"))
															.withEditor("name")
					.build();
		EEFTestEnvironment env = new EEFTestEnvironment.Builder()
																.setEditingModel(editingModel)
																.setEditedObject(EcoreFactory.eINSTANCE.createEClass())
															.build();
		return env.getEditingContext();
	}
	
}
