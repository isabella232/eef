/**
 * 
 */
package org.eclipse.emf.samples.conference.provider;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferenceCustomItemProviderAdapterFactory extends ConferenceItemProviderAdapterFactory {

	public ConferenceCustomItemProviderAdapterFactory() {
		super();
		
		// additional item providers
		supportedTypes.add(ITableItemLabelProvider.class);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createConferenceAdapter()
	 */
	
	public Adapter createConferenceAdapter() {
		if (conferenceItemProvider == null) {
			conferenceItemProvider = new ConferenceCustomItemProvider(this);
		}

		return conferenceItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createParticipantAdapter()
	 */
	
	public Adapter createParticipantAdapter() {
		if (participantItemProvider == null) {
			participantItemProvider = new ParticipantCustomItemProvider(this);
		}

		return participantItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createSessionAdapter()
	 */
	
	public Adapter createSessionAdapter() {
		if (sessionItemProvider == null) {
			sessionItemProvider = new SessionCustomItemProvider(this);
		}

		return sessionItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createVenueAdapter()
	 */
	
	public Adapter createVenueAdapter() {
		if (venueItemProvider == null) {
			venueItemProvider = new VenueCustomItemProvider(this);
		}

		return venueItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createRoomAdapter()
	 */
	
	public Adapter createRoomAdapter() {
		if (roomItemProvider == null) {
			roomItemProvider = new RoomCustomItemProvider(this);
		}

		return roomItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createScheduleAdapter()
	 */
	
	public Adapter createScheduleAdapter() {
		if (scheduleItemProvider == null) {
			scheduleItemProvider = new ScheduleItemProvider(this);
		}
		return scheduleItemProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.samples.conference.provider.ConferenceItemProviderAdapterFactory#createSessionScheduleAdapter()
	 */
	
	public Adapter createSessionScheduleAdapter() {
		if (sessionScheduleItemProvider == null) {
			sessionScheduleItemProvider = new SessionScheduleCustomItemProvider(this);
		}

		return sessionScheduleItemProvider;
	}

	
	
}
