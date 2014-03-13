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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.LoadResourceAction.LoadResourceDialog;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction.ExtendedLoadResourceDialog;
import org.eclipse.jface.layout.PixelConverter;
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
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.google.common.base.Strings;

/**
 * Wizard page to choose the model or metamodel to initialize in editing model.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ModelToChoosePage extends WizardPage {

	private Text text;
	private Text text2;
	private Button btnFromFileSystem;
	private Button btnFromFileSystem2;
	private Button fromModelRadio;
	private Button fromMetamodelRadio;

	/**
	 * Model chosen URI .
	 */
	private URI createURI;
	private IFile selectedModel;
	private boolean isModel = true;
	private EditingDomain editingDomain;

	/**
	 * Default constructor.
	 * 
	 * @param selection
	 * 
	 * @param pageName
	 */
	protected ModelToChoosePage(IStructuredSelection selection) {
		super("Model selection");
		setTitle("Select a model or a metamodel.");
		setDescription("Initialize editing model with chosen model.");
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
		Composite projectGroup = new Composite(parent, SWT.NONE);
		setControl(projectGroup);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = false;
		layout.marginWidth = 0;
		projectGroup.setLayout(layout);
		projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// radio button
		fromModelRadio = new Button(projectGroup, SWT.RADIO);
		fromModelRadio.setText("Select model:");
		this.text = new Text(projectGroup, SWT.BORDER);

		GridData directoryPathData = new GridData(SWT.FILL, SWT.NONE, true, false);
		directoryPathData.widthHint = new PixelConverter(text).convertWidthInCharsToPixels(25);
		text.setLayoutData(directoryPathData);

		// browse button
		btnFromFileSystem = new Button(projectGroup, SWT.PUSH);
		btnFromFileSystem.setText("Browse");
		setButtonLayoutData(btnFromFileSystem);

		// radio button
		fromMetamodelRadio = new Button(projectGroup, SWT.RADIO);
		fromMetamodelRadio.setText("Select metamodel:");
		text2 = new Text(projectGroup, SWT.BORDER);

		GridData archivePathData = new GridData(SWT.FILL, SWT.NONE, true, false);
		archivePathData.widthHint = new PixelConverter(text2).convertWidthInCharsToPixels(25);
		text2.setLayoutData(archivePathData);
		btnFromFileSystem2 = new Button(projectGroup, SWT.PUSH);
		btnFromFileSystem2.setText("Browse");
		setButtonLayoutData(btnFromFileSystem2);

		// init selection and enable
		fromModelRadio.setSelection(true);
		text2.setEnabled(false);
		btnFromFileSystem2.setEnabled(false);

		// add listener on buttons
		btnFromFileSystem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseFileSystem();
				if (createURI != null) {
					text.setText(createURI.toString());
				}
			}
		});

		btnFromFileSystem2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseFileSystem2();
				if (createURI != null) {
					text2.setText(createURI.toString());
				}
			}
		});

		fromModelRadio.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				modelRadioSelected();
			}
		});

		fromMetamodelRadio.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				metamodelRadioSelected();
			}
		});

		text.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				createURI = URI.createURI(text.getText());
				setPageComplete(isPageComplete());
			}
		});

		text2.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				createURI = URI.createURI(text2.getText());
				setPageComplete(isPageComplete());
			}
		});

		// init text model
		if (selectedModel != null) {
			createURI = URI.createPlatformResourceURI(selectedModel.getFullPath().toString(), true);
			if (isModel) {
				text.setText(createURI.toString());
			} else {
				text2.setText(createURI.toString());
			}
		}

		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

	}

	private void metamodelRadioSelected() {
		if (fromMetamodelRadio.getSelection()) {
			text.setEnabled(false);
			btnFromFileSystem.setEnabled(false);
			text2.setEnabled(true);
			btnFromFileSystem2.setEnabled(true);
			text2.setFocus();
			isModel = false;
		}
	}

	private void modelRadioSelected() {
		if (fromModelRadio.getSelection()) {
			text.setEnabled(true);
			btnFromFileSystem.setEnabled(true);
			text2.setEnabled(false);
			btnFromFileSystem2.setEnabled(false);
			text.setFocus();
			isModel = true;
		}
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
		LoadResourceDialog loadResourceDialog = new LoadResourceDialog(getShell());
		loadResourceDialog.open();

		if (!Strings.isNullOrEmpty(loadResourceDialog.getURIText()) && !loadResourceDialog.getURIs().isEmpty()) {
			createURI = loadResourceDialog.getURIs().get(0);
		}

	}

	/**
	 * Open file dialog on Browse file system.
	 */
	protected void handleBrowseFileSystem2() {
		ExtendedLoadResourceDialog loadResourceDialog = new ExtendedLoadResourceDialog(getShell(), editingDomain);
		loadResourceDialog.open();

		if (!Strings.isNullOrEmpty(loadResourceDialog.getURIText()) && !loadResourceDialog.getURIs().isEmpty()) {
			createURI = loadResourceDialog.getURIs().get(0);
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

	/**
	 * @return the isModel
	 */
	public boolean isModel() {
		return isModel;
	}

	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

}
