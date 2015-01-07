/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.editor.internal.actions;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.action.CreateDynamicInstanceAction;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.DynamicModelWizard;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.editingModel.presentation.EditingModelEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 * Create Dynamic instance action for Editing model editor. Load the new
 * instance in it.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class ExtendedCreateDynamicInstanceAction extends CreateDynamicInstanceAction {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.ecore.action.CreateDynamicInstanceAction#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		URI uri = eClass.eResource().getURI();
		IStructuredSelection selection = StructuredSelection.EMPTY;
		if (uri != null && uri.isHierarchical()) {
			if (uri.isRelative() || (uri = uri.deresolve(PLATFORM_RESOURCE)).isRelative()) {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toString()));
				if (file.exists()) {
					selection = new StructuredSelection(file);
				}
			}
		}

		DynamicModelWizard dynamicModelWizard = new DynamicModelWizard(eClass) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.ecore.presentation.DynamicModelWizard#performFinish()
			 */
			@Override
			public boolean performFinish() {
				try {
					// Remember the file.
					//
					final IFile modelFile = getModelFile();
					// Get the URI of the model file.
					//
					final URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);

					// Do the work within an operation.
					//
					WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
						@Override
						protected void execute(IProgressMonitor progressMonitor) {
							try {
								// Create a resource set
								//
								ResourceSet resourceSet = new ResourceSetImpl();
								resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap());

								// Create a resource for this file.
								//
								Resource resource = resourceSet.createResource(fileURI);

								// Add the initial model object to the contents.
								//
								EObject rootObject = createInitialModel();
								if (rootObject != null) {
									resource.getContents().add(rootObject);
								}

								// Save the contents of the resource to the file
								// system.
								//
								Map<Object, Object> options = new HashMap<Object, Object>();
								options.put("SCHEMA_LOCATION", Boolean.TRUE);
								options.put("LINE_WIDTH", 80);
								resource.save(options);
							} catch (Exception exception) {
								exception.printStackTrace();
							} finally {
								progressMonitor.done();
							}
						}
					};

					getContainer().run(false, false, operation);

					// Select the new file resource in the current view.
					//
					IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
					IWorkbenchPage page = workbenchWindow.getActivePage();
					final IWorkbenchPart activePart = page.getActivePart();
					if (activePart instanceof ISetSelectionTarget) {
						final ISelection targetSelection = new StructuredSelection(modelFile);
						getShell().getDisplay().asyncExec(new Runnable() {
							public void run() {
								((ISetSelectionTarget) activePart).selectReveal(targetSelection);
							}
						});
					}

					// Load the new file in EEF Editor.
					if (activePart instanceof EditingModelEditor) {
						((EditingModelEditor) activePart).getEditingDomainForOtherModel().getResourceSet().getResource(fileURI, true);
					}

					return true;
				} catch (Exception exception) {
					exception.printStackTrace();
					return false;
				}
			}

		};
		dynamicModelWizard.init(PlatformUI.getWorkbench(), selection);
		WizardDialog wizardDialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), dynamicModelWizard);

		wizardDialog.open();
	}

	/**
	 * Create a new model.
	 */
	EObject createInitialModel() {
		return EcoreUtil.create(eClass);
	}

}
