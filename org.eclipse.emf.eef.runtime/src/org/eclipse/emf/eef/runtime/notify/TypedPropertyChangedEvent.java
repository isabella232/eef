/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;

import org.eclipse.emf.common.notify.Notification;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TypedPropertyChangedEvent extends PropertyChangeEvent {

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -4645517232176703327L;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a feature of the notifier has been set.
	 * This applies for simple features.
	 * @see Notification#getEventType
	 */
	int SET = Notification.SET;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a feature of the notifier has been unset.
	 * This applies for unsettable features.
	 * @see Notification#getEventType
	 */
	int UNSET = Notification.UNSET;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a value has been inserted into a list-based feature of the notifier.
	 * @see Notification#getEventType
	 */
	int ADD = Notification.ADD;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a value has been removed from a list-based feature of the notifier.
	 * @see Notification#getEventType
	 */
	int REMOVE = Notification.REMOVE;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a several values have been added into a list-based feature of the notifier.
	 * @see Notification#getEventType
	 */
	int ADD_MANY = Notification.ADD_MANY;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a several values have been removed from a list-based feature of the notifier.
	 * @see Notification#getEventType
	 */
	int REMOVE_MANY = Notification.REMOVE_MANY;

	/**
	 * An {@link Notification#getEventType event type} indicating that 
	 * a value has been moved within a list-based feature of the notifier.
	 * @see Notification#getEventType
	 */
	int MOVE = Notification.MOVE;

	private int eventType;

	/**
	 * @param source
	 * @param propertyName
	 * @param eventType
	 * @param oldValue
	 * @param newValue
	 */
	public TypedPropertyChangedEvent(Object source, String propertyName, int eventType, Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
		this.eventType = eventType;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

}
