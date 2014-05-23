/**
 * 
 */
package org.eclipse.emf.eef.runtime.util;

import org.eclipse.emf.common.notify.Notification;

/**
 * @author NLEPINE
 * 
 */
public interface EEFNotificationFilterInvocationParameters extends EEFInvocationParameters {

	/**
	 * @return
	 */
	Notification getNotification();

}
