/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

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
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.tests.views.RootView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingModelBuilderTests {
	
	private PropertiesEditingModel expectedModel1;
	private PropertiesEditingModel expectedModel2;
	
	@Before
	public void setUp() {
		buildExpectedModel1();
		buildExpectedModel2();
	}

	@Test
	public void testNoBindingForEClassWithoutView() {
		PropertiesEditingModel model = new EditingModelBuilder().bindClass(BindingmodelPackage.Literals.SAMPLE).build();
		assertNotNull(model);
		assertEquals("A EClassBinding shouldn't be created without a view.", 0, model.getBindings().size());
	}
	
	@Test
	public void testBuilderSyntax1() {
		PropertiesEditingModel model = 
				new EditingModelBuilder()
					.bindClass(BindingmodelPackage.Literals.SAMPLE).withView(SampleView.class)
					.bindClass(BindingmodelPackage.Literals.ROOT).withView(RootView.class)
					.build();
		assertTrue(EcoreUtil.equals(model, expectedModel1));
	}

	@Test
	public void testBuilderSyntax2() {
		PropertiesEditingModel model = 
				new EditingModelBuilder()
					.bindClass(BindingmodelPackage.Literals.SAMPLE)
						.bindProperty(BindingmodelPackage.Literals.ABSTRACT_SAMPLE__NAME, "title")
					.withView(SampleView.class)
						.bindProperty(BindingmodelPackage.Literals.SAMPLE__ACTIVE, "activity")
					.build();
		assertTrue(EcoreUtil.equals(model, expectedModel2));
	}

	/**
	 * 
	 */
	private void buildExpectedModel1() {
		expectedModel1 = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(BindingmodelPackage.Literals.SAMPLE);
		JavaView sampleView = EditingModelFactory.eINSTANCE.createJavaView();
		sampleView.setDefinition(SampleView.class);
		eClassBinding.getViews().add(sampleView);
		expectedModel1.getBindings().add(eClassBinding);
		eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(BindingmodelPackage.Literals.ROOT);
		JavaView rootView = EditingModelFactory.eINSTANCE.createJavaView();
		rootView.setDefinition(RootView.class);
		eClassBinding.getViews().add(rootView);
		expectedModel1.getBindings().add(eClassBinding);
	}
	
	/**
	 * 
	 */
	private void buildExpectedModel2() {
		expectedModel2 = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(BindingmodelPackage.Literals.SAMPLE);
		JavaView sampleView = EditingModelFactory.eINSTANCE.createJavaView();
		sampleView.setDefinition(SampleView.class);
		eClassBinding.getViews().add(sampleView);
		PropertyBinding namePropertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		namePropertyBinding.setFeature(BindingmodelPackage.Literals.ABSTRACT_SAMPLE__NAME);
		namePropertyBinding.setEditor("title");
		eClassBinding.getPropertyBindings().add(namePropertyBinding);
		PropertyBinding activePropertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		activePropertyBinding.setFeature(BindingmodelPackage.Literals.SAMPLE__ACTIVE);
		activePropertyBinding.setEditor("activity");
		eClassBinding.getPropertyBindings().add(activePropertyBinding);
		expectedModel2.getBindings().add(eClassBinding);
	}

}
