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
package org.eclipse.emf.eef.runtime.editingModel.presentation;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Wizard page to choose the model.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ModelToChoosePage extends WizardPage {

	private Text text;
	private Button btnFromWorkspace;
	private Button btnFromFileSystem;
	/**
	 * Model chosen URI .
	 */
	private URI createURI;
	private IFile selectedModel;

	/**
	 * Default constructor.
	 * 
	 * @param selection
	 * 
	 * @param pageName
	 */
	protected ModelToChoosePage(IStructuredSelection selection) {
		super("Model selection");
		setTitle("Select a model.");
		setDescription("Select the model.");
		if (selection.getFirstElement() instanceof IFile) {
			selectedModel = (IFile) selection.getFirstElement();
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		GridData gd_composite = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_composite.widthHint = 568;
		composite.setLayoutData(gd_composite);

		Label label = new Label(composite, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Model:");

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				createURI = URI.createURI(text.getText());
				setPageComplete(isPageComplete());
			}
		});
		if (selectedModel != null) {
			createURI = URI.createPlatformResourceURI(selectedModel.getFullPath().toString(), true);
			text.setText(createURI.toString());
		}

		Composite buttonsComposite = new Composite(container, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(2, false));
		buttonsComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		btnFromWorkspace = new Button(buttonsComposite, SWT.NONE);
		btnFromWorkspace.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		btnFromWorkspace.setText("From workspace...");

		btnFromWorkspace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseWorkspace();
			}
		});

		btnFromFileSystem = new Button(buttonsComposite, SWT.NONE);
		btnFromFileSystem.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		btnFromFileSystem.setText("From file system...");

		btnFromFileSystem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseFileSystem();
			}
		});

		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

	}

	/**
	 * @return the URI
	 */
	public URI getURI() {
		return createURI;
	}

	/**
	 * Open file dialog on Browse file system.
	 */
	protected void handleBrowseFileSystem() {
		FileDialog fileDialog = new FileDialog(getShell());
		fileDialog.open();

		String filterPath = fileDialog.getFilterPath();
		String fileName = fileDialog.getFileName();
		if (fileName != null) {
			createURI = URI.createFileURI(filterPath + File.separator + fileName);
			text.setText(createURI.toString());
		}

	}

	/**
	 * Open workspace resource dialog on Browse workspace.
	 */
	protected void handleBrowseWorkspace() {
		IFile file = null;
		IFile[] files = WorkspaceResourceDialog.openFileSelection(getShell(), null, null, false, null, null);
		if (files.length != 0) {
			file = files[0];
		}

		if (file != null) {
			createURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			text.setText(createURI.toString());
		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		return isValidURI();
	}

	// @Override
	// public boolean canFlipToNextPage() {
	// return isValidURI() && getNextPage() != null;
	// }

	/**
	 * @return is the text URI is valid.
	 */
	protected boolean isValidURI() {
		if (createURI == null || createURI.isEmpty()) {
			// setErrorMessage("Chosen model is empty.");
			return true;
		}
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		try {
			Resource resource = resourceSet.getResource(createURI, true);
			if (resource == null) {
				setErrorMessage("Resource '" + createURI.toString() + "' does not exist.");
				return false;
			}
			resource = null;
		} catch (RuntimeException exception) {
			setErrorMessage("Resource '" + createURI.toString() + "' does not exist.");
			return false;
			// FilterEditorPlugin.INSTANCE.log(exception);
		}
		resourceSet = null;
		setErrorMessage(null);
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		createURI = null;
		super.dispose();
	}

}
