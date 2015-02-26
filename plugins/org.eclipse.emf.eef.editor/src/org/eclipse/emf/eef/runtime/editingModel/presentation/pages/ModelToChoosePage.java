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
package org.eclipse.emf.eef.runtime.editingModel.presentation.pages;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
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

	public static final String DEFAULT_MODEL_NAME = "default model name";
	private Button fromMetamodelRadio;
	private Text modelPath;
	private Button browseForModel;

	private Button fromModelRadio;
	private Text metamodelPath;
	private Button browseForMetamodel;

	private Button initModelCheckbox;

	private List<ModelInitializationChangeListener> listeners = new ArrayList<ModelToChoosePage.ModelInitializationChangeListener>();

	/**
	 * Model chosen URI .
	 */
	private URI selectedFileURI;
	private IFile selectedModel;
	private boolean initModel = false;
	private boolean isModel = false;

	private EditingDomain editingDomain;

	/**
	 * Default constructor.
	 * 
	 * @param selection
	 * 
	 * @param pageName
	 */
	public ModelToChoosePage(IStructuredSelection selection) {
		super("Model selection");
		setTitle("Select a model or a metamodel.");
		setDescription("Initialize editing model with chosen model.");
		if (selection.getFirstElement() instanceof IFile) {
			selectedModel = (IFile) selection.getFirstElement();
		}
	}

	/**
	 * @param editingDomain
	 */
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * @param listener
	 */
	public void addModelInitializationListener(ModelInitializationChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * @param listener
	 */
	public void removeModelInitializationListener(ModelInitializationChangeListener listener) {
		listeners.remove(listener);
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

		// ------- Metamodel Option -------
		fromMetamodelRadio = new Button(projectGroup, SWT.RADIO);
		fromMetamodelRadio.setText("Select metamodel:");
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

		metamodelPath = new Text(projectGroup, SWT.BORDER);
		GridData archivePathData = new GridData(SWT.FILL, SWT.NONE, true, false);
		archivePathData.widthHint = new PixelConverter(metamodelPath).convertWidthInCharsToPixels(25);
		metamodelPath.setLayoutData(archivePathData);
		metamodelPath.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				selectedFileURI = URI.createURI(metamodelPath.getText());
				setPageComplete(isPageComplete());
				String modelName = DEFAULT_MODEL_NAME;
				if (getURI() != null) {
					modelName = getPackageURI(getURI());
				}
				notifyModelIntializationChange(modelName);

			}
		});

		browseForMetamodel = new Button(projectGroup, SWT.PUSH);
		browseForMetamodel.setText("Browse");
		setButtonLayoutData(browseForMetamodel);
		browseForMetamodel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseFileSystem2();
				if (selectedFileURI != null) {
					metamodelPath.setText(selectedFileURI.toString());
				}
			}
		});

		// ------- Model Option -------
		fromModelRadio = new Button(projectGroup, SWT.RADIO);
		fromModelRadio.setText("Select model:");
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

		this.modelPath = new Text(projectGroup, SWT.BORDER);
		GridData directoryPathData = new GridData(SWT.FILL, SWT.NONE, true, false);
		directoryPathData.widthHint = new PixelConverter(modelPath).convertWidthInCharsToPixels(25);
		modelPath.setLayoutData(directoryPathData);
		modelPath.addListener(SWT.Modify, new Listener() {

			public void handleEvent(Event event) {
				selectedFileURI = URI.createURI(modelPath.getText());
				setPageComplete(isPageComplete());
				String modelName = DEFAULT_MODEL_NAME;
				if (getURI() != null && isValidURI()) {
					modelName = getMetamodelePackageURI(getURI());
				}
				notifyModelIntializationChange(modelName);
			}

		});

		browseForModel = new Button(projectGroup, SWT.PUSH);
		browseForModel.setText("Browse");
		setButtonLayoutData(browseForModel);
		browseForModel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				handleBrowseFileSystem();
				if (selectedFileURI != null) {
					modelPath.setText(selectedFileURI.toString());
				}
			}
		});

		// initialize editing model
		initModelCheckbox = new Button(projectGroup, SWT.CHECK);
		initModelCheckbox.setText("Initialize EEF settings model");
		initModelCheckbox.addSelectionListener(new SelectionAdapter() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse
			 * .swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				initModelCheckboxSelected();
			}

		});
		initModelCheckbox.setSelection(true);
		initModelCheckboxSelected();

		// init text model
		if (selectedModel != null) {
			selectedFileURI = URI.createPlatformResourceURI(selectedModel.getFullPath().toString(), true);
			if ("ecore".equals(selectedFileURI.fileExtension())) {
				metamodelPath.setText(selectedFileURI.toString());
				fromMetamodelRadio.setSelection(true);
				metamodelRadioSelected();
			} else {
				modelPath.setText(selectedFileURI.toString());
				fromModelRadio.setSelection(true);
				modelRadioSelected();
			}
		} else {
			fromMetamodelRadio.setSelection(true);
			metamodelRadioSelected();
		}

		setPageComplete(false);
		setErrorMessage(null);
		setMessage(null);

	}

	/**
	 * @param modelName
	 * @return model name
	 */
	public String getMetamodelePackageURI(URI uri) {
		String modelName = "";
		ResourceSet rs = new ResourceSetImpl();
		Resource modelResource = rs.getResource(uri, true);
		if (modelResource != null && !modelResource.getContents().isEmpty()) {
			EObject root = modelResource.getContents().get(0);
			if (root != null) {
				modelName = root.eClass().getEPackage().getName();
			}
		}
		if (Strings.isNullOrEmpty(modelName)) {
			modelName = DEFAULT_MODEL_NAME;
		}
		return modelName;
	}

	/**
	 * @param modelName
	 * @return model name
	 */
	public String getPackageURI(URI uri) {
		String modelName = "";
		ResourceSet rs = new ResourceSetImpl();
		Resource modelResource = rs.getResource(uri, true);
		if (modelResource != null && !modelResource.getContents().isEmpty()) {
			EObject root = modelResource.getContents().get(0);
			if (root instanceof EPackage) {
				modelName = ((EPackage) root).getName();
			}
		}
		if (Strings.isNullOrEmpty(modelName)) {
			modelName = DEFAULT_MODEL_NAME;
		}
		return modelName;
	}

	/**
	 * @return the initKind
	 */
	public boolean initModel() {
		return initModel;
	}

	/**
	 * @return the URI
	 */
	public URI getURI() {
		return selectedFileURI;
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
	public boolean isValidURI() {
		if (selectedFileURI == null || selectedFileURI.isEmpty()) {
			return true;
		}
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		try {
			Resource resource = resourceSet.getResource(selectedFileURI, true);
			if (resource == null) {
				setErrorMessage("Resource '" + selectedFileURI.toString() + "' does not exist.");
				return false;
			}
			resource = null;
		} catch (RuntimeException exception) {
			setErrorMessage("Pb with the resource '" + selectedFileURI.toString() + "':" + exception.getMessage());
			return false;
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
		selectedFileURI = null;
		super.dispose();
	}

	private void metamodelRadioSelected() {
		if (fromMetamodelRadio.getSelection()) {
			modelPath.setEnabled(false);
			browseForModel.setEnabled(false);
			metamodelPath.setEnabled(true);
			browseForMetamodel.setEnabled(true);
			metamodelPath.setFocus();
			String modelName = DEFAULT_MODEL_NAME;
			if (getURI() != null) {
				modelName = getPackageURI(getURI());
			}

			isModel = false;
			notifyModelIntializationChange(modelName);
		}
	}

	private void modelRadioSelected() {
		if (fromModelRadio.getSelection()) {
			modelPath.setEnabled(true);
			browseForModel.setEnabled(true);
			metamodelPath.setEnabled(false);
			browseForMetamodel.setEnabled(false);
			modelPath.setFocus();
			String modelName = DEFAULT_MODEL_NAME;
			if (getURI() != null && isValidURI()) {
				modelName = getMetamodelePackageURI(getURI());
			}
			isModel = true;
			notifyModelIntializationChange(modelName);
		}
	}

	private void initModelCheckboxSelected() {
		initModel = !initModel;
	}

	/**
	 * Open file dialog on Browse file system.
	 */
	private void handleBrowseFileSystem() {
		LoadResourceDialog loadResourceDialog = new LoadResourceDialog(getShell());
		loadResourceDialog.open();

		if (!Strings.isNullOrEmpty(loadResourceDialog.getURIText()) && !loadResourceDialog.getURIs().isEmpty()) {
			selectedFileURI = loadResourceDialog.getURIs().get(0);
		}

	}

	/**
	 * Open file dialog on Browse file system.
	 */
	private void handleBrowseFileSystem2() {
		ExtendedLoadResourceDialog loadResourceDialog = new ExtendedLoadResourceDialog(getShell(), editingDomain);
		loadResourceDialog.open();

		if (!Strings.isNullOrEmpty(loadResourceDialog.getURIText()) && !loadResourceDialog.getURIs().isEmpty()) {
			selectedFileURI = loadResourceDialog.getURIs().get(0);
		}

	}

	/**
	 * @param modelName
	 */
	private void notifyModelIntializationChange(String modelName) {
		for (ModelInitializationChangeListener listener : listeners) {
			listener.modelInitializationChanged(modelName);
		}
	}

	/**
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	public interface ModelInitializationChangeListener {

		void modelInitializationChanged(String modelName);

	}

	/**
	 * @return the isModel
	 */
	public boolean isModel() {
		return isModel;
	}

}
