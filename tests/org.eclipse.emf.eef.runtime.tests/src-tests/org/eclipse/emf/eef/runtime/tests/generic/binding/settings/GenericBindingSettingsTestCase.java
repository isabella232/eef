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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EObjectEditor;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ElementEditor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test for Generic Binding Settings.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
@RunWith(Parameterized.class)
public class GenericBindingSettingsTestCase extends AbstractGenericBindingSettingsTestCase {

	private EObject eObject;
	private EClass eClass;
	private EStructuralFeature eStructuralFeature;

	/**
	 * @param eObject
	 *            EObject
	 */
	public GenericBindingSettingsTestCase(EObject eObject, EClass eclass, EStructuralFeature eStructuralFeature) {
		this.eObject = eObject;
		this.eClass = eclass;
		this.eStructuralFeature = eStructuralFeature;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Parameters
	public static Collection<Object[]> data() throws Exception {
		Resource resource = new ResourceImpl();
		resource.setURI(URI.createURI("http://testEEF"));
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setNsURI("http://testEEF");
		resource.getContents().add(ePackage);
		Object[][] data = new Object[][] { GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C1", XMLTypePackage.eINSTANCE.getString()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C13", XMLTypePackage.eINSTANCE.getInt()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C2", XMLTypePackage.eINSTANCE.getDouble()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C14", XMLTypePackage.eINSTANCE.getDoubleObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C3", XMLTypePackage.eINSTANCE.getInteger()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C15", XMLTypePackage.eINSTANCE.getIntObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C4", XMLTypePackage.eINSTANCE.getFloat()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C16", XMLTypePackage.eINSTANCE.getFloatObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C5", XMLTypePackage.eINSTANCE.getLong()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C17", XMLTypePackage.eINSTANCE.getLongObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C6", XMLTypePackage.eINSTANCE.getShort()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C18", XMLTypePackage.eINSTANCE.getShortObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C7", XMLTypePackage.eINSTANCE.getUnsignedByte()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C19", XMLTypePackage.eINSTANCE.getUnsignedByteObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C8", XMLTypePackage.eINSTANCE.getUnsignedInt()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C20", XMLTypePackage.eINSTANCE.getUnsignedIntObject()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C9", XMLTypePackage.eINSTANCE.getUnsignedLong()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C21", XMLTypePackage.eINSTANCE.getUnsignedShort()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C10", XMLTypePackage.eINSTANCE.getUnsignedShortObject()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C22", XMLTypePackage.eINSTANCE.getByte()),
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C11", XMLTypePackage.eINSTANCE.getByteObject()), GenericBindingSettingsUtil.createEObjectWithSingleBooleanAttribute(ePackage, "C23", XMLTypePackage.eINSTANCE.getBoolean()),
				GenericBindingSettingsUtil.createEObjectWithSingleBooleanAttribute(ePackage, "C12", XMLTypePackage.eINSTANCE.getBooleanObject()), GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, "C24", XMLTypePackage.eINSTANCE.getDecimal()),
				// attribute 0..*
				// TODO : uncomment with multi valued editor
				/*
				 * GenericBindingSettingsUtil.createEObjectWithMultiAttribute(
				 * ePackage, XMLTypePackage.eINSTANCE.getString()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getInt()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getDouble()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getDoubleObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getInteger()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getIntObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getFloat()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getFloatObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getLong()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getLongObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getShort()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getShortObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedByte()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedByteObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedInt()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedIntObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedLong()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedShort()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getUnsignedShortObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getByte()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getByteObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getBoolean()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getBooleanObject()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getDecimal()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getDate()),
				 * GenericBindingSettingsUtil
				 * .createEObjectWithMultiAttribute(ePackage,
				 * XMLTypePackage.eINSTANCE.getDateTime()),
				 */
				// enum
				GenericBindingSettingsUtil.createEObjectWithSingleEnum(ePackage, "C25"),
				// date
				GenericBindingSettingsUtil.createEObjectWithSingleDate(ePackage, "C26", XMLTypePackage.eINSTANCE.getDate()), GenericBindingSettingsUtil.createEObjectWithSingleDate(ePackage, "C36", XMLTypePackage.eINSTANCE.getDateTime()),
				// commentaire
				GenericBindingSettingsUtil.createEObjectWithSingleStringAttributeComment(ePackage, "C27", XMLTypePackage.eINSTANCE.getString()),
				// reference 0..1
				GenericBindingSettingsUtil.createEObjectWithSingleReference(ePackage, "C28", "C29"),
				// reference 0..*
				GenericBindingSettingsUtil.createEObjectWithMultiReference(ePackage, "C30", "C31"),
				// composition 0..1
				GenericBindingSettingsUtil.createEObjectWithSingleComposition(ePackage, "C32", "C33"),
				// composition 0..*
				GenericBindingSettingsUtil.createEObjectWithMultiComposition(ePackage, "C34", "C35"), };
		return Arrays.asList(data);
	}

	@Test
	public void testGenericBindingSettings() throws Exception {
		GenericBindingSettings genericBindingSettings = initGenericBindingSettings(eClass.eResource());
		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(eObject);

		// test EClass binding
		assertEquals("1 EClass binding one EClass", 1, propertiesEditingModel.getBindings().size());
		EClassBinding classBinding = propertiesEditingModel.getBindings().get(0);
		assertEquals("Bad binding eclass", eClass, classBinding.getEClass());
		assertEquals("Bad properties editing model", propertiesEditingModel, classBinding.getEditingModel());

		// test class binding view
		assertEquals("1 view in class binding", 1, classBinding.getViews().size());
		assertTrue("View not instanceof EObjectView", classBinding.getViews().get(0) instanceof EObjectView);
		EObjectView eObjectView = (EObjectView) classBinding.getViews().get(0);
		assertTrue("Definition not instanceof org.eclipse.emf.eef.views.View", eObjectView.getDefinition() instanceof org.eclipse.emf.eef.views.View);
		org.eclipse.emf.eef.views.View view = (org.eclipse.emf.eef.views.View) eObjectView.getDefinition();
		assertNull("View representation should not be null", view.getRepresentation());
		assertEquals("1 element in view", 1, view.getElements().size());
		assertTrue("Element should be a Container", view.getElements().get(0) instanceof Container);
		Container container = (Container) view.getElements().get(0);
		assertNotNull("Representation should not be null", container.getRepresentation());
		assertTrue("Representation should be a group", container.getRepresentation().getName().equals(GenericBindingSettings.GROUP_CONTAINER_NAME));

		// test property binding
		assertEquals("1 property binding", 1, classBinding.getPropertyBindings().size());
		EStructuralFeatureBinding propertyBinding = (EStructuralFeatureBinding) classBinding.getPropertyBindings().get(0);
		assertEquals("Bad propertyBinding", eStructuralFeature, propertyBinding.getFeature());
		assertTrue("Setting should be empty", propertyBinding.getSettings().isEmpty());
		assertTrue("Property settings should be empty", propertyBinding.getSubPropertyBindings().isEmpty());

		// test property binding editor
		assertTrue("Editor should be instanceof EObjectEditor", propertyBinding.getEditor() instanceof EObjectEditor);
		EObjectEditor editor = (EObjectEditor) propertyBinding.getEditor();
		assertTrue("Definition should be an ElementEditor", editor.getDefinition() instanceof ElementEditor);
		ElementEditor elementEditor = (ElementEditor) editor.getDefinition();

		// test representation
		if (GenericBindingSettingsUtil.SINGLE_STRING_ATTRIBUTE.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a text", GenericBindingSettings.TEXT_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_BOOLEAN_ATTRIBUTE.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a checkbox", GenericBindingSettings.CHECKBOX_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_STRING_ATTRIBUTE_COMMENT.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a text area", GenericBindingSettings.TEXTAREA_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_ENUM.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a combo", GenericBindingSettings.COMBO_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_DATE.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a date picker", GenericBindingSettings.EDATE_PICKER_EDITOR_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.MULTI_ATTRIBUTE.equals(eStructuralFeature.getName())) {
			// TODO : uncomment with multi valued editor
			// assertEquals("Representation should be a multi valued editor",
			// GenericBindingSettings.?,
			// elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_REFERENCE.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a ecombo editor", GenericBindingSettings.ECOMBO_EDITOR_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.MULTI_REFERENCE.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a ereference editor", GenericBindingSettings.EREFERENCE_EDITOR_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.SINGLE_COMPOSITION.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a single containment", GenericBindingSettings.SINGLE_CONTAINMENT_EDITOR_WIDGET_NAME, elementEditor.getRepresentation().getName());
		} else if (GenericBindingSettingsUtil.MULTI_COMPOSITION.equals(eStructuralFeature.getName())) {
			assertEquals("Representation should be a econtainment editor", GenericBindingSettings.ECONTAINMENT_EDITOR_WIDGET_NAME, elementEditor.getRepresentation().getName());
		}
	}

}
