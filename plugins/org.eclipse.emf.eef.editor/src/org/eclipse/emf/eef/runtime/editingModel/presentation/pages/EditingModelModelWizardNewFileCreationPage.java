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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.runtime.editingModel.presentation.EditingModelModelWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;


/**
 * This is the one page of the wizard. 
 * 
 */
public class EditingModelModelWizardNewFileCreationPage extends WizardNewFileCreationPage {
	
	/**
	 * Pass in the selection.
	 * 
	 */
	public EditingModelModelWizardNewFileCreationPage(String pageId, IStructuredSelection selection) {
		super(pageId, selection);
	}

	/**
	 * The framework calls this to see if the file is correct.
	 * 
	 */
	@Override
	protected boolean validatePage() {
		if (super.validatePage()) {
			String extension = new Path(getFileName()).getFileExtension();
			if (extension == null || !EditingModelModelWizard.FILE_EXTENSIONS.contains(extension)) {
				String key = EditingModelModelWizard.FILE_EXTENSIONS.size() > 1 ? "_WARN_FilenameExtensions" : "_WARN_FilenameExtension";
				setErrorMessage(EditingModelEditPlugin.INSTANCE.getString(key, new Object[] { EditingModelModelWizard.FORMATTED_FILE_EXTENSIONS }));
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public IFile getModelFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
	}
}