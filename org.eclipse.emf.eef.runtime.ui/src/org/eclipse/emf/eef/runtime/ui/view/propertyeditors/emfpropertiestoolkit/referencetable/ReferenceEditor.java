/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit.referencetable;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Widget;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReferenceEditor extends StructuredViewer {

	private Composite control;
	private TreeViewer tree;
	
	/**
	 * @param parent
	 * @param style
	 */
	public ReferenceEditor(Composite parent, int style) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new GridLayout(4, false));
		Button addButton = new Button(control, SWT.PUSH);
		GridData addButtonData = new GridData(GridData.FILL_HORIZONTAL);
		addButtonData.horizontalAlignment = SWT.RIGHT;
		addButton.setImage(EEFRuntimeUI.getPlugin().getRuntimeImage("Add"));
		addButton.setLayoutData(addButtonData);
		
		GridData buttonData = new GridData();
		buttonData.horizontalAlignment = SWT.RIGHT;
		Button removeButton = new Button(control, SWT.PUSH);
		removeButton.setImage(EEFRuntimeUI.getPlugin().getRuntimeImage("Delete"));
		removeButton.setLayoutData(buttonData);
		Button upButton = new Button(control, SWT.PUSH);
		upButton.setImage(EEFRuntimeUI.getPlugin().getRuntimeImage("ArrowUp"));
		upButton.setLayoutData(buttonData);
		Button downButton = new Button(control, SWT.PUSH);
		downButton.setImage(EEFRuntimeUI.getPlugin().getRuntimeImage("ArrowDown"));
		downButton.setLayoutData(buttonData);

		tree = new TreeViewer(control, SWT.BORDER);
		GridData treeData = new GridData(GridData.FILL_BOTH);
		treeData.horizontalSpan = 4;
		tree.getControl().setLayoutData(treeData);
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ContentViewer#getContentProvider()
	 */
	public IContentProvider getContentProvider() {
		return tree.getContentProvider();
	}

	/**
	 * @see org.eclipse.jface.viewers.ContentViewer#getInput()
	 */
	public Object getInput() {
		return tree.getInput();
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#getLabelProvider()
	 */
	public IBaseLabelProvider getLabelProvider() {
		return tree.getLabelProvider();
	}

	/**
	 * @see org.eclipse.jface.viewers.ColumnViewer#setLabelProvider(org.eclipse.jface.viewers.IBaseLabelProvider)
	 */
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		tree.setLabelProvider(labelProvider);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDoubleClickListener(org.eclipse.jface.viewers.IDoubleClickListener)
	 */
	public void addDoubleClickListener(IDoubleClickListener listener) {
		tree.addDoubleClickListener(listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		tree.setSelection(selection);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDragSupport(int, org.eclipse.swt.dnd.Transfer[], org.eclipse.swt.dnd.DragSourceListener)
	 */
	public void addDragSupport(int operations, Transfer[] transferTypes, DragSourceListener listener) {
		tree.addDragSupport(operations, transferTypes, listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDropSupport(int, org.eclipse.swt.dnd.Transfer[], org.eclipse.swt.dnd.DropTargetListener)
	 */
	public void addDropSupport(int operations, Transfer[] transferTypes, DropTargetListener listener) {
		tree.addDropSupport(operations, transferTypes, listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void addFilter(ViewerFilter filter) {
		tree.addFilter(filter);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#getFilters()
	 */
	public ViewerFilter[] getFilters() {
		return tree.getFilters();
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 */
	public void setContentProvider(IContentProvider provider) {
		tree.setContentProvider(provider);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#getSorter()
	 */
	public ViewerSorter getSorter() {
		return tree.getSorter();
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		tree.setSelection(selection, reveal);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#setFilters(org.eclipse.jface.viewers.ViewerFilter[])
	 */
	public void setFilters(ViewerFilter[] filters) {
		tree.setFilters(filters);
	}

	/**
	 * @param listener
	 * @see org.eclipse.jface.viewers.Viewer#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		tree.addSelectionChangedListener(listener);
	}

	/**
	 * @param listener
	 * @see org.eclipse.jface.viewers.StructuredViewer#addPostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		tree.addPostSelectionChangedListener(listener);
	}

	/**
	 * @param listener
	 * @see org.eclipse.jface.viewers.AbstractTreeViewer#addTreeListener(org.eclipse.jface.viewers.ITreeViewerListener)
	 */
	public void addTreeListener(ITreeViewerListener listener) {
		tree.addTreeListener(listener);
	}

	/**
	 * @param layoutData
	 * @see org.eclipse.swt.widgets.Control#setLayoutData(java.lang.Object)
	 */
	public void setLayoutData(Object layoutData) {
		control.setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#doFindInputItem(java.lang.Object)
	 */
	@Override
	protected Widget doFindInputItem(Object element) {
		//TODO
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#doFindItem(java.lang.Object)
	 */
	@Override
	protected Widget doFindItem(Object element) {
		//TODO
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#doUpdateItem(org.eclipse.swt.widgets.Widget, java.lang.Object, boolean)
	 */
	@Override
	protected void doUpdateItem(Widget item, Object element, boolean fullMap) {
		//TODO
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#getSelectionFromWidget()
	 */
	@Override
	protected List<?> getSelectionFromWidget() {
		ISelection selection = tree.getSelection();
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).toList();
		} else {
			if (selection != null) {
				if (selection.isEmpty()) {
					return Collections.EMPTY_LIST;
				} else {
					return Lists.newArrayList(selection);
				}
			}
		}
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#internalRefresh(java.lang.Object)
	 */
	@Override
	protected void internalRefresh(Object element) {
		tree.refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#reveal(java.lang.Object)
	 */
	@Override
	public void reveal(Object element) {
		tree.reveal(element);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(java.util.List, boolean)
	 */
	@Override
	protected void setSelectionToWidget(@SuppressWarnings("rawtypes") List elements, boolean reveal) {
		StructuredSelection selection = new StructuredSelection(elements);
		tree.setSelection(selection, reveal);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public Control getControl() {
		return control;
	}

}
