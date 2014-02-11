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
 * A representation of the model object '<em><b>Conference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getParticipants <em>Participants</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getSessions <em>Sessions</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getVenues <em>Venues</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getOverview <em>Overview</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Conference#getSchedule <em>Schedule</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference()
 * @model
 * @generated
 */
public interface Conference extends EObject {
	/**
	 * Returns the value of the '<em><b>Participants</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Participant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Participants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Participants</em>' containment reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Participants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Participant> getParticipants();

	/**
	 * Returns the value of the '<em><b>Sessions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Session}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sessions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sessions</em>' containment reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Sessions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Session> getSessions();

	/**
	 * Returns the value of the '<em><b>Venues</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Venue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Venues</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Venues</em>' containment reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Venues()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Venue> getVenues();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Conference#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Overview</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overview</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overview</em>' attribute.
	 * @see #setOverview(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Overview()
	 * @model
	 * @generated
	 */
	String getOverview();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Conference#getOverview <em>Overview</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overview</em>' attribute.
	 * @see #getOverview()
	 * @generated
	 */
	void setOverview(String value);

	/**
	 * Returns the value of the '<em><b>Schedule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule</em>' containment reference.
	 * @see #setSchedule(Schedule)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getConference_Schedule()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Schedule getSchedule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Conference#getSchedule <em>Schedule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule</em>' containment reference.
	 * @see #getSchedule()
	 * @generated
	 */
	void setSchedule(Schedule value);

} // Conference
