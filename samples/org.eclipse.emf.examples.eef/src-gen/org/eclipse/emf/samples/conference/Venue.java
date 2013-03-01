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
 * A representation of the model object '<em><b>Venue</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.Venue#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Venue#getRooms <em>Rooms</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.Venue#getGetToTheVenue <em>Get To The Venue</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.samples.conference.ConferencePackage#getVenue()
 * @model
 * @generated
 */
public interface Venue extends EObject {
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
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getVenue_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Venue#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Rooms</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.samples.conference.Room}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rooms</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rooms</em>' containment reference list.
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getVenue_Rooms()
	 * @model containment="true"
	 * @generated
	 */
	EList<Room> getRooms();

	/**
	 * Returns the value of the '<em><b>Get To The Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Get To The Venue</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Get To The Venue</em>' attribute.
	 * @see #setGetToTheVenue(String)
	 * @see org.eclipse.emf.samples.conference.ConferencePackage#getVenue_GetToTheVenue()
	 * @model
	 * @generated
	 */
	String getGetToTheVenue();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.samples.conference.Venue#getGetToTheVenue <em>Get To The Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Get To The Venue</em>' attribute.
	 * @see #getGetToTheVenue()
	 * @generated
	 */
	void setGetToTheVenue(String value);

} // Venue
