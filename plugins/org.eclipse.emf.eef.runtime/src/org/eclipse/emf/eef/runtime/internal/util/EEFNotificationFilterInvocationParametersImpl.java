/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.util.EEFNotificationFilterInvocationParameters;

/**
 * @author NLEPINE
 * 
 */
public class EEFNotificationFilterInvocationParametersImpl extends EEFInvocationParametersImpl implements EEFNotificationFilterInvocationParameters {

	private Notification notification;

	/**
	 * @param editingContext
	 * @param notification
	 */
	public EEFNotificationFilterInvocationParametersImpl(PropertiesEditingContext editingContext, Notification notification) {
		super(editingContext);
		this.notification = notification;
	}

	/**
	 * @return the notification
	 */
	public Notification getNotification() {
		return notification;
	}

}
