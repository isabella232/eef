/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.presentation;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.presentation.util.EditingModelEditorResourceSet;
import org.eclipse.emf.eef.runtime.editingModel.provider.EditingModelItemProviderAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.E3EEFRuntimeUIPlatformPlugin;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.emf.eef.views.ViewsRepository;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.undo.CreateFileOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

import com.google.common.base.Strings;

/**
 * This is a simple wizard for creating a new model file. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
@SuppressWarnings("restriction")
public class EditingModelModelWizard extends Wizard implements INewWizard {

	/**
	 * The supported extensions for created files. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<String> FILE_EXTENSIONS = Collections.unmodifiableList(Arrays.asList(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameExtensions").split("\\s*,\\s*")));

	/**
	 * A formatted list of supported file extensions, suitable for display. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String FORMATTED_FILE_EXTENSIONS = EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameExtensions").replaceAll("\\s*,\\s*", ", ");

	/**
	 * This caches an instance of the model package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EditingModelPackage editingModelPackage = EditingModelPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EditingModelFactory editingModelFactory = editingModelPackage.getEditingModelFactory();

	/**
	 * This caches an instance of the model package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ViewsPackage viewsModelPackage = ViewsPackage.eINSTANCE;

	/**
	 * This caches an instance of the model factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ViewsFactory viewsModelFactory = viewsModelPackage.getViewsFactory();

	/**
	 * This is the file creation page. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	protected EditingModelModelWizardNewFileCreationPage newFileCreationPage;

	/**
	 * Remember the selection during initialization for populating the default
	 * container. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IStructuredSelection selection;

	/**
	 * Remember the workbench during initialization. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IWorkbench workbench;

	/**
	 * Caches the names of the types that can be created as the root object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected List<String> initialObjectNames;

	private ModelToChoosePage secondPage;

	private AdapterFactoryEditingDomain editingDomain;

	private ComposedAdapterFactory adapterFactory;

	private EPackage metamodelePackage;

	/**
	 * This just records the information. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle(EditingModelEditPlugin.INSTANCE.getString("_UI_Wizard_label"));
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(EditingModelEditPlugin.INSTANCE.getImage("full/wizban/NewEditingModel")));
	}

	/**
	 * Returns the names of the types that can be created as the root object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Collection<String> getInitialObjectNames() {
		if (initialObjectNames == null) {
			initialObjectNames = new ArrayList<String>();
			for (EClassifier eClassifier : editingModelPackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;
					if (!eClass.isAbstract()) {
						initialObjectNames.add(eClass.getName());
					}
				}
			}
			Collections.sort(initialObjectNames, CommonPlugin.INSTANCE.getComparator());
		}
		return initialObjectNames;
	}

	/**
	 * Create a new {@link PropertiesEditingModel}.
	 * 
	 * @generated NOT
	 */
	protected EObject createEditingModel() {
		return editingModelFactory.create(editingModelPackage.getPropertiesEditingModel());
	}

	/**
	 * Create a new {@link ViewsRepository}.
	 * 
	 * @generated NOT
	 */
	protected EObject createViewsRepository() {
		return viewsModelFactory.create(viewsModelPackage.getViewsRepository());
	}

	/**
	 * Do the work after everything is specified. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean performFinish() {
		try {
			// Remember the file.
			//
			final IFile modelFile = getModelFile();

			// Do the work within an operation.
			//
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						// Create a resource set
						//
						ResourceSet resourceSet = new ResourceSetImpl();

						// Get the URI of the model file.
						//
						URI fileURI = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);

						// Create a resource for this file.
						//
						Resource resource = resourceSet.createResource(fileURI);

						// Add the initial model object to the contents.
						//
						if (secondPage.isValidURI() && secondPage.getURI() != null && !Strings.isNullOrEmpty(secondPage.getURI().toString())) {
							URI resourceURI = secondPage.getURI();
							createEditingDomain();
							Resource modelResource = null;
							if (!secondPage.isModel()) {
								ResourceSet rs = new ResourceSetImpl();
								modelResource = rs.getResource(resourceURI, true);
							} else {
								modelResource = editingDomain.getResourceSet().getResource(resourceURI, true);
							}

							if (!modelResource.getContents().isEmpty()) {
								EObject root = modelResource.getContents().get(0);

								EPackage ePackage = root.eClass().getEPackage();
								if (!secondPage.isModel() && root instanceof EPackage) {
									ePackage = (EPackage) root;
								}
								setMetamodelePackage(ePackage);

								EEFBindingSettingsProvider bindingSettingsProvider = E3EEFRuntimeUIPlatformPlugin.getPlugin().getBindingSettingsProvider();
								EEFBindingSettings bindingSettings = bindingSettingsProvider.getBindingSettings(ePackage);

								if (bindingSettings instanceof GenericBindingSettings) {
									for (EClassifier content : ePackage.getEClassifiers()) {
										if (content instanceof EClass) {
											((GenericBindingSettings) bindingSettings).getEEFDescription((EClass) content);
										}
									}
								}

								PropertiesEditingModel editingModel = bindingSettings.getEditingModel(ePackage);

								if (editingModel != null) {
									resource.getContents().addAll(EcoreUtil.copyAll(editingModel.eResource().getContents()));
									for (EObject eObject : editingModel.getInvolvedModels()) {
										editingDomain.getResourceSet().getResources().add(eObject.eResource());
									}
									EcoreUtil.resolveAll(editingDomain.getResourceSet());
								} else {
									EObject rootObject = createEditingModel();
									if (rootObject != null) {
										resource.getContents().add(rootObject);
									}
									rootObject = createViewsRepository();
									if (rootObject != null) {
										resource.getContents().add(rootObject);
									}
								}
							}
						} else {
							EObject rootObject = createEditingModel();
							if (rootObject != null) {
								resource.getContents().add(rootObject);
							}
							rootObject = createViewsRepository();
							if (rootObject != null) {
								resource.getContents().add(rootObject);
							}
						}

						// Save the contents of the resource to the file system.
						//
						Map<Object, Object> options = new HashMap<Object, Object>();
						options.put(XMLResource.OPTION_ENCODING, "UTF-8");
						resource.save(options);

					} catch (Exception exception) {
						EditingModelEditPlugin.INSTANCE.log(exception);
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

			// Open an editor on the new file.
			//
			IEditorPart editor = null;
			try {
				editor = page.openEditor(new FileEditorInput(modelFile), workbench.getEditorRegistry().getDefaultEditor(modelFile.getFullPath().toString()).getId());
			} catch (PartInitException exception) {
				MessageDialog.openError(workbenchWindow.getShell(), EditingModelEditPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), exception.getMessage());
				return false;
			}

			// create EEF Binding Setting service
			getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					createEEFBindingSettingService(modelFile, metamodelePackage.getName());
				}
			});

			return true;
		} catch (Exception exception) {
			EditingModelEditPlugin.INSTANCE.log(exception);
			return false;
		}
	}

	protected static final String SET_EMF_SERVICE_PROVIDER = "setEMFServiceProvider";
	protected static final String EMF_SERVICE_PROVIDER = "org.eclipse.emf.eef.runtime.util.EMFServiceProvider";
	protected static final String EEF_BINDING_SETTINGS = "org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings";
	protected static final String EEF_BINDING_SETTINGS_IMPL = "org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl";
	protected static final String ORG_ECLIPSE_EMF_EEF_RUNTIME = "org.eclipse.emf.eef.runtime";
	protected static final String EEF_EDITING_MODEL_PATH = "eef.editingModel.path";

	/**
	 * @param modelFile
	 * @param runnableContext
	 * @param shell
	 * 
	 */
	public void createEEFBindingSettingService(final IFile modelFile, final String metamodelName) {
		getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				final IPath newFilePath = new Path(modelFile.getProject().getName() + "/OSGI-INF/" + metamodelName + "BindingSettings.xml");
				IFile fFile = createNewFile(newFilePath, getContainer(), getShell());
				DSCreationOperation dsCreationOperation = new DSCreationOperation(fFile, modelFile.getProject().getName() + "." + metamodelName + "BindingSettings", EEF_BINDING_SETTINGS_IMPL) {

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.pde.internal.ds.ui.wizards.DSCreationOperation#execute(org.eclipse.core.runtime.IProgressMonitor)
					 */
					@Override
					protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException, InterruptedException {
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
									entry.addToken(path);
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

					private void addManifestEEFDependency(IBundlePluginModelBase model, IProgressMonitor monitor) throws CoreException {
						IBundleModel bundleModel = model.getBundleModel();

						// Create a path from the bundle root to the component
						// file
						IContainer root = PDEProject.getBundleRoot(fFile.getProject());
						String filePath = fFile.getFullPath().makeRelativeTo(root.getFullPath()).toPortableString();

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
						property.setPropertyName(EEF_EDITING_MODEL_PATH);
						property.setPropertyValue(modelFile.getProjectRelativePath().toString());
						component.addPropertyElement(property);
					}

				};
				try {
					getContainer().run(false, true, dsCreationOperation);
				} catch (InvocationTargetException e) {
					IDEWorkbenchPlugin.log("PDEComponentUtil.createEEFBindingSettingService()", e.getTargetException()); //$NON-NLS-1$
				} catch (InterruptedException e) {
					IDEWorkbenchPlugin.log("PDEComponentUtil.createEEFBindingSettingService()", e); //$NON-NLS-1$
				}
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
				CreateFileOperation op = new CreateFileOperation(newFileHandle, null, initialContents, IDEWorkbenchMessages.WizardNewFileCreationPage_title);
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
								MessageDialog.openError(shell, IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle, /*
																																 * NLS
																																 * .
																																 * bind
																																 * (
																																 * ""
																																 * IDEWorkbenchMessages
																																 * .
																																 * WizardNewFileCreationPage_internalErrorMessage
																																 * ,
																																 */e.getCause().getMessage());
							}
						}
					});
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
			MessageDialog.open(MessageDialog.ERROR, shell, IDEWorkbenchMessages.WizardNewFileCreationPage_internalErrorTitle, /*
																															 * NLS
																															 * .
																															 * bind
																															 * (
																															 * IDEWorkbenchMessages
																															 * .
																															 * WizardNewFileCreationPage_internalErrorMessage
																															 * ,
																															 */e.getTargetException().getMessage(), SWT.SHEET);

			return null;
		}

		return newFileHandle;
	}

	protected void setMetamodelePackage(EPackage ePackage) {
		metamodelePackage = ePackage;
	}

	/**
	 * Create editing domain for other models
	 */
	public void createEditingDomain() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EditingModelItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new EditingModelEditorResourceSet() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
			 */
			public EditingDomain getEditingDomain() {
				return editingDomain;
			}

		});
	}

	/**
	 * This is the one page of the wizard. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public class EditingModelModelWizardNewFileCreationPage extends WizardNewFileCreationPage {
		/**
		 * Pass in the selection. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public EditingModelModelWizardNewFileCreationPage(String pageId, IStructuredSelection selection) {
			super(pageId, selection);
		}

		/**
		 * The framework calls this to see if the file is correct. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		@Override
		protected boolean validatePage() {
			if (super.validatePage()) {
				String extension = new Path(getFileName()).getFileExtension();
				if (extension == null || !FILE_EXTENSIONS.contains(extension)) {
					String key = FILE_EXTENSIONS.size() > 1 ? "_WARN_FilenameExtensions" : "_WARN_FilenameExtension";
					setErrorMessage(EditingModelEditPlugin.INSTANCE.getString(key, new Object[] { FORMATTED_FILE_EXTENSIONS }));
					return false;
				}
				return true;
			}
			return false;
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public IFile getModelFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}

	/**
	 * This is the page where the type of object to create is selected. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public class EditingModelModelWizardInitialObjectCreationPage extends WizardPage {
		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected Combo initialObjectField;

		/**
		 * @generated <!-- begin-user-doc --> <!-- end-user-doc -->
		 */
		protected List<String> encodings;

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected Combo encodingField;

		/**
		 * Pass in the selection. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public EditingModelModelWizardInitialObjectCreationPage(String pageId) {
			super(pageId);
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public void createControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			{
				GridLayout layout = new GridLayout();
				layout.numColumns = 1;
				layout.verticalSpacing = 12;
				composite.setLayout(layout);

				GridData data = new GridData();
				data.verticalAlignment = GridData.FILL;
				data.grabExcessVerticalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				composite.setLayoutData(data);
			}

			Label containerLabel = new Label(composite, SWT.LEFT);
			{
				containerLabel.setText(EditingModelEditPlugin.INSTANCE.getString("_UI_ModelObject"));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				containerLabel.setLayoutData(data);
			}

			initialObjectField = new Combo(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				initialObjectField.setLayoutData(data);
			}

			for (String objectName : getInitialObjectNames()) {
				initialObjectField.add(getLabel(objectName));
			}

			if (initialObjectField.getItemCount() == 1) {
				initialObjectField.select(0);
			}
			initialObjectField.addModifyListener(validator);

			Label encodingLabel = new Label(composite, SWT.LEFT);
			{
				encodingLabel.setText(EditingModelEditPlugin.INSTANCE.getString("_UI_XMLEncoding"));

				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				encodingLabel.setLayoutData(data);
			}
			encodingField = new Combo(composite, SWT.BORDER);
			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				encodingField.setLayoutData(data);
			}

			for (String encoding : getEncodings()) {
				encodingField.add(encoding);
			}

			encodingField.select(0);
			encodingField.addModifyListener(validator);

			setPageComplete(validatePage());
			setControl(composite);
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected ModifyListener validator = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				setPageComplete(validatePage());
			}
		};

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected boolean validatePage() {
			return getInitialObjectName() != null && getEncodings().contains(encodingField.getText());
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			if (visible) {
				if (initialObjectField.getItemCount() == 1) {
					initialObjectField.clearSelection();
					encodingField.setFocus();
				} else {
					encodingField.clearSelection();
					initialObjectField.setFocus();
				}
			}
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public String getInitialObjectName() {
			String label = initialObjectField.getText();

			for (String name : getInitialObjectNames()) {
				if (getLabel(name).equals(label)) {
					return name;
				}
			}
			return null;
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public String getEncoding() {
			return encodingField.getText();
		}

		/**
		 * Returns the label for the specified type name. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected String getLabel(String typeName) {
			try {
				return EditingModelEditPlugin.INSTANCE.getString("_UI_" + typeName + "_type");
			} catch (MissingResourceException mre) {
				EditingModelEditPlugin.INSTANCE.log(mre);
			}
			return typeName;
		}

		/**
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		protected Collection<String> getEncodings() {
			if (encodings == null) {
				encodings = new ArrayList<String>();
				for (StringTokenizer stringTokenizer = new StringTokenizer(EditingModelEditPlugin.INSTANCE.getString("_UI_XMLEncodingChoices")); stringTokenizer.hasMoreTokens();) {
					encodings.add(stringTokenizer.nextToken());
				}
			}
			return encodings;
		}
	}

	/**
	 * The framework calls this to create the contents of the wizard. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void addPages() {
		// Create a page, set the title, and the initial model file name.
		//
		newFileCreationPage = new EditingModelModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelModelWizard_label"));
		newFileCreationPage.setDescription(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelModelWizard_description"));
		newFileCreationPage.setFileName(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);

		// Try and get the resource selection to determine a current directory
		// for the file dialog.
		//
		if (selection != null && !selection.isEmpty()) {
			// Get the resource...
			//
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				// Get the resource parent, if its a file.
				//
				IResource selectedResource = (IResource) selectedElement;
				if (selectedResource.getType() == IResource.FILE) {
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				//
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					// Set this for the container.
					//
					newFileCreationPage.setContainerFullPath(selectedResource.getFullPath());

					// Make up a unique new name here.
					//
					String defaultModelBaseFilename = EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameDefaultBase");
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;
					for (int i = 1; ((IContainer) selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
					}
					newFileCreationPage.setFileName(modelFilename);
				}
			}
		}

		secondPage = new ModelToChoosePage(selection);
		secondPage.setEditingDomain(editingDomain);
		addPage(secondPage);

	}

	/**
	 * Get the file from the page. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IFile getModelFile() {
		return newFileCreationPage.getModelFile();
	}

}
