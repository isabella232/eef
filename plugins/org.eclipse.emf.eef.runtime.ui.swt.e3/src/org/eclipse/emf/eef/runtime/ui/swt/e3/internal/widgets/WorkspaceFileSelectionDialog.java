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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets;

import java.util.List;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TrayDialog;
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
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class WorkspaceFileSelectionDialog extends TrayDialog {

	private boolean isMulti;
	private Object input;
	private List<Object> selection;

	/**
	 * @param shell
	 * @param isMulti
	 */
	public WorkspaceFileSelectionDialog(Shell shell, boolean isMulti) {
		super(shell);
		this.isMulti = isMulti;
		this.input = null;
		selection = Lists.newArrayList();
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(Object input) {
		this.input = input;
	}

	/**
	 * @return the selection
	 */
	public List<Object> getResult() {
		return selection;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setSize(UIConstants.EEF_SELECTION_DIALOG_WIDTH, UIConstants.EEF_SELECTION_DIALOG_HEIGHT);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout(1, false));
		Label title = new Label(container, SWT.NONE);
		title.setText("Select the model file you want to load:");
		int styles = SWT.V_SCROLL | SWT.H_SCROLL;
		if (isMulti) {
			styles |= SWT.MULTI;
		}
		TreeViewer viewer = new TreeViewer(container, styles);
		viewer.setContentProvider(new WorkbenchContentProvider()); 
		viewer.setLabelProvider(new WorkbenchLabelProvider());
		viewer.setInput(input);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			

			@SuppressWarnings("unchecked")
			public void selectionChanged(SelectionChangedEvent event) {
				WorkspaceFileSelectionDialog.this.selection = ((StructuredSelection)event.getSelection()).toList();
			}
		});
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		return container;
	}

	
	
}
