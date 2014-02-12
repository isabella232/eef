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
package org.eclipse.emf.eef.runtime.unit.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.internal.util.EMFServiceImpl;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFServiceImplTests {

	private EMFService emfService;

	private ResourceSet resourceSet;

	private EPackage ecoreResourcePackage;

	private EPackage editingModelResourcePackage;

	private EPackage editingModelPluginPackage;

	@Before
	public void setUp() {
		emfService = new EMFServiceImpl();
		resourceSet = new ResourceSetImpl();
		// Register the appropriate resource factory to handle all file extensions.
		//
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put
			(Resource.Factory.Registry.DEFAULT_EXTENSION, 
			 new XMIResourceFactoryImpl());
		
		URI uri = URI.createPlatformPluginURI("org.eclipse.emf.ecore/model/Ecore.ecore", true);
		Resource resource = resourceSet.getResource(uri, true);
		ecoreResourcePackage = (EPackage)resource.getContents().get(0);
		uri = URI.createPlatformPluginURI("org.eclipse.emf.eef.runtime/model/editingModel.ecore", true);
		resource = resourceSet.getResource(uri, true);
		editingModelResourcePackage = (EPackage)resource.getContents().get(0);
		editingModelPluginPackage = EditingModelFactory.eINSTANCE.createEClassBinding().eClass().getEPackage();
	}

	@Test
	public void testPackageEquality() {
		EPackage ePack1 = EcoreFactory.eINSTANCE.createEPackage();
		assertTrue("Standard EMFService implementation doesn't correctly handle EPackage binary equality", emfService.equals(ePack1, ePack1));

		assertTrue("Standard EMFService implementation doesn't correctly handle EPackage from resource > EPackage from plugin equality", emfService.equals(ecoreResourcePackage, ePack1.eClass().getEPackage()));
		assertTrue("Standard EMFService implementation doesn't correctly handle EPackage from resource > EPackage from plugin equality", emfService.equals(ecoreResourcePackage, EcorePackage.eINSTANCE));
		assertTrue("Standard EMFService implementation doesn't correctly handle EPackage from plugin > EPackage from resource equality", emfService.equals(editingModelPluginPackage, editingModelResourcePackage));
		assertTrue("Standard EMFService implementation doesn't correctly handle EPackage from plugin > EPackage from resource equality", emfService.equals(EditingModelPackage.eINSTANCE, editingModelResourcePackage));

		assertFalse("Standard EMFService implementation doesn't correctly handle package difference", emfService.equals(editingModelPluginPackage, ecoreResourcePackage));
		assertFalse("Standard EMFService implementation doesn't correctly handle package difference", emfService.equals(editingModelResourcePackage, ecoreResourcePackage));
	}

	@Test
	public void testEClassEquality() {
		EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		assertTrue("Standard EMFService implementation doesn't correctly handle EClass binary equality", emfService.equals(eClass1, eClass1));

		EClass eClassFromResource = (EClass) ecoreResourcePackage.getEClassifier("EClass");
		assertTrue("Standard EMFService implementation doesn't correctly handle EClass from resource > EClass from plugin equality", emfService.equals(eClassFromResource, eClass1.eClass()));
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		EClass eClassBindingFromResource = (EClass) editingModelResourcePackage.getEClassifier("EClassBinding");
		assertTrue("Standard EMFService implementation doesn't correctly handle EClass from plugin > EClass from resource equality", emfService.equals(eClassBinding.eClass(), eClassBindingFromResource));

		assertFalse("Standard EMFService implementation doesn't correctly handle EClass difference", emfService.equals(eClassBindingFromResource, eClassFromResource));
		assertFalse("Standard EMFService implementation doesn't correctly handle EClass difference", emfService.equals(eClassBinding.eClass(), eClass1.eClass()));

	}

	@Test
	public void testEStructuralFeatureEquality() {
		EStructuralFeature feature1 = EcoreFactory.eINSTANCE.createEAttribute();
		feature1.setName("attribute");
		EStructuralFeature feature2 = EcoreFactory.eINSTANCE.createEReference();
		feature2.setName("reference");
		EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.getEStructuralFeatures().add(feature1);
		eClass1.getEStructuralFeatures().add(feature2);
		assertTrue("Standard EMFService implementation doesn't correctly handle EAttribute binary equality", emfService.equals(feature1, feature1));
		assertTrue("Standard EMFService implementation doesn't correctly handle EReference binary equality", emfService.equals(feature2, feature2));

		EClass eClass = (EClass) ecoreResourcePackage.getEClassifier("EClass");
		EStructuralFeature eStructuralFeatureFromResource = eClass.getEStructuralFeature("name");
		EStructuralFeature eStructuralFeatureFromPlugin = eClass1.eClass().getEStructuralFeature("name");
		assertTrue("Standard EMFService implementation doesn't correctly handle EStructuralFeature from resource > EStructuralFeature from plugin equality", emfService.equals(eStructuralFeatureFromResource, eStructuralFeatureFromPlugin));
		EClass eClassBindingFromResource = (EClass) editingModelResourcePackage.getEClassifier("EClassBinding");
		EStructuralFeature propertyBindingsFromResource = eClassBindingFromResource.getEStructuralFeature("propertyBindings");
		EClassBinding eClassBinding = EditingModelFactory.eINSTANCE.createEClassBinding();
		PropertyBinding propertyBinding = EditingModelFactory.eINSTANCE.createPropertyBinding();
		eClassBinding.getPropertyBindings().add(propertyBinding);
		assertTrue("Standard EMFService implementation doesn't correctly handle EStructuralFeature from plugin > EStructuralFeature from resource equality", emfService.equals(propertyBinding.eContainingFeature(), propertyBindingsFromResource));		

	}
}
