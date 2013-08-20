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
package org.eclipse.emf.eef.editor.internal.widgets;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.eef.runtime.context.NullPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TreeEEFViewer extends ContentViewer {

	private static final String ECLASS_DATA_KEY = "EClass";
	private EMFServiceProvider emfServiceProvider;
	private ImageManager imageManager;
	
	private EReference editedFeature;
	private Collection<TreeEEFViewerListener> listeners;

	private Composite parent;
	private SashForm control;
	private Label name;
	private TreeViewer selection;
	private EEFViewer viewer;
	private Menu addViewElementMenu;
	private SelectionSynchronizer selectionSynchronizer;
	private AddViewElementItemAdapter addViewElementItemAdapter;

	private Button addViewElementButton;
	private Button removeViewElementButton;

	
	public TreeEEFViewer(Composite parent, int style) {
		this.parent = parent;
		listeners = Lists.newArrayList();
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public final void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param imageManager the imageManager to set
	 */
	public final void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}
	
	/**
	 * Adds a {@link TreeEEFViewerListener}.
	 * @param listener the listener to add.
	 */
	public final void addListener(TreeEEFViewerListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Removes a {@link TreeEEFViewerListener}.
	 * @param listener the listener to remove.
	 */
	public void removeListener(TreeEEFViewerListener listener) {
		listeners.remove(listener);
	}

	/**
	 * @param title of the widget
	 */
	public final void setTitle(String title) {
		name.setText(title == null?"":title);
	}

	/**
	 * Creates the viewer contents
	 */
	public final void createContents() {
		control = createSashForm(parent);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		Composite selectionContainer = createComposite(control);
		selectionContainer.setLayout(new GridLayout(3, false));
		name = createLabel(selectionContainer);
		name.setText("Contents:");
		name.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Image image = imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add");
		addViewElementButton = createButton(selectionContainer, image);
		addViewElementButton.addSelectionListener(new AddViewElementAdapter());
		image = imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Delete");
		removeViewElementButton = createButton(selectionContainer, image);
		removeViewElementButton.addSelectionListener(new RemoveViewElementAdapter());
		selection = new TreeViewer(createTree(selectionContainer));
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.horizontalSpan = 3;
		selection.getControl().setLayoutData(layoutData);
		selection.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				EObject editedObject = getEditedObject();
				if (editedObject == null) {
					addViewElementButton.setEnabled(false);
					removeViewElementButton.setEnabled(false);
				} else {
					Collection<EClass> instanciableTypes = computeInstanciableTypes();
					addViewElementButton.setEnabled(instanciableTypes != null && !instanciableTypes.isEmpty());
					removeViewElementButton.setEnabled(true);
				}
			}
		});
		viewer = createEEFViewer(control);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		control.setWeights(new int[] { 35, 65});
		
	}

	/**
	 * @return 
	 * 
	 */
	protected SashForm createSashForm(Composite container) {
		return new SashForm(container, SWT.HORIZONTAL);
	}

	/**
	 * @param selectionContainer
	 * @return
	 */
	protected Tree createTree(Composite selectionContainer) {
		return new Tree(selectionContainer, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	}

	/**
	 * @return
	 */
	protected Composite createComposite(Composite control) {
		return new Composite(control, SWT.NONE);
	}

	/**
	 * @param selectionContainer
	 */
	protected Label createLabel(Composite selectionContainer) {
		return new Label(selectionContainer, SWT.NONE);
	}

	/**
	 * @param selectionContainer
	 * @param image
	 * @return 
	 */
	protected Button createButton(Composite selectionContainer, Image image) {
		Button result = new Button(selectionContainer, SWT.PUSH);
		result.setImage(image);
		result.setEnabled(false);
		return result;
	}

	/**
	 * @return
	 */
	protected EEFViewer createEEFViewer(Composite container) {
		return new EEFViewer(container, SWT.NONE);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	@Override
	public final Control getControl() {
		return control;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	@Override
	public final ISelection getSelection() {
		if (viewer != null && viewer.getInput() instanceof PropertiesEditingContext && !(viewer.getInput() instanceof NullPropertiesEditingContext)) {
			return new StructuredSelection(((PropertiesEditingContext)viewer.getInput()).getEditingComponent().getEObject());
		}
		return new StructuredSelection();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	@Override
	public final void refresh() {
		if (selection != null) {
			selection.refresh();
			viewer.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected final void inputChanged(Object input, Object oldInput) {
		if (input instanceof EEFViewerInput) {
			EObject editedObject = ((EEFViewerInput) input).getEditedObject();
			editedFeature = (EReference) ((EEFViewerInput) input).getEditedFeature();
			selection.setInput(Lists.newArrayList(editedObject));
			Object[] elements = ((IStructuredContentProvider)selection.getContentProvider()).getElements(editedObject);
			if (elements.length > 0) {
				selection.setSelection(new StructuredSelection(elements[0]));
			}
			fireSelectionChanged(new SelectionChangedEvent(this, elements.length == 0?new StructuredSelection(): new StructuredSelection(elements)));
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	@Override
	public final void setSelection(ISelection selection, boolean reveal) {
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 */
	@Override
	public final void setContentProvider(IContentProvider contentProvider) {
		super.setContentProvider(contentProvider);
		selection.setContentProvider(contentProvider);
		viewer.setContentProvider(new EEFContentProvider());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#setLabelProvider(org.eclipse.jface.viewers.IBaseLabelProvider)
	 */
	@Override
	public final void setLabelProvider(IBaseLabelProvider labelProvider) {
		super.setLabelProvider(labelProvider);
		selection.setLabelProvider(getLabelProvider());
	}

	/**
	 * @param selectionInterpreter the selectionInterpreter to set
	 */
	public final void setSelectionInterpreter(SelectionInterpreter selectionInterpreter) {
		if (selection != null) {
			if (selectionSynchronizer != null) {
				selection.removeSelectionChangedListener(selectionSynchronizer);
			}
			selectionSynchronizer = new SelectionSynchronizer(selectionInterpreter, viewer);
			selection.addSelectionChangedListener(selectionSynchronizer);
		}
	}
	
	private EObject getEditedObject() {
		EObject editedObject = null;
		if (selection.getSelection() instanceof StructuredSelection) {
			StructuredSelection sSel = (StructuredSelection)selection.getSelection();
			if (!sSel.isEmpty()) {
				editedObject = (EObject) sSel.getFirstElement();
			}
		}
		return editedObject;
	}

	private Collection<EClass> computeInstanciableTypes() {
		EObject editedObject = null;
		Collection<EClass> listOfInstanciableType = null;
		editedObject = getEditedObject();
		if (editedObject != null) {
			//TODO: find a better way to get the AdapterFactory
			AdapterFactory adapterFactory = ((AdapterFactoryContentProvider)getContentProvider()).getAdapterFactory();
			listOfInstanciableType = emfServiceProvider.getEMFService(editedObject.eClass().getEPackage()).listOfInstanciableType(adapterFactory, editedObject, editedFeature);
		}
		return listOfInstanciableType;
	}

	private Menu buildMenu(Shell shell, Collection<EClass> listOfInstanciableTypes) {
		Menu menu = new Menu(shell, SWT.POP_UP);
		if (listOfInstanciableTypes.size() > 0) {
			for (EClass eClass : listOfInstanciableTypes) {
				MenuItem item = new MenuItem (menu, SWT.PUSH);
				item.setText (eClass.getName());
				item.setData(ECLASS_DATA_KEY, eClass);
				item.addSelectionListener(getViewElementItemAdapter());
			}
		}
		return menu;
	}
	
	private AddViewElementItemAdapter getViewElementItemAdapter() {
		if (addViewElementItemAdapter == null) {
			addViewElementItemAdapter = new AddViewElementItemAdapter();
		}
		return addViewElementItemAdapter;
	}
	
	private class AddViewElementAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (addViewElementMenu == null) {
				Collection<EClass> listOfInstanciableType = computeInstanciableTypes();
				if (addViewElementMenu != null) {
					addViewElementMenu.dispose();
				}
				addViewElementMenu = buildMenu(e.display.getActiveShell(), listOfInstanciableType);
			}
			Rectangle rect = addViewElementButton.getBounds ();
			Point pt = new Point (rect.x, rect.y + rect.height);
			pt = addViewElementButton.getParent().toDisplay (pt);
			addViewElementMenu.setLocation (pt.x, pt.y);
			addViewElementMenu.setVisible (true);
		}
		
	}
	
	private class AddViewElementItemAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			Object source = e.getSource();
			if (source instanceof MenuItem) {
				EObject editedObject = getEditedObject();
				Object data = ((MenuItem)source).getData(ECLASS_DATA_KEY);
				if (data != null) {
					for (TreeEEFViewerListener listener : listeners) {
						listener.handleAdd(editedObject, (EClass) data);
					}
					//FIXME: this is a trick! need to be improved
					selection.setExpandedState(editedObject, true);
				}
			}
		}
		
	}
	
	private class RemoveViewElementAdapter extends SelectionAdapter {

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			EObject editedObject = getEditedObject();
			for (TreeEEFViewerListener listener : listeners) {
				listener.handleRemove(editedObject);
			}
		}
		
	}
	
	private final static class SelectionSynchronizer implements ISelectionChangedListener {
		
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
	
	/**
	 * 
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	public interface TreeEEFViewerListener {
		
		/**
		 * Notifies listeners that the creation of an element has been requested.
		 * @param owner the owning element.
		 * @param elementType the kind of element to create.
		 */
		public void handleAdd(EObject owner, EClass elementType);
		
		/**
		 * Notifies listeners that the remove of an element has been requested. 
		 * @param element the element to remove.
		 */
		public void handleRemove(EObject element);
		
	}
}
