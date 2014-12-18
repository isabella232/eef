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
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Generic Binding Settings.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettings2TestCase extends AbstractGenericBindingSettingsTestCase {

	private static final String URI2 = "uri2";
	private static final String URI1 = "uri1";
	private GenericBindingSettings genericBindingSettings;
	private EObject class1;
	private EObject class2;
	private EObject class3;

	@Before
	public void setUp() throws Exception {
		Resource resource = new ResourceImpl();
		resource.setURI(URI.createURI(URI1));
		genericBindingSettings = initGenericBindingSettings(resource);

		// First package : 2 classes
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setNsURI(URI1);
		Object[] createEObjectWithSingleEnum = GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, GenericBindingSettingsUtil.CLASS1, XMLTypePackage.eINSTANCE.getString(), URI1);
		class1 = (EObject) createEObjectWithSingleEnum[0];
		Object[] createEObjectWithSingleStringAttribute = GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage, GenericBindingSettingsUtil.CLASS2, XMLTypePackage.eINSTANCE.getString(), URI1);
		class2 = (EObject) createEObjectWithSingleStringAttribute[0];

		// Second package : 1 class
		EPackage ePackage2 = EcoreFactory.eINSTANCE.createEPackage();
		ePackage2.setNsURI(URI2);
		Object[] createEObjectWithSingleStringAttribute2 = GenericBindingSettingsUtil.createEObjectWithSingleStringAttribute(ePackage2, GenericBindingSettingsUtil.CLASS1, XMLTypePackage.eINSTANCE.getString(), URI2);
		class3 = (EObject) createEObjectWithSingleStringAttribute2[0];

		resource.getContents().add(ePackage);
		resource.getContents().add(ePackage2);
	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testPropertiesEditingModelNumber() throws Exception {
		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);

		// properties editing model with 1 uri
		checkPropertiesEditingModel(propertiesEditingModel, URI1);

		// properties editing model with 1 uri
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		checkPropertiesEditingModel(propertiesEditingModel, URI1);

		// properties editing model with 1 uri
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class2);
		checkPropertiesEditingModel(propertiesEditingModel, URI1);

		// change package
		// properties editing model with 2 uris
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class3);
		checkPropertiesEditingModel(propertiesEditingModel, URI1, URI2);

		// properties editing model with 2 uris
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class3);
		checkPropertiesEditingModel(propertiesEditingModel, URI1, URI2);

		// properties editing model with 2 uris
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class2);
		checkPropertiesEditingModel(propertiesEditingModel, URI1, URI2);

		propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		checkPropertiesEditingModel(propertiesEditingModel, URI1, URI2);

		// properties editing model with 2 uris
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class3);
		checkPropertiesEditingModel(propertiesEditingModel, URI1, URI2);

	}

	/**
	 * @throws Exception
	 */
	@Test
	public void testPropertiesEditingModelClassBindingNumber() throws Exception {
		PropertiesEditingModel propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);

		// check one binding on CLASS1
		checkOneBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1);

		// check one binding on CLASS1
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		checkOneBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1);

		// check 2 bindings on CLASS1 and CLASS2
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class2);
		checkTwoBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1, GenericBindingSettingsUtil.CLASS2);

		// check 2 bindings on CLASS1 and CLASS2
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class2);
		checkTwoBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1, GenericBindingSettingsUtil.CLASS2);

		// check 2 bindings on CLASS1 and CLASS2
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class1);
		checkTwoBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1, GenericBindingSettingsUtil.CLASS2);

		// check 2 bindings on CLASS1 and CLASS2
		propertiesEditingModel = genericBindingSettings.getEEFDescription(class2);
		checkTwoBinding(propertiesEditingModel, GenericBindingSettingsUtil.CLASS1, GenericBindingSettingsUtil.CLASS2);

	}

	/**
	 * Check one binding
	 * 
	 * @param propertiesEditingModel
	 * @param name
	 */
	public void checkOneBinding(PropertiesEditingModel propertiesEditingModel, String name) {
		assertEquals("PEM should have 1 binding.", 1, propertiesEditingModel.getBindings().size());
		EClassBinding eClassBinding = propertiesEditingModel.getBindings().get(0);
		assertEquals("Binding name is not correct.", name, eClassBinding.getEClass().getName());
	}

	/**
	 * Check 2 bindings
	 * 
	 * @param propertiesEditingModel
	 * @param name
	 * @param name2
	 */
	public void checkTwoBinding(PropertiesEditingModel propertiesEditingModel, String name, String name2) {
		assertEquals("PEM should have 2 bindings.", 2, propertiesEditingModel.getBindings().size());
		EClassBinding eClassBinding = propertiesEditingModel.getBindings().get(0);
		assertEquals("Binding name is not correct.", name, eClassBinding.getEClass().getName());
		EClassBinding eClassBinding2 = propertiesEditingModel.getBindings().get(1);
		assertEquals("Binding name is not correct.", name2, eClassBinding2.getEClass().getName());
	}

	/**
	 * Check one uri in map
	 * 
	 * @param propertiesEditingModel
	 * @param uri
	 */
	public void checkPropertiesEditingModel(PropertiesEditingModel propertiesEditingModel, String uri) {
		assertNotNull("PEM should not be null.", propertiesEditingModel);
		assertNotNull("Map should not be null.", genericBindingSettings.getMapURI2PropertiesEditingModel());
		assertEquals("The map should have 1 PEM.", 1, genericBindingSettings.getMapURI2PropertiesEditingModel().size());
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri));
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri).getContents().get(0));
		assertEquals("PEMs should be equals.", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri).getContents().get(0), propertiesEditingModel);
	}

	/**
	 * Check 2 uris in map
	 * 
	 * @param propertiesEditingModel
	 * @param uri
	 * @param uri2
	 */
	public void checkPropertiesEditingModel(PropertiesEditingModel propertiesEditingModel, String uri, String uri2) {
		assertNotNull("PEM should not be null.", propertiesEditingModel);
		assertNotNull("Map should not be null.", genericBindingSettings.getMapURI2PropertiesEditingModel());
		assertEquals("The map should have 2 PEMs.", 2, genericBindingSettings.getMapURI2PropertiesEditingModel().size());
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri));
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri2));
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri).getContents().get(0));
		assertNotNull("PEM should not be null", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri2).getContents().get(0));
		assertTrue("PEMs should be equals.", genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri).getContents().get(0).equals(propertiesEditingModel) || genericBindingSettings.getMapURI2PropertiesEditingModel().get(uri2).getContents().get(0).equals(propertiesEditingModel));
	}
}
