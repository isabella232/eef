/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingListener {

	/**
	 * Specifies that a feature value has changed.
	 * 
	 * @param event information about this change.
	 */
	void firePropertiesChanged(PropertiesEditingEvent event);
	
}
