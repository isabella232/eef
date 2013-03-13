/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.util;

import org.eclipse.emf.eef.runtime.ui.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.view.ViewSettings2;
import org.eclipse.emf.eef.runtime.ui.widgets.util.SelectionMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewSettingsImpl implements ViewSettings2 {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings2#getSelectionMode()
	 */
	public SelectionMode getSelectionMode() {
		return SelectionMode.TREE;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings2#getDecoratorPositioning()
	 */
	public int getDecoratorPositioning() {
		return SWT.TOP | SWT.LEFT;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings2#getErrorDecorationImage()
	 */
	public Image getErrorDecorationImage() {
		return EEFSWTConstants.ERROR_DECORATOR;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings2#getWarningDecorationImage()
	 */
	public Image getWarningDecorationImage() {
		return EEFSWTConstants.WARNING_DECORATOR;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.ViewSettings2#getLockDecorationImage()
	 */
	public Image getLockDecorationImage() {
		return EEFSWTConstants.LOCK_DECORATOR;
	}

}
