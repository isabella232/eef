/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.widgets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.internal.widgets.util.NullContentProvider;
import org.eclipse.emf.eef.runtime.ui.services.resources.ImageManager;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Widget;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceEditor extends StructuredViewer {

	/**
	 * Constant usable in the addColumn method as width parameter.
	 */
	public static final int UNDEFINED_COLUMN_WIDTH = -1;
	
	private Composite parent;
	private int style;
	
	private int lowerBound = 0;
	private int upperBound = -1;
	
	private ImageManager imageManager;

	private Composite control;
	private TableViewer table;
	private Collection<ReferenceEditorListener> listeners;
	private Button addButton;
	private Button removeButton;
	private Button upButton;
	private Button downButton;
	
	private boolean locked;

	private List<ColumnSettings> columnsToInit = Lists.newArrayList();
	
	/**
	 * @param parent
	 * @param style
	 */
	public EReferenceEditor(Composite parent, int style) {
		this.parent = parent;
		this.style = style;
	}


	public void createContents() {
		control = createControlComposite(parent);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		control.setLayout(layout);
		table = new TableViewer(control, style);
		if (!columnsToInit.isEmpty()) {
			initColumns();
		}
		table.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				updateButtons();
			}
		});
		addButton = createButton(control);
		GridData buttonData = new GridData();
		buttonData.verticalAlignment = SWT.UP;
		if (imageManager != null) {
			addButton.setImage(imageManager.getImage(EEFRuntimeUI.getResourceLocator(), "Add"));
		} else {
			addButton.setText("add");			
		}
		addButton.setLayoutData(buttonData);
		addButton.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				for (ReferenceEditorListener listener : listeners) {
					listener.add();
				}
			}

		});

		removeButton = createButton(control);
		if (imageManager != null) {
			removeButton.setImage(imageManager.getImage(EEFRuntimeUI.getResourceLocator(), "Delete"));
		} else {
			removeButton.setText("del");
		}
		removeButton.setLayoutData(buttonData);
		removeButton.addSelectionListener(new ReferenceEditorSelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorSelectionAdapter#fireSingleSelection(java.lang.Object)
			 */
			public void fireSingleSelection(Object selection) {
				for (ReferenceEditorListener listener : listeners) {
					listener.remove(selection);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorSelectionAdapter#fireMultiSelection(java.util.List)
			 */
			protected void fireMultiSelection(List<?> selection) {
				for (ReferenceEditorListener listener : listeners) {
					listener.removeAll(selection);
				}
			}


		});

		upButton = createButton(control);
		if (imageManager != null) {
			upButton.setImage(imageManager.getImage(EEFRuntimeUI.getResourceLocator(), "ArrowUp"));
		} else {
			upButton.setText("up");
		}
		upButton.setLayoutData(buttonData);
		upButton.addSelectionListener(new ReferenceEditorSelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorSelectionAdapter#fireSingleSelection(java.lang.Object)
			 */
			public void fireSingleSelection(Object selection) {
				for (ReferenceEditorListener listener : listeners) {
					listener.moveUp(selection);
				}
			}
		});

		downButton = createButton(control);
		if (imageManager != null) {
			downButton.setImage(imageManager.getImage(EEFRuntimeUI.getResourceLocator(), "ArrowDown"));
		} else {
			downButton.setText("down");			
		}
		downButton.setLayoutData(buttonData);
		downButton.addSelectionListener(new ReferenceEditorSelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorSelectionAdapter#fireSingleSelection(java.lang.Object)
			 */
			public void fireSingleSelection(Object selection) {
				for (ReferenceEditorListener listener : listeners) {
					listener.moveDown(selection);
				}
			}
		});
		buildAdditionnalActionControls(control);
		GridData treeData = new GridData(GridData.FILL_BOTH);
		treeData.verticalSpan = control.getChildren().length - 1;
		table.getControl().setLayoutData(treeData);
		listeners = Lists.newArrayList();
	}
	
	private void initColumns() {
		table.getTable().setHeaderVisible(true);
		for (ColumnSettings col : columnsToInit) {
			TableColumn column = new TableColumn(table.getTable(), SWT.NONE);
			column.setText(col.name);
			if (col.width > 0) {
				column.setWidth(col.width);
			}
		}
		columnsToInit.clear();
	}


	/**
	 * Adds a column to the Table
	 * @param name name of the column
	 * @param width width of the column. This value can be UNDEFINED_COLUMN_WIDTH. 
	 */
	public void addColumn(String name, int width) {
		if (table != null) {
			table.getTable().setHeaderVisible(true);
			TableColumn column = new TableColumn(table.getTable(), SWT.NONE);
			column.setText(name);
			if (width > 0) {
				column.setWidth(width);
			}
		} else {
			columnsToInit.add(new ColumnSettings(name, width));
		}
	}

	/**
	 * @param parent
	 * @return
	 */
	protected Composite createControlComposite(Composite parent) {
		return new Composite(parent, SWT.NONE);
	}
	
	/**
	 * @return
	 */
	protected Button createButton(Composite control) {
		return new Button(control, SWT.PUSH);
	}

	protected void buildAdditionnalActionControls(Composite parent) {
		//Do nothing.
	}

	/**
	 * @param lowerBound the lowerBound to set
	 */
	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	/**
	 * @param upperBound the upperBound to set
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * Add a {@link ReferenceEditorListener} to this instance.
	 * @param listener {@link ReferenceEditorListener} to add.
	 */
	public void addReferenceEditorListener(ReferenceEditorListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Remove a {@link ReferenceEditorListener} to this instance.
	 * @param listener {@link ReferenceEditorListener} to remove.
	 */
	public void removeReferenceEditorListener(ReferenceEditorListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * @see org.eclipse.jface.viewers.ContentViewer#getContentProvider()
	 */
	public IContentProvider getContentProvider() {
		return table.getContentProvider();
	}

	/**
	 * @see org.eclipse.jface.viewers.ContentViewer#getInput()
	 */
	public Object getInput() {
		return table.getInput();
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	protected void inputChanged(Object input, Object oldInput) {
		table.setInput(input);
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#getLabelProvider()
	 */
	public IBaseLabelProvider getLabelProvider() {
		return table.getLabelProvider();
	}

	/**
	 * @see org.eclipse.jface.viewers.ColumnViewer#setLabelProvider(org.eclipse.jface.viewers.IBaseLabelProvider)
	 */
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		table.setLabelProvider(labelProvider);
	}

	/**
	 * @param imageManager the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}


	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDoubleClickListener(org.eclipse.jface.viewers.IDoubleClickListener)
	 */
	public void addDoubleClickListener(IDoubleClickListener listener) {
		table.addDoubleClickListener(listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {
		table.setSelection(selection);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDragSupport(int, org.eclipse.swt.dnd.Transfer[], org.eclipse.swt.dnd.DragSourceListener)
	 */
	public void addDragSupport(int operations, Transfer[] transferTypes, DragSourceListener listener) {
		table.addDragSupport(operations, transferTypes, listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addDropSupport(int, org.eclipse.swt.dnd.Transfer[], org.eclipse.swt.dnd.DropTargetListener)
	 */
	public void addDropSupport(int operations, Transfer[] transferTypes, DropTargetListener listener) {
		table.addDropSupport(operations, transferTypes, listener);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void addFilter(ViewerFilter filter) {
		table.addFilter(filter);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#getFilters()
	 */
	public ViewerFilter[] getFilters() {
		return table.getFilters();
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 */
	public void setContentProvider(IContentProvider provider) {
		super.setContentProvider(new NullContentProvider());
		table.setContentProvider(provider);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#getSorter()
	 */
	public ViewerSorter getSorter() {
		return table.getSorter();
	}

	/**
	 * @see org.eclipse.jface.viewers.TreeViewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {
		table.setSelection(selection, reveal);
	}

	/**
	 * @see org.eclipse.jface.viewers.StructuredViewer#setFilters(org.eclipse.jface.viewers.ViewerFilter[])
	 */
	public void setFilters(ViewerFilter[] filters) {
		table.setFilters(filters);
	}

	/**
	 * @param listener
	 * @see org.eclipse.jface.viewers.Viewer#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		table.addSelectionChangedListener(listener);
	}

	/**
	 * @param listener
	 * @see org.eclipse.jface.viewers.StructuredViewer#addPostSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addPostSelectionChangedListener(ISelectionChangedListener listener) {
		table.addPostSelectionChangedListener(listener);
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
		ISelection selection = table.getSelection();
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
		table.refresh();
		updateButtons();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#reveal(java.lang.Object)
	 */
	@Override
	public void reveal(Object element) {
		table.reveal(element);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.StructuredViewer#setSelectionToWidget(java.util.List, boolean)
	 */
	@Override
	protected void setSelectionToWidget(@SuppressWarnings("rawtypes") List elements, boolean reveal) {
		StructuredSelection selection = new StructuredSelection(elements);
		table.setSelection(selection, reveal);
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
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
		updateButtons();
	}

	/**
	 * Update the list buttons.
	 */
	protected void updateButtons() {
		StructuredSelection selection = (StructuredSelection) table.getSelection();
		addButton.setEnabled(shouldEnableAdd(selection));
		if (selection.size() == 0) {
			removeButton.setEnabled(false);
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		} else if (selection.size() == 1) {
			removeButton.setEnabled(shouldEnableRemove(selection));
			upButton.setEnabled(shouldEnableUp(selection));
			downButton.setEnabled(shouldEnableDown(selection));
		} else {
			removeButton.setEnabled(shouldEnableRemove(selection));
			upButton.setEnabled(false);
			downButton.setEnabled(false);
		}
	}

	private boolean shouldEnableAdd(StructuredSelection selection) {
		Object[] elements = ((IStructuredContentProvider)table.getContentProvider()).getElements(table.getInput());
		return !locked && ((upperBound == -1) || (elements.length < upperBound));
	}
	
	private boolean shouldEnableRemove(StructuredSelection selection) {
		Object[] elements = ((IStructuredContentProvider)table.getContentProvider()).getElements(table.getInput());
		return !locked && ((lowerBound == 0) || (elements.length > lowerBound));
	}
	
	private boolean shouldEnableUp(StructuredSelection selection) {
		Object selectedElement = selection.getFirstElement();
		Object[] elements = ((IStructuredContentProvider)table.getContentProvider()).getElements(table.getInput());
		if (elements != null) {
			List<?> listInput = Arrays.asList(elements);
			return !locked && (listInput.size() > 1  && listInput.indexOf(selectedElement) > 0);
		}
		return false;
	}

	private boolean shouldEnableDown(StructuredSelection selection) {
		Object selectedElement = selection.getFirstElement();
		Object[] elements = ((IStructuredContentProvider)table.getContentProvider()).getElements(table.getInput());
		if (elements != null) {
			List<?> listInput = Arrays.asList(elements);
			return !locked && (listInput.size() > 1)  && (listInput.indexOf(selectedElement) < listInput.size() - 1);
		}
		return false;
	}

	/**
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	public interface ReferenceEditorListener {

		/**
		 * Handle a "add" request.
		 */
		void add();
		
		/**
		 * Handle a "edit" request.
		 * @param editedElement Edited Element.
		 */
		void edit(Object editedElement);
		
		/**
		 * Handle a "remove" request.
		 * @param removedElement Removed Element.
		 */
		void remove(Object removedElement);
		
		/**
		 * Handle a "remove" request for several elements.
		 * @param removedElements Removed Elements.
		 */
		void removeAll(Collection<?> removedElements);
		
		/**
		 * Handle a "move up" request.
		 * @param movedElement Moved Element.
		 */
		void moveUp(Object movedElement);
		
		/**
		 * Handle a "move down" request.
		 * @param movedElement Moved Element.
		 */
		void moveDown(Object movedElement);
		
	}
	
	/**
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	private abstract class ReferenceEditorSelectionAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			if (table.getSelection() != null && !table.getSelection().isEmpty()) {
				if (table.getSelection() instanceof StructuredSelection) {
					StructuredSelection sSel = (StructuredSelection) table.getSelection();
					if (sSel.size() == 1) {
						fireSingleSelection(sSel.getFirstElement());
					} else {
						fireMultiSelection((List<?>) sSel.toList());
					}

				}
			}
		}
		
		public abstract void fireSingleSelection(Object selection);
		
		protected void fireMultiSelection(List<?> selection) {
			//do nothing
		}
	}

	
	private static final class ColumnSettings {
		
		private String name;
		private int width;
		
		/**
		 * @param name
		 * @param width
		 */
		public ColumnSettings(String name, int width) {
			this.name = name;
			this.width = width;
		}
		
		
	}
}
