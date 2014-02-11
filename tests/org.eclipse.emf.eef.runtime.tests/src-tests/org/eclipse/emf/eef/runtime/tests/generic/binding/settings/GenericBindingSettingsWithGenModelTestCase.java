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
package org.eclipse.emf.eef.runtime.tests.generic.binding.settings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.codegen.ecore.genmodel.GenPropertyKind;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Generic Binding Settings.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettingsWithGenModelTestCase extends AbstractGenericBindingSettingsTestCase {

	private static final String URI1 = "uri1";
	private GenericBindingSettings genericBindingSettings;
	private EObject class1;
	private EClass eClass1;
	private EAttribute eAttribute1_String;
	private EAttribute eAttribute2_StringMultiLine;
	private EAttribute eAttribute3_EnumMultiLine;
	private EReference eReference_MultiLine;

	private boolean isMultiLine = true;
	private String noCategory = "";
	private String category1 = "Cat1";
	private String category2 = "Cat2";
	private GenPackage genPackage;
	private GenClass genClass;

	@Before
	public void setUp() throws Exception {

		Resource resource = new ResourceImpl();
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setNsURI(URI1);
		resource.getContents().add(ePackage);

		Object[] createEObjectWithSingleStringAttribute = GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, GenericBindingSettingsUtil.CLASS1, XMLTypePackage.eINSTANCE.getString(), URI1);
		class1 = (EObject) createEObjectWithSingleStringAttribute[0];
		eClass1 = (EClass) createEObjectWithSingleStringAttribute[1];
		eAttribute1_String = (EAttribute) createEObjectWithSingleStringAttribute[2];

		eAttribute2_StringMultiLine = GenericBindingSettingsUtil.addSingleStringAttribute(eClass1, XMLTypePackage.eINSTANCE.getString());
		EEnum enum1 = GenericBindingSettingsUtil.createEEnum(ePackage, GenericBindingSettingsUtil.ENUM1);
		eAttribute3_EnumMultiLine = GenericBindingSettingsUtil.addSingleStringAttribute(eClass1, enum1);
		eAttribute3_EnumMultiLine.setName(GenericBindingSettingsUtil.SINGLE_ENUM);

		EClass eClass2 = GenericBindingSettingsUtil.createEClass(ePackage, GenericBindingSettingsUtil.CLASS2);
		eReference_MultiLine = GenericBindingSettingsUtil.addSingleReference(eClass1, eClass2);

		genPackage = GenModelFactory.eINSTANCE.createGenPackage();
		genPackage.setEcorePackage(ePackage);
		genClass = GenModelFactory.eINSTANCE.createGenClass();
		genClass.setEcoreClass(eClass1);

		Resource resourceGenModel = new ResourceImpl();
		resourceGenModel.getContents().add(genPackage);

		genericBindingSettings = initGenericBindingSettings(resource, resourceGenModel);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testGenModelMultiLine() throws Exception {
		// build genModel
		addGenFeature(genPackage, genClass, eAttribute1_String, !isMultiLine, GenPropertyKind.EDITABLE_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eAttribute2_StringMultiLine, isMultiLine, GenPropertyKind.EDITABLE_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eAttribute3_EnumMultiLine, isMultiLine, GenPropertyKind.EDITABLE_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eReference_MultiLine, isMultiLine, GenPropertyKind.EDITABLE_LITERAL, noCategory);

		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		// test EClass binding
		assertEquals("1 EClass binding one EClass", 1, propertiesEditingModel.getBindings().size());
		EClassBinding classBinding = propertiesEditingModel.getBindings().get(0);

		// test property binding
		assertEquals("4 property binding", 4, classBinding.getPropertyBindings().size());

		// test attribute 1 : text
		PropertyBinding propertyBinding1 = classBinding.getPropertyBindings().get(0);
		checkPropertyBinding(propertyBinding1, eAttribute1_String, GenericBindingSettings.TEXT_WIDGET_NAME, false, GenericBindingSettingsUtil.CLASS1);

		// test attribute 2 : text area
		propertyBinding1 = classBinding.getPropertyBindings().get(1);
		checkPropertyBinding(propertyBinding1, eAttribute2_StringMultiLine, GenericBindingSettings.TEXTAREA_WIDGET_NAME, false, GenericBindingSettingsUtil.CLASS1);

		// test attribute 3 : enum
		propertyBinding1 = classBinding.getPropertyBindings().get(2);
		checkPropertyBinding(propertyBinding1, eAttribute3_EnumMultiLine, GenericBindingSettings.COMBO_WIDGET_NAME, false, GenericBindingSettingsUtil.CLASS1);

		// test reference : ref
		propertyBinding1 = classBinding.getPropertyBindings().get(3);
		checkPropertyBinding(propertyBinding1, eReference_MultiLine, GenericBindingSettings.ECOMBO_EDITOR_WIDGET_NAME, false, GenericBindingSettingsUtil.CLASS1);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testGenModelEditableMode() throws Exception {
		// build genModel
		addGenFeature(genPackage, genClass, eAttribute1_String, !isMultiLine, GenPropertyKind.NONE_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eAttribute2_StringMultiLine, isMultiLine, GenPropertyKind.READONLY_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eAttribute3_EnumMultiLine, isMultiLine, GenPropertyKind.READONLY_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eReference_MultiLine, isMultiLine, GenPropertyKind.NONE_LITERAL, noCategory);

		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		// test EClass binding
		assertEquals("1 EClass binding one EClass", 1, propertiesEditingModel.getBindings().size());
		EClassBinding classBinding = propertiesEditingModel.getBindings().get(0);

		// test property binding
		assertEquals("2 property binding", 2, classBinding.getPropertyBindings().size());

		// test attribute 2 : textarea read only
		PropertyBinding propertyBinding1 = classBinding.getPropertyBindings().get(0);
		checkPropertyBinding(propertyBinding1, eAttribute2_StringMultiLine, GenericBindingSettings.TEXTAREA_WIDGET_NAME, true, GenericBindingSettingsUtil.CLASS1);

		// test attribute 3 : enum
		propertyBinding1 = classBinding.getPropertyBindings().get(1);
		checkPropertyBinding(propertyBinding1, eAttribute3_EnumMultiLine, GenericBindingSettings.COMBO_WIDGET_NAME, true, GenericBindingSettingsUtil.CLASS1);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testGenModelCategory() throws Exception {
		// build genModel
		addGenFeature(genPackage, genClass, eAttribute1_String, !isMultiLine, GenPropertyKind.EDITABLE_LITERAL, category1);
		addGenFeature(genPackage, genClass, eAttribute2_StringMultiLine, isMultiLine, GenPropertyKind.READONLY_LITERAL, noCategory);
		addGenFeature(genPackage, genClass, eAttribute3_EnumMultiLine, isMultiLine, GenPropertyKind.READONLY_LITERAL, category2);
		addGenFeature(genPackage, genClass, eReference_MultiLine, isMultiLine, GenPropertyKind.EDITABLE_LITERAL, category1);

		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		// test EClass binding
		assertEquals("1 EClass binding one EClass", 1, propertiesEditingModel.getBindings().size());
		EClassBinding classBinding = propertiesEditingModel.getBindings().get(0);

		// test property binding
		assertEquals("4 property binding", 4, classBinding.getPropertyBindings().size());

		// test attribute 1 : text
		PropertyBinding propertyBinding1 = classBinding.getPropertyBindings().get(0);
		checkPropertyBinding(propertyBinding1, eAttribute1_String, GenericBindingSettings.TEXT_WIDGET_NAME, false, category1);

		// test attribute 2 : text area
		propertyBinding1 = classBinding.getPropertyBindings().get(1);
		checkPropertyBinding(propertyBinding1, eAttribute2_StringMultiLine, GenericBindingSettings.TEXTAREA_WIDGET_NAME, true, GenericBindingSettingsUtil.CLASS1);

		// test attribute 3 : enum
		propertyBinding1 = classBinding.getPropertyBindings().get(2);
		checkPropertyBinding(propertyBinding1, eAttribute3_EnumMultiLine, GenericBindingSettings.COMBO_WIDGET_NAME, true, category2);

		// test reference : ref
		propertyBinding1 = classBinding.getPropertyBindings().get(3);
		checkPropertyBinding(propertyBinding1, eReference_MultiLine, GenericBindingSettings.ECOMBO_EDITOR_WIDGET_NAME, false, category1);

	}

	/**
	 * @param propertyBinding1
	 * @param eStructuralFeature
	 * @param textWidgetName
	 * @param category
	 */
	public void checkPropertyBinding(PropertyBinding propertyBinding1, EStructuralFeature eStructuralFeature, String textWidgetName, boolean isReadOnly, String category) {
		assertEquals("Bad feature", eStructuralFeature, propertyBinding1.getFeature());

		// test property binding editor
		assertTrue("Editor should be instanceof EObjectEditor", propertyBinding1.getEditor() instanceof EObjectEditor);
		EObjectEditor editor = (EObjectEditor) propertyBinding1.getEditor();
		assertTrue("Definition should be an ElementEditor", editor.getDefinition() instanceof ElementEditor);
		ElementEditor elementEditor = (ElementEditor) editor.getDefinition();
		// test representation
		assertEquals("Representation should be a " + textWidgetName, textWidgetName, elementEditor.getRepresentation().getName());
		// test read only
		assertEquals("Representation should be read only/editable", isReadOnly, elementEditor.isReadOnly());
		// test category
		assertEquals("Category is wrong", category, ((Container) elementEditor.eContainer()).getName());
	}

	/**
	 * @param genPackage
	 * @param genClass
	 */
	public GenFeature addGenFeature(GenPackage genPackage, GenClass genClass, EStructuralFeature feature, boolean isMultiLine, GenPropertyKind editable, String category) {
		GenFeature genFeature = GenModelFactory.eINSTANCE.createGenFeature();
		genFeature.setEcoreFeature(feature);
		genFeature.setPropertyMultiLine(isMultiLine);
		genFeature.setPropertyCategory(category);
		genFeature.setProperty(editable);
		genClass.getGenFeatures().add(genFeature);
		genPackage.getGenClasses().add(genClass);
		return genFeature;
	}

}
