/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.samples.gettingstarted.internal.actions.showingmodel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ModelElementSelectionComposite extends Composite {

	private TreeViewer viewer;
	private AdapterFactory adapterFactory;
	private ResourceSet resourceSet;
	private Label status;

	public ModelElementSelectionComposite(Composite parent, int style, AdapterFactory adapterFactory, ResourceSet resourceSet) {
		super(parent, style);
		this.resourceSet = resourceSet;
		this.adapterFactory = adapterFactory;
		createContents(parent);
	}

	private void createContents(Composite parent) {
		setLayout(new GridLayout(2, false));
		Label label = new Label(this, SWT.NONE);
		label.setText("Select model file in the workspace:");
		GridData labelData = new GridData(GridData.FILL_HORIZONTAL);
		labelData.horizontalSpan = 2;
		label.setLayoutData(labelData);
		final Text modelPath = new Text(this, SWT.BORDER);
		modelPath.setEditable(false);
		modelPath.setEnabled(false);
		modelPath.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button modelSelection = new Button(this, SWT.PUSH);
		modelSelection.setText("Select");
		modelSelection.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				IFile selectModelFile = selectModelFile();
				if (selectModelFile != null) {
					modelPath.setText(selectModelFile.getFullPath().toString());
					tryToLoadModel(selectModelFile);
				} else {
					modelPath.setText("");
				}
			}

		});
		Label separator = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData separatorData = new GridData(GridData.FILL_HORIZONTAL);
		separatorData.horizontalSpan = 2;
		separator.setLayoutData(separatorData);
		Label label2 = new Label(this, SWT.NONE);
		label2.setText("Select model element:");
		label2.setLayoutData(labelData);
		viewer = new TreeViewer(this);
		GridData treeData = new GridData(GridData.FILL_BOTH);
		treeData.horizontalSpan = 2;
		viewer.getControl().setLayoutData(treeData);
		viewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		viewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		status = new Label(this, SWT.NONE);
		status.setLayoutData(labelData);
	}	


	protected void tryToLoadModel(IFile selectModelFile) {
		URI uri = URI.createPlatformResourceURI(selectModelFile.getFullPath().toOSString(), true);
		try {
			Resource resource = resourceSet.getResource(uri, true);
			viewer.setInput(resource);
			status.setText("OK!");
		} catch (Exception e) {
			viewer.setInput(new Object());
			status.setText("Unable to load the model. Please choose another file");
			
		}
	}

	private IFile selectModelFile() {
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setAllowMultiple(false);
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (dialog.open() == Window.OK) {
			Object firstResult = dialog.getFirstResult();
			if (firstResult instanceof IFile) {
				return (IFile) firstResult;
			}
		} 
		return null;
	}


}
