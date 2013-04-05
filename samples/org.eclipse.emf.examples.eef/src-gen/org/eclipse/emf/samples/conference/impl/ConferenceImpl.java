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

import org.eclipse.emf.samples.conference.Conference;
import org.eclipse.emf.samples.conference.ConferencePackage;
import org.eclipse.emf.samples.conference.Participant;
import org.eclipse.emf.samples.conference.Schedule;
import org.eclipse.emf.samples.conference.Session;
import org.eclipse.emf.samples.conference.Venue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Conference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getParticipants <em>Participants</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getSessions <em>Sessions</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getVenues <em>Venues</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getOverview <em>Overview</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl#getSchedule <em>Schedule</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConferenceImpl extends EObjectImpl implements Conference {
	/**
	 * The cached value of the '{@link #getParticipants() <em>Participants</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParticipants()
	 * @generated
	 * @ordered
	 */
	protected EList<Participant> participants;

	/**
	 * The cached value of the '{@link #getSessions() <em>Sessions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessions()
	 * @generated
	 * @ordered
	 */
	protected EList<Session> sessions;

	/**
	 * The cached value of the '{@link #getVenues() <em>Venues</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVenues()
	 * @generated
	 * @ordered
	 */
	protected EList<Venue> venues;

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
	 * The default value of the '{@link #getOverview() <em>Overview</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverview()
	 * @generated
	 * @ordered
	 */
	protected static final String OVERVIEW_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOverview() <em>Overview</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverview()
	 * @generated
	 * @ordered
	 */
	protected String overview = OVERVIEW_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSchedule() <em>Schedule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchedule()
	 * @generated
	 * @ordered
	 */
	protected Schedule schedule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	protected EClass eStaticClass() {
		return ConferencePackage.Literals.CONFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Participant> getParticipants() {
		if (participants == null) {
			participants = new EObjectContainmentEList<Participant>(Participant.class, this, ConferencePackage.CONFERENCE__PARTICIPANTS);
		}
		return participants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Session> getSessions() {
		if (sessions == null) {
			sessions = new EObjectContainmentEList<Session>(Session.class, this, ConferencePackage.CONFERENCE__SESSIONS);
		}
		return sessions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Venue> getVenues() {
		if (venues == null) {
			venues = new EObjectContainmentEList<Venue>(Venue.class, this, ConferencePackage.CONFERENCE__VENUES);
		}
		return venues;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.CONFERENCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOverview() {
		return overview;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverview(String newOverview) {
		String oldOverview = overview;
		overview = newOverview;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.CONFERENCE__OVERVIEW, oldOverview, overview));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSchedule(Schedule newSchedule, NotificationChain msgs) {
		Schedule oldSchedule = schedule;
		schedule = newSchedule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConferencePackage.CONFERENCE__SCHEDULE, oldSchedule, newSchedule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSchedule(Schedule newSchedule) {
		if (newSchedule != schedule) {
			NotificationChain msgs = null;
			if (schedule != null)
				msgs = ((InternalEObject)schedule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConferencePackage.CONFERENCE__SCHEDULE, null, msgs);
			if (newSchedule != null)
				msgs = ((InternalEObject)newSchedule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConferencePackage.CONFERENCE__SCHEDULE, null, msgs);
			msgs = basicSetSchedule(newSchedule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.CONFERENCE__SCHEDULE, newSchedule, newSchedule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ConferencePackage.CONFERENCE__PARTICIPANTS:
				return ((InternalEList<?>)getParticipants()).basicRemove(otherEnd, msgs);
			case ConferencePackage.CONFERENCE__SESSIONS:
				return ((InternalEList<?>)getSessions()).basicRemove(otherEnd, msgs);
			case ConferencePackage.CONFERENCE__VENUES:
				return ((InternalEList<?>)getVenues()).basicRemove(otherEnd, msgs);
			case ConferencePackage.CONFERENCE__SCHEDULE:
				return basicSetSchedule(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConferencePackage.CONFERENCE__PARTICIPANTS:
				return getParticipants();
			case ConferencePackage.CONFERENCE__SESSIONS:
				return getSessions();
			case ConferencePackage.CONFERENCE__VENUES:
				return getVenues();
			case ConferencePackage.CONFERENCE__NAME:
				return getName();
			case ConferencePackage.CONFERENCE__OVERVIEW:
				return getOverview();
			case ConferencePackage.CONFERENCE__SCHEDULE:
				return getSchedule();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ConferencePackage.CONFERENCE__PARTICIPANTS:
				getParticipants().clear();
				getParticipants().addAll((Collection<? extends Participant>)newValue);
				return;
			case ConferencePackage.CONFERENCE__SESSIONS:
				getSessions().clear();
				getSessions().addAll((Collection<? extends Session>)newValue);
				return;
			case ConferencePackage.CONFERENCE__VENUES:
				getVenues().clear();
				getVenues().addAll((Collection<? extends Venue>)newValue);
				return;
			case ConferencePackage.CONFERENCE__NAME:
				setName((String)newValue);
				return;
			case ConferencePackage.CONFERENCE__OVERVIEW:
				setOverview((String)newValue);
				return;
			case ConferencePackage.CONFERENCE__SCHEDULE:
				setSchedule((Schedule)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public void eUnset(int featureID) {
		switch (featureID) {
			case ConferencePackage.CONFERENCE__PARTICIPANTS:
				getParticipants().clear();
				return;
			case ConferencePackage.CONFERENCE__SESSIONS:
				getSessions().clear();
				return;
			case ConferencePackage.CONFERENCE__VENUES:
				getVenues().clear();
				return;
			case ConferencePackage.CONFERENCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ConferencePackage.CONFERENCE__OVERVIEW:
				setOverview(OVERVIEW_EDEFAULT);
				return;
			case ConferencePackage.CONFERENCE__SCHEDULE:
				setSchedule((Schedule)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ConferencePackage.CONFERENCE__PARTICIPANTS:
				return participants != null && !participants.isEmpty();
			case ConferencePackage.CONFERENCE__SESSIONS:
				return sessions != null && !sessions.isEmpty();
			case ConferencePackage.CONFERENCE__VENUES:
				return venues != null && !venues.isEmpty();
			case ConferencePackage.CONFERENCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ConferencePackage.CONFERENCE__OVERVIEW:
				return OVERVIEW_EDEFAULT == null ? overview != null : !OVERVIEW_EDEFAULT.equals(overview);
			case ConferencePackage.CONFERENCE__SCHEDULE:
				return schedule != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", overview: ");
		result.append(overview);
		result.append(')');
		return result.toString();
	}

} //ConferenceImpl
