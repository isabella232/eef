/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.widgets;

import java.util.Collection;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFSelectionDialog extends Dialog {

	private String title;
	private TreeViewer selectionViewer;
	private boolean multi;
	
	private AdapterFactory adapterFactory;
	private IContentProvider contentProvider;
	private IBaseLabelProvider labelProvider;
	private Object input;
	private Object selection;
	private Collection<ViewerFilter> filters;
	private EditUIProvidersFactory providersFactory;
	
	/**
	 * @param parent
	 */
	public EEFSelectionDialog(Shell parent, boolean multi) {
		super(parent);
		this.multi = multi;
		this.filters = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		if (title != null) {
			newShell.setText(title);
		}
		newShell.setSize(UIConstants.EEF_SELECTION_DIALOG_WIDTH, UIConstants.EEF_SELECTION_DIALOG_HEIGHT);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(Object input) {
		this.input = input;
	}

	/**
	 * @return the input
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	protected boolean isResizable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite control = new Composite(parent, SWT.NONE);
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		control.setLayout(new GridLayout());
		Label message = new Label(control, SWT.NONE);
		message.setText("Select the element(s) to process:");
		int style = SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
		if (multi) {
			style |= SWT.MULTI;
		}
		selectionViewer = new TreeViewer(control, style);
		selectionViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		
		for (ViewerFilter filter : filters) {
			selectionViewer.addFilter(filter);
		}
		
		selectionViewer.setContentProvider(getContentProvider());
		selectionViewer.setLabelProvider(getLabelProvider());
		selectionViewer.setInput(input);
		selectionViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			

			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection sSel = (StructuredSelection) selectionViewer.getSelection();
				if (sSel.size() == 0) {
					EEFSelectionDialog.this.selection = null;
				} else if (sSel.size() == 1) {
					EEFSelectionDialog.this.selection = sSel.getFirstElement();					
				} else {
					EEFSelectionDialog.this.selection = sSel.toList();
				}
			}
		});
		
		return control;
	}

	/**
	 * @param adapterFactory the adapterFactory to set
	 */
	public void setAdapterFactory(AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}
	
	/**
	 * @param providersFactory the {@link EditUIProvidersFactory} to use in this dialog.
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory providersFactory) {
		this.providersFactory = providersFactory;
	}

	/**
	 * @return the contentProvider
	 * TODO: need a real improvement
	 */
	public IContentProvider getContentProvider() {
		if (contentProvider == null && providersFactory != null) {
			contentProvider = providersFactory.createContentProvider(adapterFactory);
		}
		return contentProvider;
	}

	/**
	 * @param contentProvider the contentProvider to set
	 */
	public void setContentProvider(IContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * @return the labelProvider
	 */
	public IBaseLabelProvider getLabelProvider() {
		if (labelProvider == null) {
			labelProvider = providersFactory.createLabelProvider(adapterFactory);
		}
		return labelProvider;
	}

	/**
	 * @param labelProvider the labelProvider to set
	 */
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * @return the selection
	 */
	public Object getSelection() {
		return selection;
	}

	/**
	 * @param choiceOfValuesFilter
	 */
	public void addFilter(ViewerFilter filter) {
		this.filters.add(filter);
	}

}
