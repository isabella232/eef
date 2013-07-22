/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.widgets;

import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class MultiEEFViewer extends ContentViewer {

	private Composite control;
	private ListViewer selection;
	private EEFViewer viewer;
	
	public MultiEEFViewer(Composite parent, int style) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout(2, false));
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		selection = new ListViewer(control, SWT.BORDER);
		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		selection.getControl().setLayoutData(layoutData);
		viewer = new EEFViewer(control, SWT.NONE);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public void refresh() {

	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		selection.setInput(input);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	@Override
	public void setSelection(ISelection selection, boolean reveal) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 */
	@Override
	public void setContentProvider(IContentProvider contentProvider) {
		assert contentProvider instanceof EEFContentProvider:"Bad ContentProvider type. Expected EEFMultiContentProvider";
		super.setContentProvider(contentProvider);
		selection.setContentProvider(new IStructuredContentProvider() {
			
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) { }
			
			public void dispose() {	}
			
			public Object[] getElements(Object inputElement) {
				System.out.println();
				return null;
			}
		});
		viewer.setContentProvider(contentProvider);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#labelProviderChanged()
	 */
	@Override
	protected void labelProviderChanged() {
		selection.setLabelProvider(getLabelProvider());
		super.labelProviderChanged();
	}

}
