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
package org.eclipse.emf.eef.runtime.logging;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLogger {
	
	/**
	 * Logs a warning.
	 * @param pluginID Plugin ID were the error occured.
	 * @param message error message.
	 * @param t the {@link Throwable} which was thrown. Can be <code>null</code>.
	 */
	void logWarning(String pluginID, String message, Throwable t);

	/**
	 * Logs an error.
	 * @param pluginID Plugin ID were the error occured.
	 * @param message error message.
	 * @param t the {@link Throwable} which was thrown. Can be <code>null</code>.
	 */
	void logError(String pluginID, String message, Throwable t);

}
