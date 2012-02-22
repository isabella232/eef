/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.util;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EEFControlWrapperViewer<CONTROL extends Control> extends ContentViewer {

	/**
	 * @return the main {@link Control} of this {@link PropertyEditor}.
	 */
	public abstract CONTROL getMainControl();
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	public Control getControl() {
		return getMainControl();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	public ISelection getSelection() {
		throw new UnsupportedOperationException("This method isn't supported for this viewer.");
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public void refresh() {
		throw new UnsupportedOperationException("This method isn't supported for this viewer.");
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	@Override
	public void setSelection(ISelection selection, boolean reveal) {
		throw new UnsupportedOperationException("This method isn't supported for this viewer.");
	}

}
