/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.widgets;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

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
	
	/**
	 * @param parent
	 */
	public EEFSelectionDialog(Shell parent, boolean multi) {
		super(parent);
		this.multi = multi;
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
		selectionViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		return control;
	}

	/**
	 * @param adapterFactory the adapterFactory to set
	 */
	public void setAdapterFactory(AdapterFactory adapterFactory) {
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @return the contentProvider
	 */
	public IContentProvider getContentProvider() {
		if (contentProvider == null) {
			if (adapterFactory == null) {
				adapterFactory = EEFRuntimeUI.getPlugin().getRegistryAdapterFactory();
			}
			contentProvider = new AdapterFactoryContentProvider(adapterFactory);
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
			if (adapterFactory == null) {
				adapterFactory = EEFRuntimeUI.getPlugin().getRegistryAdapterFactory();
			}
			labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
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

}
