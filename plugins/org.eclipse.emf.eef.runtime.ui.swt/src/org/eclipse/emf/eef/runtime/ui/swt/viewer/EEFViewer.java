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
package org.eclipse.emf.eef.runtime.ui.swt.viewer;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewFilter;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.filters.EEFViewerFilter;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: ADD PartFilter management
 */
public class EEFViewer extends ContentViewer implements IEEFViewer {

	private PropertiesEditingContext input;

	private Composite control;
	protected CTabFolder folder;
	protected ScrolledComposite scroll;
	private ItemListener listener;
	private boolean dynamicTabHeader = true;
	private List<EEFViewerFilter> filters;

	/**
	 * @param parent {@link Composite} containing this viewer.
	 * @param style viewer.
	 */
	public EEFViewer(Composite parent, int style) {
		control = new Composite(parent, SWT.NONE);
		control.setLayout(new FillLayout());
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		scroll = new ScrolledComposite(control, SWT.V_SCROLL | SWT.H_SCROLL);
		folder = new CTabFolder(scroll, style);
		folder.setSimple(false);
		folder.setTabHeight(0);
		scroll.setContent(folder);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		listener = new ItemListener();
		folder.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				listener.updateControlListener();
			}

		});
		control.addControlListener(listener);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getControl()
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * @param dynamicTabHeader <code>true</code> if the TabHeaders must be dynamic (i.e. disappear when there is only one view).
	 */
	public void setDynamicTabHeader(boolean dynamicTabHeader) {
		this.dynamicTabHeader = dynamicTabHeader;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ContentViewer#setContentProvider(org.eclipse.jface.viewers.IContentProvider)
	 */
	public void setContentProvider(IContentProvider contentProvider) {
		assert contentProvider instanceof EEFContentProvider:"The content provider of this viewer must implement EEFContentProvider interface.";
		super.setContentProvider(contentProvider);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#inputChanged(java.lang.Object, java.lang.Object)
	 */
	protected void inputChanged(Object input, Object oldInput) {
		assert input instanceof PropertiesEditingContext:"Bad input type for EEFViewer. Expecting PropertiesEditingContext.";
		this.input = (PropertiesEditingContext)input;
		refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	public void refresh() {
		if (input != null) {
			int selectionIndex = folder.getSelectionIndex();
			clear();
			PropertiesEditingComponent component = input.getEditingComponent();
			int i = 1;
			for (org.eclipse.emf.eef.runtime.editingModel.View descriptor : component.getViewDescriptors()) {
				ViewHandler<?> viewHandler = input.getViewHandlerProvider().getViewHandler(descriptor);
				if (viewHandler instanceof PropertiesEditingViewHandler) {
					PropertiesEditingViewHandler propertiesEditingViewHandler = (PropertiesEditingViewHandler) viewHandler;
					org.eclipse.emf.eef.views.View viewDescriptor = (org.eclipse.emf.eef.views.View) ((EObjectView)descriptor).getDefinition();
					boolean filtered = isFiltered(viewDescriptor);
					if (filtered) {
						try {
							CTabItem item = new CTabItem(folder, SWT.NONE);
							item.setText(viewDescriptor.getName());
							PropertiesEditingView<Composite> view = propertiesEditingViewHandler.createView(component, descriptor,  folder);
							view.getContents().setLayoutData(new GridData(GridData.FILL_BOTH));
							propertiesEditingViewHandler.initView(component, view);
							item.setControl(view.getContents());
						} catch (ViewConstructionException e) {
							EEFLogger logger = propertiesEditingViewHandler.getLogger();
							logger.logError("org.eclipse.emf.eef.runtime.ui.swt", "An error occured during view creation.", e);
						}
					}
				} else if (viewHandler instanceof SWTViewHandler) {
					SWTViewHandler swtViewHandler = (SWTViewHandler) viewHandler;
					CTabItem item = new CTabItem(folder, SWT.NONE);
					item.setText("View " + i);
					Composite viewComposite = new Composite(folder, SWT.NONE);
					viewComposite.setLayout(new FillLayout());
					try {
						Composite view = swtViewHandler.createView(component, descriptor, viewComposite);
						swtViewHandler.initView(component, view);
					} catch (ViewConstructionException e) {
						EEFLogger logger = swtViewHandler.getLogger();
						logger.logError("org.eclipse.emf.eef.runtime.ui.swt", "An error occured during view creation.", e);
					}
					item.setControl(viewComposite);
				}
				i++;
			}
			if (dynamicTabHeader ) {
				if (folder.getItemCount() > 1) {
					folder.setTabHeight(SWT.DEFAULT);
				} else {
					folder.setTabHeight(0);
				}
			}
			if (selectionIndex >= 0) {
				folder.setSelection(selectionIndex);
			} else {
				folder.setSelection(0);
			}
		}
	}

	/**
	 * @param viewDescriptor
	 * @return
	 */
	private boolean isFiltered(org.eclipse.emf.eef.views.View viewDescriptor) {
		if (filters != null) {
			for (EEFViewerFilter filter : filters) {
				if (filter instanceof ViewFilter) {
					if (!filter.select(this, viewDescriptor)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Clear the viewDescriptors of this viewer.
	 */
	public void clear() {
		if (folder.getItemCount() > 0) {
			CTabItem[] items = folder.getItems();
			for (int i = 0; i < items.length; i++) {
				CTabItem cTabItem = items[i];

				if (!cTabItem.getControl().isDisposed())
					cTabItem.getControl().dispose();
				if (!cTabItem.isDisposed())
					cTabItem.dispose();
			}
		}
		PropertiesEditingComponent editingComponent = input.getEditingComponent();
		List<Object> views = editingComponent.getViews();
		for (Object view : views) {
			View descriptor = editingComponent.getDescriptorForView(view);
			if (descriptor != null) {
				ViewHandler<?> viewHandler = input.getViewHandlerProvider().getViewHandler(descriptor);
				viewHandler.dispose(editingComponent, view);
			}
		}
	}

	/**
	 * @return the layoutData of the control.
	 */
	public Object getLayoutData() {
		return control.getLayoutData();
	}

	/**
	 * @param layoutData the layoutData to set
	 */
	public void setLayoutData(Object layoutData) {
		control.setLayoutData(layoutData);
	}
	
	/**
	 * Add a listener to the component of the context.
	 * @param listener {@link PropertiesEditingListener} to add.
	 */
	public void addEditingListener(PropertiesEditingListener listener) {
		((EEFContentProvider)getContentProvider()).addEditingListener(listener);
	}

	/**
	 * Remove a listener to the component of the context.
	 * @param listener {@link PropertiesEditingListener} to remove.
	 */
	public void removeEditingListener(PropertiesEditingListener listener) {
		((EEFContentProvider)getContentProvider()).removeEditingListener(listener);
	}

	/**
	 * Adds the given filter to this viewer, and triggers refiltering and
	 * resorting of the elements. If you want to add more than one filter
	 * consider using {@link StructuredViewer#setFilters(ViewerFilter[])}.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see StructuredViewer#setFilters(ViewerFilter[])
	 */
	public void addFilter(EEFViewerFilter filter) {
		if (filters == null) {
			filters = Lists.newArrayList();
		}
		filters.add(filter);
		refresh();
	}

	/**
	 * Removes the given filter from this viewer, and triggers refiltering and
	 * resorting of the elements if required. Has no effect if the identical
	 * filter is not registered. If you want to remove more than one filter
	 * consider using {@link StructuredViewer#setFilters(ViewerFilter[])}.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see StructuredViewer#setFilters(ViewerFilter[])
	 */
	public void removeFilter(EEFViewerFilter filter) {
		Assert.isNotNull(filter);
		if (filters != null) {
			// Note: can't use List.remove(Object). Use identity comparison
			// instead.
			for (Iterator<EEFViewerFilter> i = filters.iterator(); i.hasNext();) {
				Object o = i.next();
				if (o == filter) {
					i.remove();
					refresh();
					if (filters.size() == 0) {
						filters = null;
					}
					return;
				}
			}
		}
	}

	/**
	 * Sets the filters, replacing any previous filters, and triggers
	 * refiltering and resorting of the elements.
	 * 
	 * @param filters an array of viewer filters
	 */
	public void setFilters(EEFViewerFilter[] filters) {
		if (filters.length == 0) {
			resetFilters();
		} else {
			this.filters = Lists.newArrayList(filters);
			refresh();
		}
	}
	
	/**
	 * Discards this viewer's filters and triggers refiltering and resorting of
	 * the elements.
	 */
	public void resetFilters() {
		if (filters != null) {
			filters = null;
			refresh();
		}
	}

	/*
	 * =============================== Selection management ================================
	 */
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#getSelection()
	 */
	public ISelection getSelection() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#setSelection(org.eclipse.jface.viewers.ISelection, boolean)
	 */
	public void setSelection(ISelection selection, boolean reveal) {

	}

	/*
	 * ================================= Scroll management =================================
	 */
	/**
	 * update the scroll composite size
	 */
	private void updateScrollSize() {
		if (folder.getSelection() != null && folder.getSelection().getControl() != null) {
			scroll.setMinSize(folder.getSelection().getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT));
		}
	}

	private class ItemListener implements ControlListener {

		Control listenedControl = null;

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
		 */
		public void controlMoved(ControlEvent e) {
			// Nothing to do

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
		 */
		public void controlResized(ControlEvent e) {
			updateScrollSize();
		}

		/**
		 * update listeners managing scroll composite size
		 */
		public void updateControlListener() {
			removeControlListener();
			Control control2 = folder.getSelection().getControl();
			if (control2 != null) {
				listener.listenedControl = control2;
				control2.addControlListener(listener);
			}

		}

		public void removeControlListener() {
			if (listener.listenedControl != null && !listener.listenedControl.isDisposed()) {
				listener.listenedControl.removeControlListener(listener);
			}
		}

	}

}
