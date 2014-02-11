/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.samples.conference.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.samples.conference.Conference;
import org.eclipse.emf.samples.conference.ConferencePackage;
import org.eclipse.emf.samples.conference.GENDER;
import org.eclipse.emf.samples.conference.Participant;
import org.eclipse.emf.samples.conference.Session;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Participant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getFirstname <em>Firstname</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getLastname <em>Lastname</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getAttending <em>Attending</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getGender <em>Gender</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#isIsRegistered <em>Is Registered</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getJobTitle <em>Job Title</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#getBio <em>Bio</em>}</li>
 *   <li>{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl#isSpeaker <em>Speaker</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParticipantImpl extends EObjectImpl implements Participant {
	/**
	 * The default value of the '{@link #getFirstname() <em>Firstname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstname()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRSTNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstname() <em>Firstname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstname()
	 * @generated
	 * @ordered
	 */
	protected String firstname = FIRSTNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastname() <em>Lastname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastname()
	 * @generated
	 * @ordered
	 */
	protected static final String LASTNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastname() <em>Lastname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastname()
	 * @generated
	 * @ordered
	 */
	protected String lastname = LASTNAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAttending() <em>Attending</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttending()
	 * @generated
	 * @ordered
	 */
	protected EList<Session> attending;

	/**
	 * The default value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected static final GENDER GENDER_EDEFAULT = GENDER.MALE;

	/**
	 * The cached value of the '{@link #getGender() <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGender()
	 * @generated
	 * @ordered
	 */
	protected GENDER gender = GENDER_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsRegistered() <em>Is Registered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsRegistered()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REGISTERED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsRegistered() <em>Is Registered</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsRegistered()
	 * @generated
	 * @ordered
	 */
	protected boolean isRegistered = IS_REGISTERED_EDEFAULT;

	/**
	 * The default value of the '{@link #getJobTitle() <em>Job Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJobTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String JOB_TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getJobTitle() <em>Job Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJobTitle()
	 * @generated
	 * @ordered
	 */
	protected String jobTitle = JOB_TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getBio() <em>Bio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBio()
	 * @generated
	 * @ordered
	 */
	protected static final String BIO_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBio() <em>Bio</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBio()
	 * @generated
	 * @ordered
	 */
	protected String bio = BIO_EDEFAULT;

	/**
	 * The default value of the '{@link #isSpeaker() <em>Speaker</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSpeaker()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SPEAKER_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParticipantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	protected EClass eStaticClass() {
		return ConferencePackage.Literals.PARTICIPANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstname(String newFirstname) {
		String oldFirstname = firstname;
		firstname = newFirstname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__FIRSTNAME, oldFirstname, firstname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastname(String newLastname) {
		String oldLastname = lastname;
		lastname = newLastname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__LASTNAME, oldLastname, lastname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Session> getAttending() {
		if (attending == null) {
			attending = new EObjectResolvingEList<Session>(Session.class, this, ConferencePackage.PARTICIPANT__ATTENDING);
		}
		return attending;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GENDER getGender() {
		return gender;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGender(GENDER newGender) {
		GENDER oldGender = gender;
		gender = newGender == null ? GENDER_EDEFAULT : newGender;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__GENDER, oldGender, gender));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsRegistered() {
		return isRegistered;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsRegistered(boolean newIsRegistered) {
		boolean oldIsRegistered = isRegistered;
		isRegistered = newIsRegistered;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__IS_REGISTERED, oldIsRegistered, isRegistered));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJobTitle(String newJobTitle) {
		String oldJobTitle = jobTitle;
		jobTitle = newJobTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__JOB_TITLE, oldJobTitle, jobTitle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBio(String newBio) {
		String oldBio = bio;
		bio = newBio;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConferencePackage.PARTICIPANT__BIO, oldBio, bio));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isSpeaker() {
		if (eContainer() instanceof Conference) {
			for (Session session : ((Conference) eContainer()).getSessions()) {
				if (session.getSpeakers() != null && session.getSpeakers().contains(this)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConferencePackage.PARTICIPANT__FIRSTNAME:
				return getFirstname();
			case ConferencePackage.PARTICIPANT__LASTNAME:
				return getLastname();
			case ConferencePackage.PARTICIPANT__ATTENDING:
				return getAttending();
			case ConferencePackage.PARTICIPANT__GENDER:
				return getGender();
			case ConferencePackage.PARTICIPANT__IS_REGISTERED:
				return isIsRegistered();
			case ConferencePackage.PARTICIPANT__JOB_TITLE:
				return getJobTitle();
			case ConferencePackage.PARTICIPANT__BIO:
				return getBio();
			case ConferencePackage.PARTICIPANT__SPEAKER:
				return isSpeaker();
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
			case ConferencePackage.PARTICIPANT__FIRSTNAME:
				setFirstname((String)newValue);
				return;
			case ConferencePackage.PARTICIPANT__LASTNAME:
				setLastname((String)newValue);
				return;
			case ConferencePackage.PARTICIPANT__ATTENDING:
				getAttending().clear();
				getAttending().addAll((Collection<? extends Session>)newValue);
				return;
			case ConferencePackage.PARTICIPANT__GENDER:
				setGender((GENDER)newValue);
				return;
			case ConferencePackage.PARTICIPANT__IS_REGISTERED:
				setIsRegistered((Boolean)newValue);
				return;
			case ConferencePackage.PARTICIPANT__JOB_TITLE:
				setJobTitle((String)newValue);
				return;
			case ConferencePackage.PARTICIPANT__BIO:
				setBio((String)newValue);
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
			case ConferencePackage.PARTICIPANT__FIRSTNAME:
				setFirstname(FIRSTNAME_EDEFAULT);
				return;
			case ConferencePackage.PARTICIPANT__LASTNAME:
				setLastname(LASTNAME_EDEFAULT);
				return;
			case ConferencePackage.PARTICIPANT__ATTENDING:
				getAttending().clear();
				return;
			case ConferencePackage.PARTICIPANT__GENDER:
				setGender(GENDER_EDEFAULT);
				return;
			case ConferencePackage.PARTICIPANT__IS_REGISTERED:
				setIsRegistered(IS_REGISTERED_EDEFAULT);
				return;
			case ConferencePackage.PARTICIPANT__JOB_TITLE:
				setJobTitle(JOB_TITLE_EDEFAULT);
				return;
			case ConferencePackage.PARTICIPANT__BIO:
				setBio(BIO_EDEFAULT);
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
			case ConferencePackage.PARTICIPANT__FIRSTNAME:
				return FIRSTNAME_EDEFAULT == null ? firstname != null : !FIRSTNAME_EDEFAULT.equals(firstname);
			case ConferencePackage.PARTICIPANT__LASTNAME:
				return LASTNAME_EDEFAULT == null ? lastname != null : !LASTNAME_EDEFAULT.equals(lastname);
			case ConferencePackage.PARTICIPANT__ATTENDING:
				return attending != null && !attending.isEmpty();
			case ConferencePackage.PARTICIPANT__GENDER:
				return gender != GENDER_EDEFAULT;
			case ConferencePackage.PARTICIPANT__IS_REGISTERED:
				return isRegistered != IS_REGISTERED_EDEFAULT;
			case ConferencePackage.PARTICIPANT__JOB_TITLE:
				return JOB_TITLE_EDEFAULT == null ? jobTitle != null : !JOB_TITLE_EDEFAULT.equals(jobTitle);
			case ConferencePackage.PARTICIPANT__BIO:
				return BIO_EDEFAULT == null ? bio != null : !BIO_EDEFAULT.equals(bio);
			case ConferencePackage.PARTICIPANT__SPEAKER:
				return isSpeaker() != SPEAKER_EDEFAULT;
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
		result.append(" (firstname: ");
		result.append(firstname);
		result.append(", lastname: ");
		result.append(lastname);
		result.append(", gender: ");
		result.append(gender);
		result.append(", isRegistered: ");
		result.append(isRegistered);
		result.append(", jobTitle: ");
		result.append(jobTitle);
		result.append(", bio: ");
		result.append(bio);
		result.append(')');
		return result.toString();
	}

} //ParticipantImpl
