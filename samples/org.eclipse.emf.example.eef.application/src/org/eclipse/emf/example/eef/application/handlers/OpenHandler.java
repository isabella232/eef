/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.example.eef.application.handlers;

import org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenHandler;
import org.eclipse.emf.example.eef.application.ConferenceApplicationConstants;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OpenHandler extends AbstractEEFOpenHandler {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenHandler#getEditingDomainKey()
	 */
	@Override
	protected String getEditingDomainKey() {
		return ConferenceApplicationConstants.EDITINGDOMAIN;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenHandler#getElementContainerID()
	 */
	@Override
	protected String getElementContainerID() {
		return ConferenceApplicationConstants.APPLICATION_PARTSTACK;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.application.handlers.AbstractEEFOpenHandler#getFilterExtensions()
	 */
	@Override
	protected String[] getFilterExtensions() {
		return new String[] { "*.conference" };
	}
	
}
