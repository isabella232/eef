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
 * A representation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getFirstname <em>Firstname</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getLastname <em>Lastname</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getAttending <em>Attending</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getGender <em>Gender</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#isIsRegistered <em>Is Registered</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getJobTitle <em>Job Title</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#getBio <em>Bio</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Participant#isSpeaker <em>Speaker</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant()
 * @model
 * @generated
 */
public interface Participant extends EObject {
	/**
	 * Returns the value of the '<em><b>Firstname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is the firstname of the person
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Firstname</em>' attribute.
	 * @see #setFirstname(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Firstname()
	 * @model required="true"
	 * @generated
	 */
	String getFirstname();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#getFirstname <em>Firstname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Firstname</em>' attribute.
	 * @see #getFirstname()
	 * @generated
	 */
	void setFirstname(String value);

	/**
	 * Returns the value of the '<em><b>Lastname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lastname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lastname</em>' attribute.
	 * @see #setLastname(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Lastname()
	 * @model
	 * @generated
	 */
	String getLastname();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#getLastname <em>Lastname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lastname</em>' attribute.
	 * @see #getLastname()
	 * @generated
	 */
	void setLastname(String value);

	/**
	 * Returns the value of the '<em><b>Attending</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Session}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attending</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attending</em>' reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Attending()
	 * @model
	 * @generated
	 */
	EList<Session> getAttending();

	/**
	 * Returns the value of the '<em><b>Gender</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.samples.conference.GENDER}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gender</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.GENDER
	 * @see #setGender(GENDER)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Gender()
	 * @model
	 * @generated
	 */
	GENDER getGender();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#getGender <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.GENDER
	 * @see #getGender()
	 * @generated
	 */
	void setGender(GENDER value);

	/**
	 * Returns the value of the '<em><b>Is Registered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Registered</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Registered</em>' attribute.
	 * @see #setIsRegistered(boolean)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_IsRegistered()
	 * @model
	 * @generated
	 */
	boolean isIsRegistered();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#isIsRegistered <em>Is Registered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Registered</em>' attribute.
	 * @see #isIsRegistered()
	 * @generated
	 */
	void setIsRegistered(boolean value);

	/**
	 * Returns the value of the '<em><b>Job Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Job Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Job Title</em>' attribute.
	 * @see #setJobTitle(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_JobTitle()
	 * @model
	 * @generated
	 */
	String getJobTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#getJobTitle <em>Job Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Job Title</em>' attribute.
	 * @see #getJobTitle()
	 * @generated
	 */
	void setJobTitle(String value);

	/**
	 * Returns the value of the '<em><b>Bio</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bio</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bio</em>' attribute.
	 * @see #setBio(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Bio()
	 * @model required="true"
	 * @generated
	 */
	String getBio();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Participant#getBio <em>Bio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bio</em>' attribute.
	 * @see #getBio()
	 * @generated
	 */
	void setBio(String value);

	/**
	 * Returns the value of the '<em><b>Speaker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Speaker</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Speaker</em>' attribute.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getParticipant_Speaker()
	 * @model required="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isSpeaker();

} // Participant
