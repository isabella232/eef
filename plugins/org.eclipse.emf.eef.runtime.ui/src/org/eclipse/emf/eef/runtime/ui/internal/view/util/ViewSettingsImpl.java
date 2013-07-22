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
package org.eclipse.emf.eef.runtime.ui.internal.view.util;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.view.ViewSettings;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewSettingsImpl implements ViewSettings {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings#getMultiEditorHeight()
	 */
	public int getMultiEditorHeight() {
		return UIConstants.MULTI_EDITOR_HEIGHT;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings#getEEFSelectionDialogHeight()
	 */
	public int getEEFSelectionDialogHeight() {
		return UIConstants.EEF_SELECTION_DIALOG_HEIGHT;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings#getEEFSelectionDialogWidth()
	 */
	public int getEEFSelectionDialogWidth() {
		return UIConstants.EEF_SELECTION_DIALOG_WIDTH;
	}

}
