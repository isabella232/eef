/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelPackage;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelBuilderTests {
	
	private PropertiesEditingModel fullExpected;
	
	@Before
	public void setUp() {
		fullExpected = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(BindingmodelPackage.Literals.SAMPLE);
		JavaView sampleView = EditingModelFactory.eINSTANCE.createJavaView();
		sampleView.setDefinition(SampleView.class);
		eClassBinding.getViews().add(sampleView);
		fullExpected.getBindings().add(eClassBinding);
		eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(BindingmodelPackage.Literals.ROOT);
		JavaView rootView = EditingModelFactory.eINSTANCE.createJavaView();
		rootView.setDefinition(RootView.class);
		eClassBinding.getViews().add(rootView);
		fullExpected.getBindings().add(eClassBinding);		
	}
	
	@Test
	public void testNoBindingForEClassWithoutView() {
		PropertiesEditingModel model = new EditingModelBuilder().bindClass(BindingmodelPackage.Literals.SAMPLE).build();
		assertNotNull(model);
		assertEquals("A EClassBinding shouldn't be created without a view.", 0, model.getBindings().size());
	}
	
	@Test
	public void testFullBindingSyntax() {
		PropertiesEditingModel model = 
				new EditingModelBuilder()
					.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(SampleView.class)
					.bindClass(BindingmodelPackage.Literals.ROOT).withView(RootView.class)
					.build();
		assertTrue(EcoreUtil.equals(model, fullExpected));
	}
}
