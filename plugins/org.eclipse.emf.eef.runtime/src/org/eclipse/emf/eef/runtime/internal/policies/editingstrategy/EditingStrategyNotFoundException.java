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
package org.eclipse.emf.eef.runtime.internal.policies.editingstrategy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingStrategyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1227421630449755759L;

	/**
	 * 
	 */
	public EditingStrategyNotFoundException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EditingStrategyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public EditingStrategyNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public EditingStrategyNotFoundException(Throwable cause) {
		super(cause);
	}

}
