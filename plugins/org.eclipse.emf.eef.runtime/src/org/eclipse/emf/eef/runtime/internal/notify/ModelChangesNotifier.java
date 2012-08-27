/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.notify;

import org.eclipse.emf.common.notify.Adapter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelChangesNotifier extends Adapter {

	public static final String EEF_EVENT_BASE_TOPIC = "org/eclipse/emf/eef/model/";
	public static final String EEF_ECLASS_NOTIFICATION_TOPIC = "EClass/";
	public static final String EEF_GLOBAL_NOTIFICATION_TOPIC = "GlobalNotification";

}
