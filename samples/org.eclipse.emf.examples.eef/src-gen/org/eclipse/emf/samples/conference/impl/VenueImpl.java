/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.samples.conference.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.samples.conference.ConferencePackage;
import org.eclipse.emf.samples.conference.Room;
import org.eclipse.emf.samples.conference.Venue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Venue</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.VenueImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.VenueImpl#getRooms <em>Rooms</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.VenueImpl#getGetToTheVenue <em>Get To The Venue</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VenueImpl extends EObjectImpl implements Venue {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRooms() <em>Rooms</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRooms()
	 * @generated
	 * @ordered
	 */
	protected EList<Room> rooms;

	/**
	 * The default value of the '{@link #getGetToTheVenue() <em>Get To The Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetToTheVenue()
	 * @generated
	 * @ordered
	 */
	protected static final String GET_TO_THE_VENUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGetToTheVenue() <em>Get To The Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetToTheVenue()
	 * @generated
	 * @ordered
	 */
	protected String getToTheVenue = GET_TO_THE_VENUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VenueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConferencePackage.Literals.VENUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.VENUE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Room> getRooms() {
		if (rooms == null) {
			rooms = new EObjectContainmentEList<Room>(Room.class, this, ConferencePackage.VENUE__ROOMS);
		}
		return rooms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGetToTheVenue() {
		return getToTheVenue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGetToTheVenue(String newGetToTheVenue) {
		String oldGetToTheVenue = getToTheVenue;
		getToTheVenue = newGetToTheVenue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.VENUE__GET_TO_THE_VENUE, oldGetToTheVenue, getToTheVenue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConferencePackage.VENUE__ROOMS:
				return ((InternalEList<?>)getRooms()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConferencePackage.VENUE__NAME:
				return getName();
			case ConferencePackage.VENUE__ROOMS:
				return getRooms();
			case ConferencePackage.VENUE__GET_TO_THE_VENUE:
				return getGetToTheVenue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConferencePackage.VENUE__NAME:
				setName((String)newValue);
				return;
			case ConferencePackage.VENUE__ROOMS:
				getRooms().clear();
				getRooms().addAll((Collection<? extends Room>)newValue);
				return;
			case ConferencePackage.VENUE__GET_TO_THE_VENUE:
				setGetToTheVenue((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConferencePackage.VENUE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConferencePackage.VENUE__ROOMS:
				getRooms().clear();
				return;
			case ConferencePackage.VENUE__GET_TO_THE_VENUE:
				setGetToTheVenue(GET_TO_THE_VENUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConferencePackage.VENUE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConferencePackage.VENUE__ROOMS:
				return rooms != null && !rooms.isEmpty();
			case ConferencePackage.VENUE__GET_TO_THE_VENUE:
				return GET_TO_THE_VENUE_EDEFAULT == null ? getToTheVenue != null : !GET_TO_THE_VENUE_EDEFAULT.equals(getToTheVenue);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", getToTheVenue: ");
		result.append(getToTheVenue);
		result.append(')');
		return result.toString();
	}

} //VenueImpl
