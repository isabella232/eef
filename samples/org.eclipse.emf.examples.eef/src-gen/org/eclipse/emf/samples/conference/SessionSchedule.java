/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.samples.conference;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Session Schedule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.SessionSchedule#getSession <em>Session</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.SessionSchedule#getRoom <em>Room</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.SessionSchedule#getDate <em>Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSessionSchedule()
 * @model
 * @generated
 */
public interface SessionSchedule extends EObject {
	/**
	 * Returns the value of the '<em><b>Session</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Session</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session</em>' reference.
	 * @see #setSession(Session)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSessionSchedule_Session()
	 * @model required="true"
	 * @generated
	 */
	Session getSession();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.SessionSchedule#getSession <em>Session</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session</em>' reference.
	 * @see #getSession()
	 * @generated
	 */
	void setSession(Session value);

	/**
	 * Returns the value of the '<em><b>Room</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Room</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Room</em>' reference.
	 * @see #setRoom(Room)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSessionSchedule_Room()
	 * @model required="true"
	 * @generated
	 */
	Room getRoom();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.SessionSchedule#getRoom <em>Room</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Room</em>' reference.
	 * @see #getRoom()
	 * @generated
	 */
	void setRoom(Room value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getSessionSchedule_Date()
	 * @model required="true"
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.SessionSchedule#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

} // SessionSchedule
