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
package org.eclipse.emf.eef.runtime.view.notify;

import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotifier extends EEFService<Object> {

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
