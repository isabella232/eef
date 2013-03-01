/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.samples.conference;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Session</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.Session#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Session#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Session#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Session#getPresenters <em>Presenters</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Session#getDocumentation <em>Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession()
 * @model
 * @generated
 */
public interface Session extends EObject {
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
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession_Title()
	 * @model required="true"
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Session#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Returns the value of the '<em><b>Keywords</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Keyword}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Keywords</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Keywords</em>' containment reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession_Keywords()
	 * @model containment="true"
	 * @generated
	 */
	EList<Keyword> getKeywords();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.samples.conference.SessionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.SessionType
	 * @see #setType(SessionType)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession_Type()
	 * @model
	 * @generated
	 */
	SessionType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Session#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.SessionType
	 * @see #getType()
	 * @generated
	 */
	void setType(SessionType value);

	/**
	 * Returns the value of the '<em><b>Presenters</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Participant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Presenters</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Presenters</em>' reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession_Presenters()
	 * @model required="true"
	 *        annotation="genConstraint significant='true'"
	 * @generated
	 */
	EList<Participant> getPresenters();

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
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSession_Documentation()
	 * @model required="true"
	 * @generated
	 */
	String getDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Session#getDocumentation <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Documentation</em>' attribute.
	 * @see #getDocumentation()
	 * @generated
	 */
	void setDocumentation(String value);

} // Session
