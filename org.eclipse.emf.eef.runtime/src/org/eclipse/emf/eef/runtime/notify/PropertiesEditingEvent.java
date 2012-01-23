/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingEvent {

	/**
	 * @return the source view for the event.
	 */
	Object getView();
	
	/**
	 * @return the edited widget.
	 */
	Object getAffectedEditor();
	
	/**
	 * @return the event type.
	 */
	int getEventType();
	
	/**
	 * @return the old value of the edited widget.
	 */
	Object getOldValue();

	/**
	 * @return the new value of the edited widget.
	 */
	Object getNewValue();
}
