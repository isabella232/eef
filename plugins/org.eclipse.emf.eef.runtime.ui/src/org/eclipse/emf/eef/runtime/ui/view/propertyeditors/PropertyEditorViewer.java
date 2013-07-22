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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorViewer<VIEWER> {

	/**
	 * @return the main {@link Viewer} of this editor.
	 */
	VIEWER getViewer();

	/**
	 * Locks the current editor towards the given {@link EEFPropertyLock}.
	 * @param lock lock configuration.
	 */
	void lock();
	
	/**
	 * Unlocks the current editor.
	 */
	void unlock();
}
