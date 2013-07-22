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

import org.eclipse.emf.common.util.Diagnostic;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFNotification {
	
	public static final int ERROR = Diagnostic.ERROR;
	public static final int WARNING = Diagnostic.WARNING;
	public static final int LOCK = 0x32;

	/**
	 * @return the kind of the current decoration.
	 */
	int getKind();
	
	/**
	 * @return the message of this notification.
	 */
	String getMessage();
}
