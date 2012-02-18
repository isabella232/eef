/**
 * <copyright>
 * </copyright>
 *
 * $Id: Talk.java,v 1.3 2011/01/05 15:06:12 glefur Exp $
 */
package org.eclipse.emf.samples.conference;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Talk</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getTopic <em>Topic</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getPresenter <em>Presenter</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getCreator <em>Creator</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Talk#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk()
 * @model
 * @generated
 */
public interface Talk extends EObject {
	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Title()
	 * @model required="true"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Topic</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topic</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topic</em>' reference.
	 * @see #setTopic(Topic)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Topic()
	 * @model required="true"
	 * @generated
	 */
	Topic getTopic();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getTopic <em>Topic</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Topic</em>' reference.
	 * @see #getTopic()
	 * @generated
	 */
	void setTopic(Topic value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.samples.conference.TALK_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.TALK_TYPE
	 * @see #setType(TALK_TYPE)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Type()
	 * @model
	 * @generated
	 */
	TALK_TYPE getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.TALK_TYPE
	 * @see #getType()
	 * @generated
	 */
	void setType(TALK_TYPE value);

	/**
	 * Returns the value of the '<em><b>Presenter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Presenter</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Presenter</em>' reference.
	 * @see #setPresenter(Person)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Presenter()
	 * @model required="true"
	 *        annotation="genConstraint significant='true'"
	 * @generated
	 */
	Person getPresenter();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getPresenter <em>Presenter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Presenter</em>' reference.
	 * @see #getPresenter()
	 * @generated
	 */
	void setPresenter(Person value);

	/**
	 * Returns the value of the '<em><b>Creator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creator</em>' reference.
	 * @see #setCreator(Person)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Creator()
	 * @model
	 * @generated
	 */
	Person getCreator();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getCreator <em>Creator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creator</em>' reference.
	 * @see #getCreator()
	 * @generated
	 */
	void setCreator(Person value);

	/**
	 * Returns the value of the '<em><b>Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Documentation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Documentation</em>' attribute.
	 * @see #setDocumentation(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getTalk_Documentation()
	 * @model required="true"
	 * @generated
	 */
	String getDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Talk#getDocumentation <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' attribute.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(String value);

} // Talk
