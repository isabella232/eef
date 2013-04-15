/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
import org.eclipse.emf.eef.runtime.tests.views.SampleTitleView;
import org.eclipse.emf.eef.runtime.tests.views.SampleView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EClassBindingResolverTests {
	
	/**
	 * This EObject is just used to helper the EMFService selection. Since we use the default service, any EObject satisfying the selection.
	 */
	private EClass sample;
	
	private EMFServiceProvider emfServiceProvider;
	
	@Before
	public void setUp() {
		sample = EcoreFactory.eINSTANCE.createEClass();
		EEFTestEnvironment build = new EEFTestEnvironment.Builder().build();
		emfServiceProvider = build.getEMFServiceProvider();
	}

	/**
	 * Here the "EClass" EClass is binded to the view SampleView. No PropertyBinding is
	 * specified. The "feature" method on the "name" editor (just "name" String) should
	 * return EcorePackage.Literals.ENAMED_ELEMENT__NAME.
	 */
	@Test
	public void testFeatureSearchOnCompositeViewBindingWithoutPropertyBinding() {
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(SampleView.class)
			.build();
		assertEquals(EcorePackage.Literals.ENAMED_ELEMENT__NAME, editingModel.getBindings().get(0).feature("name", true));
	}

	/**
	 * Here the "EClass" EClass is binded to the view SampleView. No PropertyBinding is
	 * specified. The "editor" method on the EcorePackage.Literals.ENAMED_ELEMENT__NAME feature should
	 * return the "name" editor (just "name" String) .
	 */
	@Test
	public void testEditorSearchOnCompositeViewBindingWithoutPropertyBinding() {
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
		.bindClass(EcorePackage.Literals.ECLASS)
			.withView(SampleView.class)
		.build();
		editingModel.setEMFServiceProvider(emfServiceProvider);
		assertEquals("name", editingModel.getBindings().get(0).propertyEditor(sample, EcorePackage.Literals.ENAMED_ELEMENT__NAME, true));		
	}

	/**
	 * Here the "EClass" EClass is binded to the view SampleTitleView. A PropertyBinding is
	 * specified. The "feature" method on the "title" editor (just "title" String) should
	 * return EcorePackage.Literals.ENAMED_ELEMENT__NAME.
	 */
	@Test
	public void testFeatureSearchOnCompositeViewBindingWithPropertyBinding() {
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(SampleTitleView.class)
				.bindProperty(EcorePackage.Literals.ENAMED_ELEMENT__NAME)
					.withEditor( "title")
			.build();
		assertEquals(EcorePackage.Literals.ENAMED_ELEMENT__NAME, editingModel.getBindings().get(0).feature("title", true));
	}

	/**
	 * Here the "EClass" EClass is binded to the view SampleTitleView. A PropertyBinding is
	 * specified. The "editor" method on the EcorePackage.Literals.ENAMED_ELEMENT__NAME feature should
	 * return the "title" editor (just "title" String) .
	 */
	@Test
	public void testEditorSearchOnCompositeViewBindingWithPropertyBinding() {
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(SampleTitleView.class)
				.bindProperty(EcorePackage.Literals.ENAMED_ELEMENT__NAME)
					.withEditor("title")
			.build();
		editingModel.setEMFServiceProvider(emfServiceProvider);
		assertEquals("title", editingModel.getBindings().get(0).propertyEditor(sample, EcorePackage.Literals.ENAMED_ELEMENT__NAME, true));
	}

	/**
	 * Here the "EClass" EClass is binded to a "Sample" {@link View}. No PropertyBinding is
	 * specified. The "feature" method on the "abstract" {@link ElementEditor} should
	 * return EcorePackage.Literals.ECLASS__ABSTRACT.
	 */
	@Test
	public void testFeatureSearchOnEEFViewBindingWithoutPropertyBinding() {
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample View");
		ElementEditor abstractEditor = ViewsFactory.eINSTANCE.createElementEditor();
		abstractEditor.setName("abstract");
		sampleView.getElements().add(abstractEditor);
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(sampleView)
			.build();
		assertEquals(EcorePackage.Literals.ECLASS__ABSTRACT, editingModel.getBindings().get(0).feature(abstractEditor, true));
	}

	/**
	 * Here the "EClass" EClass is binded to a "Sample" {@link View}. No PropertyBinding is
	 * specified. The "propertyEditor" method on the EcorePackage.Literals.ECLASS__ABSTRACT feature should
	 * return "abstract" {@link ElementEditor} .
	 */
	@Test
	public void testEditorSearchOnEEFViewBindingWithoutPropertyBinding() {
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample View");
		ElementEditor abstractEditor = ViewsFactory.eINSTANCE.createElementEditor();
		abstractEditor.setName("abstract");
		sampleView.getElements().add(abstractEditor);
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(sampleView)
			.build();
		editingModel.setEMFServiceProvider(emfServiceProvider);
		assertEquals(abstractEditor, editingModel.getBindings().get(0).propertyEditor(sample, EcorePackage.Literals.ECLASS__ABSTRACT, true));
	}

	/**
	 * Here the "EClass" EClass is binded to a "Sample" {@link View}. A PropertyBinding is
	 * specified. The "feature" method on the "instanciable" {@link ElementEditor} should
	 * return EcorePackage.Literals.ECLASS__ABSTRACT.
	 */
	@Test
	public void testFeatureSearchOnEEFViewBindingWithPropertyBinding() {
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample View");
		ElementEditor instanciableEditor = ViewsFactory.eINSTANCE.createElementEditor();
		instanciableEditor.setName("instanciable");
		sampleView.getElements().add(instanciableEditor);
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(sampleView)
			.bindProperty(EcorePackage.Literals.ECLASS__ABSTRACT)
				.withEditor(instanciableEditor)
			.build();
		assertEquals(EcorePackage.Literals.ECLASS__ABSTRACT, editingModel.getBindings().get(0).feature(instanciableEditor, true));
	}

	/**
	 * Here the "EClass" EClass is binded to a "Sample" {@link View}. A PropertyBinding is
	 * specified. The "feature" method on the EcorePackage.Literals.ECLASS__ABSTRACT feature should
	 * return "instanciable" {@link ElementEditor}.
	 */
	@Test
	public void testEditorSearchOnEEFViewBindingWithPropertyBinding() {
		View sampleView = ViewsFactory.eINSTANCE.createView();
		sampleView.setName("Sample View");
		ElementEditor instanciableEditor = ViewsFactory.eINSTANCE.createElementEditor();
		instanciableEditor.setName("instanciable");
		sampleView.getElements().add(instanciableEditor);
		PropertiesEditingModel editingModel = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
			.bindClass(EcorePackage.Literals.ECLASS)
				.withView(sampleView)
			.bindProperty(EcorePackage.Literals.ECLASS__ABSTRACT)
				.withEditor(instanciableEditor)
			.build();
		editingModel.setEMFServiceProvider(emfServiceProvider);
		assertEquals(instanciableEditor, editingModel.getBindings().get(0).propertyEditor(sample, EcorePackage.Literals.ECLASS__ABSTRACT, true));
	}

}
