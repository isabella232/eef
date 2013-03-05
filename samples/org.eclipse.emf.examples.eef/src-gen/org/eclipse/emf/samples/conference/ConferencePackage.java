/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.samples.conference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.samples.conference.ConferenceFactory
 * @model kind="package"
 * @generated
 */
public interface ConferencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "conference";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/samples/eef/2.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "emf-samples-conference";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ConferencePackage eINSTANCE = org.eclipse.emf.samples.conference.impl.ConferencePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl <em>Conference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.ConferenceImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getConference()
	 * @generated
	 */
	int CONFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Participants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__PARTICIPANTS = 0;

	/**
	 * The feature id for the '<em><b>Sessions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__SESSIONS = 1;

	/**
	 * The feature id for the '<em><b>Venues</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__VENUES = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__NAME = 3;

	/**
	 * The feature id for the '<em><b>Overview</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__OVERVIEW = 4;

	/**
	 * The feature id for the '<em><b>Schedule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE__SCHEDULE = 5;

	/**
	 * The number of structural features of the '<em>Conference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFERENCE_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl <em>Participant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.ParticipantImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getParticipant()
	 * @generated
	 */
	int PARTICIPANT = 1;

	/**
	 * The feature id for the '<em><b>Firstname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__FIRSTNAME = 0;

	/**
	 * The feature id for the '<em><b>Lastname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__LASTNAME = 1;

	/**
	 * The feature id for the '<em><b>Attending</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__ATTENDING = 2;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__GENDER = 3;

	/**
	 * The feature id for the '<em><b>Is Registered</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__IS_REGISTERED = 4;

	/**
	 * The feature id for the '<em><b>Job Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__JOB_TITLE = 5;

	/**
	 * The feature id for the '<em><b>Bio</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__BIO = 6;

	/**
	 * The feature id for the '<em><b>Speaker</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT__SPEAKER = 7;

	/**
	 * The number of structural features of the '<em>Participant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARTICIPANT_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.SessionImpl <em>Session</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.SessionImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSession()
	 * @generated
	 */
	int SESSION = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION__TITLE = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Speakers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION__SPEAKERS = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION__KEYWORDS = 4;

	/**
	 * The number of structural features of the '<em>Session</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.VenueImpl <em>Venue</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.VenueImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getVenue()
	 * @generated
	 */
	int VENUE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENUE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Rooms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENUE__ROOMS = 1;

	/**
	 * The feature id for the '<em><b>Get To The Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENUE__GET_TO_THE_VENUE = 2;

	/**
	 * The number of structural features of the '<em>Venue</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENUE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.RoomImpl <em>Room</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.RoomImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getRoom()
	 * @generated
	 */
	int ROOM = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__NAME = 0;

	/**
	 * The feature id for the '<em><b>Capacity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM__CAPACITY = 1;

	/**
	 * The number of structural features of the '<em>Room</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOM_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.ScheduleImpl <em>Schedule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.ScheduleImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSchedule()
	 * @generated
	 */
	int SCHEDULE = 5;

	/**
	 * The feature id for the '<em><b>Sessions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE__SESSIONS = 0;

	/**
	 * The number of structural features of the '<em>Schedule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.impl.SessionScheduleImpl <em>Session Schedule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.impl.SessionScheduleImpl
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSessionSchedule()
	 * @generated
	 */
	int SESSION_SCHEDULE = 6;

	/**
	 * The feature id for the '<em><b>Session</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION_SCHEDULE__SESSION = 0;

	/**
	 * The feature id for the '<em><b>Room</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION_SCHEDULE__ROOM = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION_SCHEDULE__DATE = 2;

	/**
	 * The number of structural features of the '<em>Session Schedule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SESSION_SCHEDULE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.SessionType <em>Session Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.SessionType
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSessionType()
	 * @generated
	 */
	int SESSION_TYPE = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.samples.conference.GENDER <em>GENDER</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.samples.conference.GENDER
	 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getGENDER()
	 * @generated
	 */
	int GENDER = 8;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Conference <em>Conference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Conference</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference
	 * @generated
	 */
	EClass getConference();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.samples.conference.Conference#getParticipants <em>Participants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Participants</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getParticipants()
	 * @see #getConference()
	 * @generated
	 */
	EReference getConference_Participants();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.samples.conference.Conference#getSessions <em>Sessions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sessions</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getSessions()
	 * @see #getConference()
	 * @generated
	 */
	EReference getConference_Sessions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.samples.conference.Conference#getVenues <em>Venues</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Venues</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getVenues()
	 * @see #getConference()
	 * @generated
	 */
	EReference getConference_Venues();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Conference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getName()
	 * @see #getConference()
	 * @generated
	 */
	EAttribute getConference_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Conference#getOverview <em>Overview</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Overview</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getOverview()
	 * @see #getConference()
	 * @generated
	 */
	EAttribute getConference_Overview();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.samples.conference.Conference#getSchedule <em>Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Schedule</em>'.
	 * @see org.eclipse.emf.samples.conference.Conference#getSchedule()
	 * @see #getConference()
	 * @generated
	 */
	EReference getConference_Schedule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Participant <em>Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Participant</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant
	 * @generated
	 */
	EClass getParticipant();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#getFirstname <em>Firstname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Firstname</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getFirstname()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_Firstname();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#getLastname <em>Lastname</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lastname</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getLastname()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_Lastname();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.samples.conference.Participant#getAttending <em>Attending</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Attending</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getAttending()
	 * @see #getParticipant()
	 * @generated
	 */
	EReference getParticipant_Attending();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#getGender <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Gender</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getGender()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_Gender();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#isIsRegistered <em>Is Registered</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Registered</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#isIsRegistered()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_IsRegistered();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#getJobTitle <em>Job Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Job Title</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getJobTitle()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_JobTitle();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#getBio <em>Bio</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bio</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#getBio()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_Bio();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Participant#isSpeaker <em>Speaker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Speaker</em>'.
	 * @see org.eclipse.emf.samples.conference.Participant#isSpeaker()
	 * @see #getParticipant()
	 * @generated
	 */
	EAttribute getParticipant_Speaker();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Session <em>Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Session</em>'.
	 * @see org.eclipse.emf.samples.conference.Session
	 * @generated
	 */
	EClass getSession();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Session#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.emf.samples.conference.Session#getTitle()
	 * @see #getSession()
	 * @generated
	 */
	EAttribute getSession_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Session#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.emf.samples.conference.Session#getType()
	 * @see #getSession()
	 * @generated
	 */
	EAttribute getSession_Type();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.samples.conference.Session#getSpeakers <em>Speakers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Speakers</em>'.
	 * @see org.eclipse.emf.samples.conference.Session#getSpeakers()
	 * @see #getSession()
	 * @generated
	 */
	EReference getSession_Speakers();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Session#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.samples.conference.Session#getDescription()
	 * @see #getSession()
	 * @generated
	 */
	EAttribute getSession_Description();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.samples.conference.Session#getKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Keywords</em>'.
	 * @see org.eclipse.emf.samples.conference.Session#getKeywords()
	 * @see #getSession()
	 * @generated
	 */
	EAttribute getSession_Keywords();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Venue <em>Venue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Venue</em>'.
	 * @see org.eclipse.emf.samples.conference.Venue
	 * @generated
	 */
	EClass getVenue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Venue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.samples.conference.Venue#getName()
	 * @see #getVenue()
	 * @generated
	 */
	EAttribute getVenue_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.samples.conference.Venue#getRooms <em>Rooms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rooms</em>'.
	 * @see org.eclipse.emf.samples.conference.Venue#getRooms()
	 * @see #getVenue()
	 * @generated
	 */
	EReference getVenue_Rooms();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Venue#getGetToTheVenue <em>Get To The Venue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Get To The Venue</em>'.
	 * @see org.eclipse.emf.samples.conference.Venue#getGetToTheVenue()
	 * @see #getVenue()
	 * @generated
	 */
	EAttribute getVenue_GetToTheVenue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Room <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Room</em>'.
	 * @see org.eclipse.emf.samples.conference.Room
	 * @generated
	 */
	EClass getRoom();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Room#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.samples.conference.Room#getName()
	 * @see #getRoom()
	 * @generated
	 */
	EAttribute getRoom_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.Room#getCapacity <em>Capacity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Capacity</em>'.
	 * @see org.eclipse.emf.samples.conference.Room#getCapacity()
	 * @see #getRoom()
	 * @generated
	 */
	EAttribute getRoom_Capacity();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.Schedule <em>Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Schedule</em>'.
	 * @see org.eclipse.emf.samples.conference.Schedule
	 * @generated
	 */
	EClass getSchedule();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.samples.conference.Schedule#getSessions <em>Sessions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sessions</em>'.
	 * @see org.eclipse.emf.samples.conference.Schedule#getSessions()
	 * @see #getSchedule()
	 * @generated
	 */
	EReference getSchedule_Sessions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.samples.conference.SessionSchedule <em>Session Schedule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Session Schedule</em>'.
	 * @see org.eclipse.emf.samples.conference.SessionSchedule
	 * @generated
	 */
	EClass getSessionSchedule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.samples.conference.SessionSchedule#getSession <em>Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Session</em>'.
	 * @see org.eclipse.emf.samples.conference.SessionSchedule#getSession()
	 * @see #getSessionSchedule()
	 * @generated
	 */
	EReference getSessionSchedule_Session();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.samples.conference.SessionSchedule#getRoom <em>Room</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Room</em>'.
	 * @see org.eclipse.emf.samples.conference.SessionSchedule#getRoom()
	 * @see #getSessionSchedule()
	 * @generated
	 */
	EReference getSessionSchedule_Room();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.samples.conference.SessionSchedule#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.emf.samples.conference.SessionSchedule#getDate()
	 * @see #getSessionSchedule()
	 * @generated
	 */
	EAttribute getSessionSchedule_Date();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.samples.conference.SessionType <em>Session Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Session Type</em>'.
	 * @see org.eclipse.emf.samples.conference.SessionType
	 * @generated
	 */
	EEnum getSessionType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.samples.conference.GENDER <em>GENDER</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>GENDER</em>'.
	 * @see org.eclipse.emf.samples.conference.GENDER
	 * @generated
	 */
	EEnum getGENDER();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ConferenceFactory getConferenceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.ConferenceImpl <em>Conference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.ConferenceImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getConference()
		 * @generated
		 */
		EClass CONFERENCE = eINSTANCE.getConference();

		/**
		 * The meta object literal for the '<em><b>Participants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFERENCE__PARTICIPANTS = eINSTANCE.getConference_Participants();

		/**
		 * The meta object literal for the '<em><b>Sessions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFERENCE__SESSIONS = eINSTANCE.getConference_Sessions();

		/**
		 * The meta object literal for the '<em><b>Venues</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFERENCE__VENUES = eINSTANCE.getConference_Venues();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFERENCE__NAME = eINSTANCE.getConference_Name();

		/**
		 * The meta object literal for the '<em><b>Overview</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONFERENCE__OVERVIEW = eINSTANCE.getConference_Overview();

		/**
		 * The meta object literal for the '<em><b>Schedule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFERENCE__SCHEDULE = eINSTANCE.getConference_Schedule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.ParticipantImpl <em>Participant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.ParticipantImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getParticipant()
		 * @generated
		 */
		EClass PARTICIPANT = eINSTANCE.getParticipant();

		/**
		 * The meta object literal for the '<em><b>Firstname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__FIRSTNAME = eINSTANCE.getParticipant_Firstname();

		/**
		 * The meta object literal for the '<em><b>Lastname</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__LASTNAME = eINSTANCE.getParticipant_Lastname();

		/**
		 * The meta object literal for the '<em><b>Attending</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARTICIPANT__ATTENDING = eINSTANCE.getParticipant_Attending();

		/**
		 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__GENDER = eINSTANCE.getParticipant_Gender();

		/**
		 * The meta object literal for the '<em><b>Is Registered</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__IS_REGISTERED = eINSTANCE.getParticipant_IsRegistered();

		/**
		 * The meta object literal for the '<em><b>Job Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__JOB_TITLE = eINSTANCE.getParticipant_JobTitle();

		/**
		 * The meta object literal for the '<em><b>Bio</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__BIO = eINSTANCE.getParticipant_Bio();

		/**
		 * The meta object literal for the '<em><b>Speaker</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARTICIPANT__SPEAKER = eINSTANCE.getParticipant_Speaker();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.SessionImpl <em>Session</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.SessionImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSession()
		 * @generated
		 */
		EClass SESSION = eINSTANCE.getSession();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SESSION__TITLE = eINSTANCE.getSession_Title();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SESSION__TYPE = eINSTANCE.getSession_Type();

		/**
		 * The meta object literal for the '<em><b>Speakers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SESSION__SPEAKERS = eINSTANCE.getSession_Speakers();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SESSION__DESCRIPTION = eINSTANCE.getSession_Description();

		/**
		 * The meta object literal for the '<em><b>Keywords</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SESSION__KEYWORDS = eINSTANCE.getSession_Keywords();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.VenueImpl <em>Venue</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.VenueImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getVenue()
		 * @generated
		 */
		EClass VENUE = eINSTANCE.getVenue();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VENUE__NAME = eINSTANCE.getVenue_Name();

		/**
		 * The meta object literal for the '<em><b>Rooms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VENUE__ROOMS = eINSTANCE.getVenue_Rooms();

		/**
		 * The meta object literal for the '<em><b>Get To The Venue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VENUE__GET_TO_THE_VENUE = eINSTANCE.getVenue_GetToTheVenue();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.RoomImpl <em>Room</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.RoomImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getRoom()
		 * @generated
		 */
		EClass ROOM = eINSTANCE.getRoom();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM__NAME = eINSTANCE.getRoom_Name();

		/**
		 * The meta object literal for the '<em><b>Capacity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROOM__CAPACITY = eINSTANCE.getRoom_Capacity();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.ScheduleImpl <em>Schedule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.ScheduleImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSchedule()
		 * @generated
		 */
		EClass SCHEDULE = eINSTANCE.getSchedule();

		/**
		 * The meta object literal for the '<em><b>Sessions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEDULE__SESSIONS = eINSTANCE.getSchedule_Sessions();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.impl.SessionScheduleImpl <em>Session Schedule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.impl.SessionScheduleImpl
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSessionSchedule()
		 * @generated
		 */
		EClass SESSION_SCHEDULE = eINSTANCE.getSessionSchedule();

		/**
		 * The meta object literal for the '<em><b>Session</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SESSION_SCHEDULE__SESSION = eINSTANCE.getSessionSchedule_Session();

		/**
		 * The meta object literal for the '<em><b>Room</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SESSION_SCHEDULE__ROOM = eINSTANCE.getSessionSchedule_Room();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SESSION_SCHEDULE__DATE = eINSTANCE.getSessionSchedule_Date();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.SessionType <em>Session Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.SessionType
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getSessionType()
		 * @generated
		 */
		EEnum SESSION_TYPE = eINSTANCE.getSessionType();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.samples.conference.GENDER <em>GENDER</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.samples.conference.GENDER
		 * @see org.eclipse.emf.samples.conference.impl.ConferencePackageImpl#getGENDER()
		 * @generated
		 */
		EEnum GENDER = eINSTANCE.getGENDER();

	}

} //ConferencePackage
