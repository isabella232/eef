/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.notify;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.event.EventAdmin;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public final class ModelChangesNotifierImpl extends AbstractModelChangesNotifier {

	/**
	 * @param eventAdmin
	 */
	public ModelChangesNotifierImpl(final EventAdmin eventAdmin) {
		super(eventAdmin);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.notify.AbstractModelChangesNotifier#getTopic(org.eclipse.emf.common.notify.Notification)
	 */
	public String getTopic(Notification notification) {
		String topic;
		if (notification.getNotifier() instanceof EObject) {
			EObject notifier = (EObject) notification.getNotifier();
			topic = EEF_EVENT_BASE_TOPIC + EEF_ECLASS_NOTIFICATION_TOPIC + notifier.eClass().getEPackage().getName() + "_" + notifier.eClass().getName();
		} else {
			topic = EEF_EVENT_BASE_TOPIC + EEF_GLOBAL_NOTIFICATION_TOPIC;
		}
		return topic;
	}

}
