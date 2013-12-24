/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.query;

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
 * @see org.eclipse.emf.eef.runtime.query.QueryFactory
 * @model kind="package"
 * @generated
 */
public interface QueryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "query";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/eef/query/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "eef-query";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	QueryPackage eINSTANCE = org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.query.impl.QueryImpl <em>Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryImpl
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 0;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY__BODY = 0;

	/**
	 * The number of structural features of the '<em>Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.query.impl.BodyImpl <em>Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.query.impl.BodyImpl
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getBody()
	 * @generated
	 */
	int BODY = 1;

	/**
	 * The number of structural features of the '<em>Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BODY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl <em>Java Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getJavaBody()
	 * @generated
	 */
	int JAVA_BODY = 2;

	/**
	 * The feature id for the '<em><b>Qualified Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_BODY__QUALIFIED_CLASS = BODY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_BODY__METHOD = BODY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_BODY__STATIC = BODY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Java Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_BODY_FEATURE_COUNT = BODY_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.query.impl.FilterImpl <em>Filter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.query.impl.FilterImpl
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getFilter()
	 * @generated
	 */
	int FILTER = 3;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER__BODY = QUERY__BODY;

	/**
	 * The number of structural features of the '<em>Filter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILTER_FEATURE_COUNT = QUERY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.eef.runtime.query.impl.NavigationImpl <em>Navigation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.eef.runtime.query.impl.NavigationImpl
	 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getNavigation()
	 * @generated
	 */
	int NAVIGATION = 4;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAVIGATION__BODY = QUERY__BODY;

	/**
	 * The number of structural features of the '<em>Navigation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAVIGATION_FEATURE_COUNT = QUERY_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.query.Query <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.eef.runtime.query.Query#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.Query#getBody()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.query.Body <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Body</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.Body
	 * @generated
	 */
	EClass getBody();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.query.JavaBody <em>Java Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Body</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.JavaBody
	 * @generated
	 */
	EClass getJavaBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.query.JavaBody#getQualifiedClass <em>Qualified Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qualified Class</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.JavaBody#getQualifiedClass()
	 * @see #getJavaBody()
	 * @generated
	 */
	EAttribute getJavaBody_QualifiedClass();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.query.JavaBody#getMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.JavaBody#getMethod()
	 * @see #getJavaBody()
	 * @generated
	 */
	EAttribute getJavaBody_Method();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.eef.runtime.query.JavaBody#isStatic <em>Static</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Static</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.JavaBody#isStatic()
	 * @see #getJavaBody()
	 * @generated
	 */
	EAttribute getJavaBody_Static();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.query.Filter <em>Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Filter</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.Filter
	 * @generated
	 */
	EClass getFilter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.eef.runtime.query.Navigation <em>Navigation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Navigation</em>'.
	 * @see org.eclipse.emf.eef.runtime.query.Navigation
	 * @generated
	 */
	EClass getNavigation();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	QueryFactory getQueryFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.query.impl.QueryImpl <em>Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryImpl
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getQuery()
		 * @generated
		 */
		EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUERY__BODY = eINSTANCE.getQuery_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.query.impl.BodyImpl <em>Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.query.impl.BodyImpl
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getBody()
		 * @generated
		 */
		EClass BODY = eINSTANCE.getBody();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl <em>Java Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.query.impl.JavaBodyImpl
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getJavaBody()
		 * @generated
		 */
		EClass JAVA_BODY = eINSTANCE.getJavaBody();

		/**
		 * The meta object literal for the '<em><b>Qualified Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_BODY__QUALIFIED_CLASS = eINSTANCE.getJavaBody_QualifiedClass();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_BODY__METHOD = eINSTANCE.getJavaBody_Method();

		/**
		 * The meta object literal for the '<em><b>Static</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_BODY__STATIC = eINSTANCE.getJavaBody_Static();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.query.impl.FilterImpl <em>Filter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.query.impl.FilterImpl
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getFilter()
		 * @generated
		 */
		EClass FILTER = eINSTANCE.getFilter();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.eef.runtime.query.impl.NavigationImpl <em>Navigation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.eef.runtime.query.impl.NavigationImpl
		 * @see org.eclipse.emf.eef.runtime.query.impl.QueryPackageImpl#getNavigation()
		 * @generated
		 */
		EClass NAVIGATION = eINSTANCE.getNavigation();

	}

} //QueryPackage
