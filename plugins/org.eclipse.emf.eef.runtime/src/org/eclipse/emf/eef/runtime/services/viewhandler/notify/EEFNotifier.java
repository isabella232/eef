/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.viewhandler.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotifier {

	/**
	 * Notifies a view with a {@link EEFNotification}.
	 * @param notification {@link EEFNotification} to process.
	 */
	void notify(EEFNotification notification);
	
	/**
	 * Removes the notification displayed in the managed view if exists.
	 */
	void clearViewNotification();
	
	/**
	 * Removes the notification displayed on the given editor if exists.
	 * @param editor editor of the view to process.
	 */
	void clearEditorNotification(Object editor);
}
