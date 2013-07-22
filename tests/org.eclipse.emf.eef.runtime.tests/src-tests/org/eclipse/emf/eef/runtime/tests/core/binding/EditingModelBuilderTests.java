/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.tests.core.binding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelBuilder;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.JavaEditor;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironment;
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
		PropertiesEditingModel model = new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID).bindClass(EcorePackage.Literals.ECLASS).build();
		assertNotNull(model);
		assertEquals("A EClassBinding shouldn't be created without a view.", 0, model.getBindings().size());
	}
	
	@Test
	public void testBuilderSyntax1() {
		PropertiesEditingModel model = 
				new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
					.bindClass(EcorePackage.Literals.ECLASS).withView(SampleView.class)
					.bindClass(EcorePackage.Literals.EPACKAGE).withView(RootView.class)
					.build();
		assertTrue(EcoreUtil.equals(model, expectedModel1));
	}

	@Test
	public void testBuilderSyntax2() {
		PropertiesEditingModel model = 
				new EditingModelBuilder(EEFTestEnvironment.TESTS_EDITING_MODEL_ID)
					.bindClass(EcorePackage.Literals.ECLASS)
						.bindProperty(EcorePackage.Literals.ENAMED_ELEMENT__NAME)
							.withEditor("title")
					.withView(SampleView.class)
						.bindProperty(EcorePackage.Literals.ECLASS__ABSTRACT)
							.withEditor("abstract")
					.build();
		assertTrue(EcoreUtil.equals(model, expectedModel2));
	}

	/**
	 * 
	 */
	private void buildExpectedModel1() {
		expectedModel1 = EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
		expectedModel1.setId(EEFTestEnvironment.TESTS_EDITING_MODEL_ID);
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(EcorePackage.Literals.ECLASS);
		JavaView sampleView = EditingModelFactory.eINSTANCE.createJavaView();
		sampleView.setDefinition(SampleView.class);
		eClassBinding.getViews().add(sampleView);
		expectedModel1.getBindings().add(eClassBinding);
		eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(EcorePackage.Literals.EPACKAGE);
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
		expectedModel2.setId(EEFTestEnvironment.TESTS_EDITING_MODEL_ID);
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		eClassBinding.setEClass(EcorePackage.Literals.ECLASS);
		JavaView sampleView = EditingModelFactory.eINSTANCE.createJavaView();
		sampleView.setDefinition(SampleView.class);
		eClassBinding.getViews().add(sampleView);
		PropertyBinding namePropertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		namePropertyBinding.setFeature(EcorePackage.Literals.ENAMED_ELEMENT__NAME);
		JavaEditor titleEditor = EditingModelFactory.eINSTANCE.createJavaEditor();
		titleEditor.setDefinition("title");
		namePropertyBinding.setEditor(titleEditor);
		eClassBinding.getPropertyBindings().add(namePropertyBinding);
		PropertyBinding activePropertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		activePropertyBinding.setFeature(EcorePackage.Literals.ECLASS__ABSTRACT);
		JavaEditor activityEditor = EditingModelFactory.eINSTANCE.createJavaEditor();
		activityEditor.setDefinition("abstract");
		activePropertyBinding.setEditor(activityEditor);
		eClassBinding.getPropertyBindings().add(activePropertyBinding);
		expectedModel2.getBindings().add(eClassBinding);
	}

}
