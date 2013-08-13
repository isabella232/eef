/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.widgets;

import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.editor.internal.widgets.util.SelectionMEEFVContentProvider;
import org.eclipse.emf.eef.runtime.context.NullPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class MultiEEFViewer extends ContentViewer {

	private SashForm control;
	private TreeViewer selection;
	private EEFViewer viewer;
	private SelectionSynchronizer selectionSynchronizer;
	
	public MultiEEFViewer(Composite parent, int style) {
		control = new SashForm(parent, SWT.HORIZONTAL);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		selection = new TreeViewer(control, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData layoutData = new GridData(GridData.FILL_VERTICAL);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		selection.getControl().setLayoutData(layoutData);
		viewer = new EEFViewer(control, SWT.NONE);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		control.setWeights(new int[] { 0, 100});
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

	public EEFViewer getSubViewer() {
		return viewer;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		if (getSubViewer() != null && getSubViewer().getInput() instanceof PropertiesEditingContext && !(getSubViewer().getInput() instanceof NullPropertiesEditingContext)) {
			return new StructuredSelection(((PropertiesEditingContext)getSubViewer().getInput()).getEditingComponent().getEObject());
		}
		return new StructuredSelection();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public void refresh() {
		inputChanged(getInput(), null);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		selection.setInput(input);
		Object[] elements = ((IStructuredContentProvider)selection.getContentProvider()).getElements(input);
		// If there is no binding or only one binding available for the selected element, 
		// the selection list is useless, we hide it.
		if (elements.length < 2) {
			control.setWeights(new int[] { 0, 100});
		} else {
			// Else we display it
			control.setWeights(new int[] { 35, 65});
		}
		if (elements.length > 0) {
			selection.setSelection(new StructuredSelection(elements[0]));
		}
		fireSelectionChanged(new SelectionChangedEvent(this, elements.length == 0?new StructuredSelection(): new StructuredSelection(elements)));
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
		selection.setContentProvider(new SelectionMEEFVContentProvider());
		viewer.setContentProvider(contentProvider);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#setLabelProvider(org.eclipse.jface.viewers.IBaseLabelProvider)
	 */
	@Override
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		super.setLabelProvider(labelProvider);
		selection.setLabelProvider(getLabelProvider());
	}

	/**
	 * @param selectionInterpreter the selectionInterpreter to set
	 */
	public void setSelectionInterpreter(SelectionInterpreter selectionInterpreter) {
		if (selection != null) {
			if (selectionSynchronizer != null) {
				selection.removeSelectionChangedListener(selectionSynchronizer);
			}
			selectionSynchronizer = new SelectionSynchronizer(selectionInterpreter, viewer);
			selection.addSelectionChangedListener(selectionSynchronizer);
		}
	}

	private static class SelectionSynchronizer implements ISelectionChangedListener {
		
		private SelectionInterpreter selectionInterpreter;
		private EEFViewer viewer;
		
		public SelectionSynchronizer(SelectionInterpreter selectionInterpreter, EEFViewer viewer) {
			this.selectionInterpreter = selectionInterpreter;
			this.viewer = viewer;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSelection() != null) {
				PropertiesEditingContext context = selectionInterpreter.createContextFromSelection(event.getSelection());
				if (context != null) {
					viewer.setInput(context);
				}
			}
		}
		
	}
	
	public interface SelectionInterpreter {
		
		/**
		 * Creates a {@link PropertiesEditingContext} from the given JFace Selection.
		 * @param selection the selected element.
		 * @return the {@link PropertiesEditingContext} to set as input of the EEF Viewer.
		 */
		public PropertiesEditingContext createContextFromSelection(ISelection selection);
		
	}	
}
