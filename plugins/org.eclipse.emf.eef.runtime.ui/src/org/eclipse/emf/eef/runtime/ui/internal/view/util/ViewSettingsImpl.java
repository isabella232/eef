/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.util;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.view.ViewSettings;
import org.eclipse.emf.eef.runtime.ui.widgets.util.SelectionMode;

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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings#getSelectionMode()
	 */
	public SelectionMode getSelectionMode() {
		return SelectionMode.TREE;
	}

}
