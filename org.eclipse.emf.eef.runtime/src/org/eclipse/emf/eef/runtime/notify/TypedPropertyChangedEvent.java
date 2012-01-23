/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.beans.PropertyChangeEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TypedPropertyChangedEvent extends PropertyChangeEvent {

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -3014044502487758802L;
	
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
