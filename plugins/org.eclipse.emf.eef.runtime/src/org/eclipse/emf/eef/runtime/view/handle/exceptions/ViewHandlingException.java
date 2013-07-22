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
package org.eclipse.emf.eef.runtime.view.handle.exceptions;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlingException extends Exception {

	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -6177074408752086194L;

	/**
	 * 
	 */
	public ViewHandlingException() {
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ViewHandlingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ViewHandlingException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ViewHandlingException(Throwable arg0) {
		super(arg0);
	}

	
	
}
