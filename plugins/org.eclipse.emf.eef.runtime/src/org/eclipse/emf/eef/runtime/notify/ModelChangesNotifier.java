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

import org.eclipse.emf.common.notify.Adapter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface ModelChangesNotifier extends Adapter {

	public static final String EEF_EVENT_BASE_TOPIC = "org/eclipse/emf/eef/model/";
	public static final String EEF_ECLASS_NOTIFICATION_TOPIC = "EClass/";
	public static final String EEF_GLOBAL_NOTIFICATION_TOPIC = "GlobalNotification";
	public static final String EEF_EVENT_BASE_SETTINGS = "org/eclipse/emf/eef/settings/";

}
