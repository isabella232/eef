/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.eeftests.bindingmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.eef.eeftests.bindingmodel.BindingmodelFactory
 * @model kind="package"
 * @generated
 */
public interface BindingmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bindingmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/eef/tests/bindingModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eeftests-bindingmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BindingmodelPackage eINSTANCE = org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.RootImpl <em>Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.RootImpl
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getRoot()
	 * @generated
	 */
	int ROOT = 0;

	/**
	 * The feature id for the '<em><b>Samples</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT__SAMPLES = 0;

	/**
	 * The number of structural features of the '<em>Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.AbstractSampleImpl <em>Abstract Sample</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.AbstractSampleImpl
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getAbstractSample()
	 * @generated
	 */
	int ABSTRACT_SAMPLE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SAMPLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Abstract Sample</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SAMPLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.SampleImpl <em>Sample</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.SampleImpl
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getSample()
	 * @generated
	 */
	int SAMPLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE__NAME = ABSTRACT_SAMPLE__NAME;

	/**
	 * The feature id for the '<em><b>Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE__ACTIVE = ABSTRACT_SAMPLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sample</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAMPLE_FEATURE_COUNT = ABSTRACT_SAMPLE_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.eeftests.bindingmodel.Root <em>Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.Root
	 * @generated
	 */
	EClass getRoot();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.eef.eeftests.bindingmodel.Root#getSamples <em>Samples</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Samples</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.Root#getSamples()
	 * @see #getRoot()
	 * @generated
	 */
	EReference getRoot_Samples();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample <em>Abstract Sample</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Sample</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample
	 * @generated
	 */
	EClass getAbstractSample();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.AbstractSample#getName()
	 * @see #getAbstractSample()
	 * @generated
	 */
	EAttribute getAbstractSample_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.eeftests.bindingmodel.Sample <em>Sample</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sample</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.Sample
	 * @generated
	 */
	EClass getSample();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.eeftests.bindingmodel.Sample#isActive <em>Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Active</em>'.
	 * @see org.eclipse.emf.eef.eeftests.bindingmodel.Sample#isActive()
	 * @see #getSample()
	 * @generated
	 */
	EAttribute getSample_Active();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BindingmodelFactory getBindingmodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.RootImpl <em>Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.RootImpl
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getRoot()
		 * @generated
		 */
		EClass ROOT = eINSTANCE.getRoot();

		/**
		 * The meta object literal for the '<em><b>Samples</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT__SAMPLES = eINSTANCE.getRoot_Samples();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.AbstractSampleImpl <em>Abstract Sample</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.AbstractSampleImpl
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getAbstractSample()
		 * @generated
		 */
		EClass ABSTRACT_SAMPLE = eINSTANCE.getAbstractSample();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_SAMPLE__NAME = eINSTANCE.getAbstractSample_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.eeftests.bindingmodel.impl.SampleImpl <em>Sample</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.SampleImpl
		 * @see org.eclipse.emf.eef.eeftests.bindingmodel.impl.BindingmodelPackageImpl#getSample()
		 * @generated
		 */
		EClass SAMPLE = eINSTANCE.getSample();

		/**
		 * The meta object literal for the '<em><b>Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SAMPLE__ACTIVE = eINSTANCE.getSample_Active();

	}

} //BindingmodelPackage
