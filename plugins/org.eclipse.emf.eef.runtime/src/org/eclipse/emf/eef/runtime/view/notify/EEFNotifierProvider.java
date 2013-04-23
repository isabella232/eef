/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotifierProvider {
	
	/**
	 * Returns a notifier able to handle the given view.
	 * @param view the view to notify.
	 * @return an {@link EEFNotifier} able to handle the given view.
	 */
	EEFNotifier getNotifier(Object view);

}
