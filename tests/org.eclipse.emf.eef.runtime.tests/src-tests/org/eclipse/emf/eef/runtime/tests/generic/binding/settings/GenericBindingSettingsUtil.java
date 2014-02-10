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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Util class to create data for Generic binding setting test.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class GenericBindingSettingsUtil {

	public static final String ENUM1 = "Enum1";
	public static final String CLASS1 = "Class1";
	public static final String CLASS2 = "Class2";
	public static final String SINGLE_STRING_ATTRIBUTE = "singleStringAttribute";
	public static final String SINGLE_STRING_ATTRIBUTE_COMMENT = "documentation";
	public static final String SINGLE_ENUM = "singleEnum";
	public static final String SINGLE_DATE = "singleDate";
	public static final String SINGLE_REFERENCE = "singleReference";
	public static final String SINGLE_COMPOSITION = "singleComposition";
	public static final String MULTI_ATTRIBUTE = "multiAttribute";
	public static final String MULTI_REFERENCE = "multiReference";
	public static final String MULTI_COMPOSITION = "multiComposition";
	public static final String SINGLE_BOOLEAN_ATTRIBUTE = "singleBooleanAttribute";

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute
	 */
	// protected static Object[] createEObjectWithSingleStringAttribute(EPackage
	// ePackage, EClassifier type) {
	// return createEObjectWithSingleStringAttribute(ePackage, CLASS1, type);
	// }

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute
	 */
	protected static Object[] createEObjectWithSingleStringAttribute(EPackage ePackage, String name, EClassifier type) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addSingleStringAttribute(eClass1);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute
	 */
	protected static Object[] createEObjectWithSingleStringAttribute(EPackage ePackage, String name, EClassifier type, String resourceURI) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addSingleStringAttribute(eClass1);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass, resourceURI);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * Add {@link EObject} in Resource.
	 * 
	 * @param eObject
	 *            EObject
	 */
	protected static void putInResource(EObject eObject) {
		putInResource(eObject, "http://testEEF");
	}

	/**
	 * Add {@link EObject} in Resource.
	 * 
	 * @param eObject
	 *            EObject
	 */
	protected static void putInResource(EObject eObject, String resourceURI) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(URI.createURI(resourceURI));
		resource.getContents().add(eObject);
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute
	 */
	protected static Object[] createEObjectWithSingleStringAttributeComment(EPackage ePackage, String name, EClassifier type) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addSingleStringAttribute(eClass1);
		singleStringAttribute.setName(SINGLE_STRING_ATTRIBUTE_COMMENT);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with enum
	 */
	protected static Object[] createEObjectWithSingleEnum(EPackage ePackage, String name) {
		EClass eClass1 = createEClass(ePackage, name);
		EEnum enum1 = createEEnum(ePackage, ENUM1);
		EAttribute singleStringAttribute = addSingleStringAttribute(eClass1);
		singleStringAttribute.setName(SINGLE_ENUM);
		singleStringAttribute.setEType(enum1);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute Edate
	 */
	protected static Object[] createEObjectWithSingleDate(EPackage ePackage, String name, EClassifier type) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addSingleStringAttribute(eClass1);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with ereference 0..1
	 */
	protected static Object[] createEObjectWithSingleReference(EPackage ePackage, String name, String name2) {
		EClass eClass1 = createEClass(ePackage, name);
		EClass eClass2 = createEClass(ePackage, name2);
		EReference eReference = addSingleReference(eClass1);
		eReference.setEType(eClass2);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, eReference };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with ereference 0..*
	 */
	protected static Object[] createEObjectWithMultiReference(EPackage ePackage, String name, String name2) {
		EClass eClass1 = createEClass(ePackage, name);
		EClass eClass2 = createEClass(ePackage, name2);
		EReference eReference = addMultiReference(eClass1);
		eReference.setEType(eClass2);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, eReference };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with composition 0..1
	 */
	protected static Object[] createEObjectWithSingleComposition(EPackage ePackage, String name, String name2) {
		EClass eClass1 = createEClass(ePackage, name);
		EClass eClass2 = createEClass(ePackage, name2);
		EReference eReference = addSingleComposition(eClass1);
		eReference.setEType(eClass2);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, eReference };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with composition 0..*
	 */
	protected static Object[] createEObjectWithMultiComposition(EPackage ePackage, String name, String name2) {
		EClass eClass1 = createEClass(ePackage, name);
		EClass eClass2 = createEClass(ePackage, name2);
		EReference eReference = addMultiComposition(eClass1);
		eReference.setEType(eClass2);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, eReference };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute 0..*
	 */
	protected static Object[] createEObjectWithMultiAttribute(EPackage ePackage, EClassifier type, String name) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addMultiAttribute(eClass1);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param ePackage
	 * @param type
	 * @return EClass with eAttribute
	 */
	protected static Object[] createEObjectWithSingleBooleanAttribute(EPackage ePackage, String name, EClassifier type) {
		EClass eClass1 = createEClass(ePackage, name);
		EAttribute singleStringAttribute = addSingleBooleanAttribute(eClass1);
		singleStringAttribute.setEType(type);
		EObject createdClass = EcoreUtil.create(eClass1);
		putInResource(createdClass);
		return new Object[] { createdClass, eClass1, singleStringAttribute };
	}

	/**
	 * @param eClass
	 * @return add ereference to eclass
	 */
	protected static EReference addSingleReference(EClass eClass) {
		EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setName(SINGLE_REFERENCE);
		eClass.getEStructuralFeatures().add(eReference);
		return eReference;
	}

	/**
	 * @param eClass
	 * @return add ereference to eclass
	 */
	protected static EReference addMultiReference(EClass eClass) {
		EReference eReference = EcoreFactory.eINSTANCE.createEReference();
		eReference.setName(MULTI_REFERENCE);
		eReference.setUpperBound(-1);
		eClass.getEStructuralFeatures().add(eReference);
		return eReference;
	}

	/**
	 * @param eClass
	 * @return add ereference to eclass
	 */
	protected static EReference addSingleComposition(EClass eClass) {
		EReference eReference = addSingleReference(eClass);
		eReference.setName(SINGLE_COMPOSITION);
		eReference.setContainment(true);
		return eReference;
	}

	/**
	 * @param eClass
	 * @return add ereference to eclass
	 */
	protected static EReference addMultiComposition(EClass eClass) {
		EReference eReference = addMultiReference(eClass);
		eReference.setName(MULTI_COMPOSITION);
		eReference.setContainment(true);
		return eReference;
	}

	/**
	 * @param eClass
	 * @return add eattribute to eclass
	 */
	protected static EAttribute addSingleStringAttribute(EClass eClass) {
		EAttribute singleStringAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		singleStringAttribute.setName(SINGLE_STRING_ATTRIBUTE);
		eClass.getEStructuralFeatures().add(singleStringAttribute);
		return singleStringAttribute;
	}

	/**
	 * @param eClass
	 * @return add eattribute to eclass
	 */
	protected static EAttribute addMultiAttribute(EClass eClass) {
		EAttribute singleStringAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		singleStringAttribute.setName(MULTI_ATTRIBUTE);
		singleStringAttribute.setUpperBound(-1);
		eClass.getEStructuralFeatures().add(singleStringAttribute);
		return singleStringAttribute;
	}

	/**
	 * @param eClass
	 * @return add eattribute to eclass
	 */
	protected static EAttribute addSingleBooleanAttribute(EClass eClass) {
		EAttribute singleStringAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		singleStringAttribute.setName(SINGLE_BOOLEAN_ATTRIBUTE);
		eClass.getEStructuralFeatures().add(singleStringAttribute);
		return singleStringAttribute;
	}

	/**
	 * @param ePackage
	 * @param name
	 * @return new Eclass
	 */
	protected static EClass createEClass(EPackage ePackage, String name) {
		EClass eClass1 = EcoreFactory.eINSTANCE.createEClass();
		eClass1.setName(name);
		ePackage.getEClassifiers().add(eClass1);
		return eClass1;
	}

	/**
	 * @param ePackage
	 * @param name
	 * @return new EEnum
	 */
	protected static EEnum createEEnum(EPackage ePackage, String name) {
		EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
		eEnum.setName(name);
		ePackage.getEClassifiers().add(eEnum);
		return eEnum;
	}

}
