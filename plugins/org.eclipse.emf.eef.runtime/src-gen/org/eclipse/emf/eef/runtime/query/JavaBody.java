/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.query;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Body</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.JavaBody#getBundle <em>Bundle</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.JavaBody#getQualifiedClass <em>Qualified Class</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.JavaBody#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.emf.eef.runtime.query.JavaBody#isStatic <em>Static</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.eef.runtime.query.QueryPackage#getJavaBody()
 * @model
 * @generated
 */
public interface JavaBody<TYPE> extends Body<TYPE> {
	/**
	 * Returns the value of the '<em><b>Bundle</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bundle</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bundle</em>' attribute.
	 * @see #setBundle(String)
	 * @see org.eclipse.emf.eef.runtime.query.QueryPackage#getJavaBody_Bundle()
	 * @model
	 * @generated
	 */
	String getBundle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.query.JavaBody#getBundle <em>Bundle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bundle</em>' attribute.
	 * @see #getBundle()
	 * @generated
	 */
	void setBundle(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qualified Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qualified Class</em>' attribute.
	 * @see #setQualifiedClass(String)
	 * @see org.eclipse.emf.eef.runtime.query.QueryPackage#getJavaBody_QualifiedClass()
	 * @model required="true"
	 * @generated
	 */
	String getQualifiedClass();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.query.JavaBody#getQualifiedClass <em>Qualified Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qualified Class</em>' attribute.
	 * @see #getQualifiedClass()
	 * @generated
	 */
	void setQualifiedClass(String value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see org.eclipse.emf.eef.runtime.query.QueryPackage#getJavaBody_Method()
	 * @model required="true"
	 * @generated
	 */
	String getMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.query.JavaBody#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(String value);

	/**
	 * Returns the value of the '<em><b>Static</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Static</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Static</em>' attribute.
	 * @see #setStatic(boolean)
	 * @see org.eclipse.emf.eef.runtime.query.QueryPackage#getJavaBody_Static()
	 * @model
	 * @generated
	 */
	boolean isStatic();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.eef.runtime.query.JavaBody#isStatic <em>Static</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Static</em>' attribute.
	 * @see #isStatic()
	 * @generated
	 */
	void setStatic(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true" loaderDataType="org.eclipse.emf.eef.runtime.query.ClassLoader" loaderRequired="true" targetRequired="true" parametersMany="true"
	 * @generated
	 */
	TYPE invoke(ClassLoader loader, Object target, EList<Object> parameters);

} // JavaBody
