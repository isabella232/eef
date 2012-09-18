/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public interface PropertiesEditingMessageManager {

	/**
	 * @param notification 
	 */
	void processMessage(EEFNotification notification);

	void clearMessage();

}