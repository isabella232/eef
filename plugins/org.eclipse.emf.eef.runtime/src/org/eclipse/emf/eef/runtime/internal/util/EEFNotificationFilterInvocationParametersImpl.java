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
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.util.EEFNotificationFilterInvocationParameters;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
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
