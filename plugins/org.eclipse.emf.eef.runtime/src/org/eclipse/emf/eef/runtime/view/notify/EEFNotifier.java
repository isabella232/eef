/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotifier {

	/**
	 * Notifies a view with a {@link EEFNotification}.
	 * @param view View to process.
	 * @param notification {@link EEFNotification} to process.
	 */
	void notify(Object view, EEFNotification notification);
	
	/**
	 * Removes the notification displayed in the managed view if exists.
	 * @param view View to process.
	 */
	void clearViewNotification(Object view);
	
	/**
	 * Removes the notification displayed on the given editor if exists.
	 * @param view View to process.
	 * @param editor editor of the view to process.
	 */
	void clearEditorNotification(Object view, Object editor);
}
