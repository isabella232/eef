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
package org.eclipse.emf.eef.editor;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.pde.core.IBaseModel;
import org.eclipse.pde.core.build.IBuildEntry;
import org.eclipse.pde.core.build.IBuildModel;
import org.eclipse.pde.core.build.IBuildModelFactory;
import org.eclipse.pde.internal.core.build.WorkspaceBuildModel;
import org.eclipse.pde.internal.core.ibundle.IBundleModel;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.internal.core.natures.PDE;
import org.eclipse.pde.internal.core.project.PDEProject;
import org.eclipse.pde.internal.ds.core.IDSComponent;
import org.eclipse.pde.internal.ds.core.IDSDocumentFactory;
import org.eclipse.pde.internal.ds.core.IDSProperty;
import org.eclipse.pde.internal.ds.core.IDSProvide;
import org.eclipse.pde.internal.ds.core.IDSReference;
import org.eclipse.pde.internal.ds.core.IDSService;
import org.eclipse.pde.internal.ds.ui.wizards.DSCreationOperation;
import org.eclipse.pde.internal.ui.util.ModelModification;
import org.eclipse.pde.internal.ui.util.PDEModelUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

/**
 * PDE helper, add EEFBindingSettings service and manifest/OsGi configurations.
 * 
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
@SuppressWarnings("restriction")
public class PDEHelper {

	protected static final String SET_EMF_SERVICE_PROVIDER = "setEMFServiceProvider";
	protected static final String EMF_SERVICE_PROVIDER = "org.eclipse.emf.eef.runtime.util.EMFServiceProvider";
	protected static final String EEF_BINDING_SETTINGS = "org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings";
	protected static final String EEF_BINDING_SETTINGS_IMPL = "org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl";
	protected static final String ORG_ECLIPSE_EMF_EEF_RUNTIME = "org.eclipse.emf.eef2.runtime";

	/**
	 * Create EEFBindingSetting Service in UI thread.
	 * 
	 * @param shell Shell
	 * @param container IRunnableContext
	 * @param modelFile EditingModel IFile
	 * @param ePackage EPackage
	 * 
	 */
	public void createEEFBindingSettingService(final Shell shell, final IRunnableContext container,	final IFile modelFile, final EPackage ePackage) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				createBindingSettingService(shell, container, modelFile, ePackage);
			}
		});

	}

	/**
	 * Creates a file resource handle for the file with the given workspace
	 * path. This method does not create the file resource; this is the
	 * responsibility of <code>createFile</code>.
	 * 
	 * @param filePath
	 *            the path of the file resource to create a handle for
	 * @return the new file resource handle
	 * @see #createFile
	 */
	protected static IFile createFileHandle(IPath filePath) {
		return IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFile(filePath);
	}

	/**
	 * Creates a new file resource in the selected container and with the
	 * selected name. Creates any missing resource containers along the path;
	 * does nothing if the container resources already exist.
	 * <p>
	 * In normal usage, this method is invoked after the user has pressed Finish
	 * on the wizard; the enablement of the Finish button implies that all
	 * controls on on this page currently contain valid values.
	 * </p>
	 * <p>
	 * Note that this page caches the new file once it has been successfully
	 * created; subsequent invocations of this method will answer the same file
	 * resource without attempting to create it again.
	 * </p>
	 * <p>
	 * This method should be called within a workspace modify operation since it
	 * creates resources.
	 * </p>
	 * 
	 * @param newFilePath
	 * 
	 * @param runnableContext
	 * @param shell
	 * 
	 * @return the created file resource, or <code>null</code> if the file was
	 *         not created
	 */
	public IFile createNewFile(IPath newFilePath, IRunnableContext runnableContext, final Shell shell) {
		// create the new file and cache it if
		// successful

		final IFile newFileHandle = createFileHandle(newFilePath);
		final InputStream initialContents = null;

		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				CreateFileOperation op = new CreateFileOperation(newFileHandle, null, initialContents,
						IDEWorkbenchMessages.WizardNewFileCreationPage_title);
				try {
					// see bug
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so
					// that the undo state is
					// not preserved. Making this
					// undoable resulted in too many
					// accidental file deletions.
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(shell));
				} catch (final ExecutionException e) {
					if (shell != null) {
						shell.getDisplay().syncExec(new Runnable() {
							public void run() {
								if (e.getCause() instanceof CoreException) {
									ErrorDialog.openError(shell, // Was
											// Utilities.getFocusShell()
											IDEWorkbenchMessages.WizardNewFileCreationPage_errorTitle, null, // no
											// special
											// message
											((CoreException) e.getCause()).getStatus());
								} else {
									IDEWorkbenchPlugin.log(getClass(), "createNewFile()", e.getCause()); //$NON-NLS-1$
									MessageDialog.openError(shell,
											IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle, e
													.getCause().getMessage());
								}
							}
						});
					} else {
						EditingModelEditPlugin.INSTANCE.log(e);
					}
				}
			}
		};

		try {
			runnableContext.run(false, true, op);
		} catch (InterruptedException e) {
			return null;
		} catch (InvocationTargetException e) {
			// Execution Exceptions are handled above
			// but we may still get
			// unexpected runtime errors.
			IDEWorkbenchPlugin.log("PDEComponentUtil.createNewFile()", e.getTargetException()); //$NON-NLS-1$
			MessageDialog.open(MessageDialog.ERROR, shell,
					IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle, e.getTargetException()
							.getMessage(), SWT.SHEET);

			return null;
		}

		return newFileHandle;
	}

	/**
	 * Create EEFBindingSetting Service.
	 * 
	 * @param shell Shell
	 * @param container IRunnableContext
	 * @param modelFile EditingModel IFile
	 * @param ePackage EPackage
	 * 
	 */
	protected void createBindingSettingService(final Shell shell, final IRunnableContext container, final IFile modelFile,
			final EPackage metamodel) {
		final IPath newFilePath = new Path(modelFile.getProject().getName() + "/OSGI-INF/" + metamodel.getName()
				+ "BindingSettings.xml");
		IFile fFile = createNewFile(newFilePath, container, shell);
		DSCreationOperation dsCreationOperation = new DSCreationOperation(fFile, modelFile.getProject().getName() + "."
				+ metamodel.getName() + "BindingSettings", EEF_BINDING_SETTINGS_IMPL) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.pde.internal.ds.ui.wizards.DSCreationOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
			 */
			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
					InterruptedException {
				monitor.beginTask("", 3);
				createContent();
				monitor.worked(1);
				if (PDE.hasPluginNature(fFile.getProject())) {
					writeManifest(fFile.getProject(), new SubProgressMonitor(monitor, 1));
					writeBuildProperties(fFile.getProject(), new SubProgressMonitor(monitor, 1));
				}
				monitor.done();
				if (PDE.hasPluginNature(fFile.getProject())) {
					addManifestEEFDependency(fFile.getProject(), new SubProgressMonitor(monitor, 1));
				}
			}

			protected void addManifestEEFDependency(IProject project, SubProgressMonitor monitor) {

				PDEModelUtility.modifyModel(new ModelModification(project) {

					protected void modifyModel(IBaseModel model, IProgressMonitor monitor) throws CoreException {

						if (model instanceof IBundlePluginModelBase)
							addManifestEEFDependency((IBundlePluginModelBase) model, monitor);
					}
				}, monitor);
				monitor.done();

			}

			private void writeManifest(IProject project, SubProgressMonitor monitor) {

				PDEModelUtility.modifyModel(new ModelModification(project) {

					protected void modifyModel(IBaseModel model, IProgressMonitor monitor) throws CoreException {

						if (model instanceof IBundlePluginModelBase)
							updateManifest((IBundlePluginModelBase) model, monitor);
					}
				}, monitor);
				monitor.done();

			}

			private void writeBuildProperties(final IProject project, SubProgressMonitor monitor) {

				PDEModelUtility.modifyModel(new ModelModification(PDEProject.getBuildProperties(project)) {
					protected void modifyModel(IBaseModel model, IProgressMonitor monitor) throws CoreException {
						if (!(model instanceof IBuildModel))
							return;
						IFile file = PDEProject.getBuildProperties(project);
						if (file.exists()) {
							WorkspaceBuildModel wbm = new WorkspaceBuildModel(file);
							wbm.load();
							if (!wbm.isLoaded())
								return;
							IBuildModelFactory factory = wbm.getFactory();
							String path = fFile.getFullPath().removeFirstSegments(1).toPortableString();
							IBuildEntry entry = wbm.getBuild().getEntry(IBuildEntry.BIN_INCLUDES);
							if (entry == null) {
								entry = factory.createEntry(IBuildEntry.BIN_INCLUDES);
								wbm.getBuild().add(entry);
							}
							if (!entry.contains(path)) {
								entry.addToken(path);
							}
							wbm.save();
						}
					}
				}, null);

				monitor.done();

			}

			private final String DS_MANIFEST_KEY = "Service-Component"; //$NON-NLS-1$

			private void updateManifest(IBundlePluginModelBase model, IProgressMonitor monitor) throws CoreException {
				IBundleModel bundleModel = model.getBundleModel();

				// Create a path from the bundle root to the component
				// file
				IContainer root = PDEProject.getBundleRoot(fFile.getProject());
				String filePath = fFile.getFullPath().makeRelativeTo(root.getFullPath()).toPortableString();

				String header = bundleModel.getBundle().getHeader(DS_MANIFEST_KEY);
				if (header != null) {
					if (containsValue(header, filePath)) {
						return;
					}
					filePath = header + ",\n " + filePath; //$NON-NLS-1$
				}
				bundleModel.getBundle().setHeader(DS_MANIFEST_KEY, filePath);
			}

			private void addManifestEEFDependency(IBundlePluginModelBase model, IProgressMonitor monitor)
					throws CoreException {
				IBundleModel bundleModel = model.getBundleModel();

				// Create a path from the bundle root to the component
				// file
				String filePath = ORG_ECLIPSE_EMF_EEF_RUNTIME;
				String header = bundleModel.getBundle().getHeader("Require-Bundle");
				if (header != null) {
					if (containsValue(header, ORG_ECLIPSE_EMF_EEF_RUNTIME)) {
						return;
					}
					filePath = header + ",\n " + ORG_ECLIPSE_EMF_EEF_RUNTIME; //$NON-NLS-1$
				}
				bundleModel.getBundle().setHeader("Require-Bundle", filePath);
			}

			private boolean containsValue(String header, String value) {
				if (header == null) {
					return false;
				}
				value = value.trim();
				StringTokenizer st = new StringTokenizer(header, ","); //$NON-NLS-1$
				while (st.hasMoreElements()) {
					String token = st.nextToken();
					if (token.trim().contains(value)) {
						return true;
					}
				}
				return false;
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.pde.internal.ds.ui.wizards.DSCreationOperation#initializeDS(org.eclipse.pde.internal.ds.core.IDSComponent,
			 *      org.eclipse.core.resources.IFile)
			 */
			@Override
			protected void initializeDS(IDSComponent component, IFile file) {
				super.initializeDS(component, file);
				initializeBindingSetting(component);
			}

			/**
			 * @param component
			 */
			public void initializeBindingSetting(IDSComponent component) {
				IDSDocumentFactory factory = component.getModel().getFactory();

				IDSService service = factory.createService();
				component.addChildNode(service);

				IDSProvide provide = factory.createProvide();
				provide.setInterface(EEF_BINDING_SETTINGS);
				service.addProvidedService(provide);

				IDSReference reference = factory.createReference();
				// set interface attribute
				String fullyQualifiedName = EMF_SERVICE_PROVIDER;
				reference.setReferenceInterface(fullyQualifiedName);

				// set name attribute
				int index = fullyQualifiedName.lastIndexOf("."); //$NON-NLS-1$
				if (index != -1) {
					fullyQualifiedName = fullyQualifiedName.substring(index + 1);
				}
				reference.setReferenceName(fullyQualifiedName);
				reference.setReferenceBind(SET_EMF_SERVICE_PROVIDER);

				// add reference
				component.addReference(reference);

				IDSProperty property = factory.createProperty();
				property.setPropertyName(EEFBindingSettingsImpl.EEF_EDITING_MODEL_URI);
				property.setPropertyValue(metamodel.getNsURI());
				component.addPropertyElement(property);

				IDSProperty uriProperty = factory.createProperty();
				uriProperty.setPropertyName(EEFBindingSettingsImpl.EEF_EDITING_MODEL_PATH);
				uriProperty.setPropertyValue(modelFile.getProjectRelativePath().toString());
				component.addPropertyElement(uriProperty);
			}

		};
		try {
			container.run(false, true, dsCreationOperation);
		} catch (InvocationTargetException e) {
			IDEWorkbenchPlugin.log("PDEComponentUtil.createEEFBindingSettingService()", e.getTargetException()); //$NON-NLS-1$
		} catch (InterruptedException e) {
			IDEWorkbenchPlugin.log("PDEComponentUtil.createEEFBindingSettingService()", e); //$NON-NLS-1$
		}
	}
}
