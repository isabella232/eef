/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotification {
	
	public static final int ERROR = Diagnostic.ERROR;
	public static final int WARNING = Diagnostic.WARNING;

	/**
	 * @return the kind of the current decoration.
	 */
	int getKind();
	
	/**
	 * @return the message of this notification.
	 */
	String getMessage();
}
