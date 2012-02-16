/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.viewer;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.EditingListener;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.swt.SWTViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelection;
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

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * TODO: ADD PartFilter management
 */
public class EEFViewer extends ContentViewer {

	private Composite control;
	protected CTabFolder folder;
	protected ScrolledComposite scroll;
	private ItemListener listener;
	private boolean dynamicTabHeader = true;

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
		refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.Viewer#refresh()
	 */
	public void refresh() {
		clear();
		PropertiesEditingContext context = ((EEFContentProvider) getContentProvider()).getContext();
		List<ViewHandler<?>> viewHandlers = context.getEditingComponent().getViewHandlers();
		PropertiesEditingComponent component = context.getEditingComponent();
		int i = 1;
		for (ViewHandler<?> handler : viewHandlers) {
			if (handler instanceof PropertiesEditingViewHandler) {
				try {
					PropertiesEditingViewHandler propertiesEditingViewHandler = (PropertiesEditingViewHandler) handler;
					CTabItem item = new CTabItem(folder, SWT.NONE);
					item.setText(propertiesEditingViewHandler.getViewDescriptor().getName());
					PropertiesEditingView view = propertiesEditingViewHandler.createView(component, folder);
					view.getContents().setLayoutData(new GridData(GridData.FILL_BOTH));
					handler.initView(component);
					item.setControl(view.getContents());
				} catch (ViewConstructionException e) {
					EEFRuntimeUI.getPlugin().getLog().log(new Status(IStatus.ERROR, EEFRuntimeUI.PLUGIN_ID, "An error occured during view creation.", e));
				}
			} else if (handler instanceof SWTViewHandler) {
				SWTViewHandler swtViewHandler = (SWTViewHandler)handler;
				CTabItem item = new CTabItem(folder, SWT.CLOSE);
				item.setText("View " + i);
				Composite viewComposite = new Composite(folder, SWT.NONE);
				viewComposite.setLayout(new FillLayout());
				try {
					swtViewHandler.createView(viewComposite);
					handler.initView(component);
				} catch (ViewConstructionException e) {
					EEFRuntimeUI.getPlugin().getLog().log(new Status(IStatus.ERROR, EEFRuntimeUI.PLUGIN_ID, "An error occured during view creation.", e));
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
		folder.setSelection(0);
	}

	/**
	 * Clear the views of this viewer.
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
	 * @param listener {@link EditingListener} to add.
	 */
	public void addEditingListener(EditingListener listener) {
		//TODO: to protect
		((EEFContentProvider)getContentProvider()).addEditingListener(listener);
	}

	/**
	 * Remove a listener to the component of the context.
	 * @param listener {@link EditingListener} to remove.
	 */
	public void removeEditingListener(EditingListener listener) {
		((EEFContentProvider)getContentProvider()).removeEditingListener(listener);
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
