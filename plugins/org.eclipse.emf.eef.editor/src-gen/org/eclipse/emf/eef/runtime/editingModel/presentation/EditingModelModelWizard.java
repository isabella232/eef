/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.presentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.MissingResourceException;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.PDEHelper;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.presentation.pages.EditingModelModelWizardNewFileCreationPage;
import org.eclipse.emf.eef.runtime.editingModel.presentation.pages.ModelToChoosePage;
import org.eclipse.emf.eef.runtime.editingModel.presentation.pages.ModelToChoosePage.ModelInitializationChangeListener;
import org.eclipse.emf.eef.runtime.editingModel.provider.EditingModelItemProviderAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettingsHelper;
import org.eclipse.emf.eef.runtime.util.EEFURIAwareResourceSet;
import org.eclipse.emf.eef.views.ViewsFactory;
import org.eclipse.emf.eef.views.ViewsPackage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 * This is a simple wizard for creating a new model file. <!-- begin-user-doc
 * --> <!-- end-user-doc -->
 * 
 * @generated
 */
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

	private ModelToChoosePage initializingMethodPage;

	private AdapterFactoryEditingDomain editingDomain;

	private ComposedAdapterFactory adapterFactory;

	private EPackage metamodelePackage;

	private IFile selectedModel;

	private IContainer containerSelectedModel;

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
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(EditingModelEditPlugin.INSTANCE.getImage("full/wizban/eef2_wizban")));
		if (selection.getFirstElement() instanceof IFile) {
			selectedModel = (IFile) selection.getFirstElement();
			containerSelectedModel = selectedModel.getParent();
		}
		if (selection.getFirstElement() instanceof IContainer) {
			containerSelectedModel = (IContainer) selection.getFirstElement();
		}
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
	 * Do the work after everything is specified. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public boolean performFinish() {
		final GenericBindingSettingsHelper genericBindingSettingsHelper = new GenericBindingSettingsHelper();
		try {
			// Remember the file.
			//
			final IFile modelFile = getModelFile();

			// Do the work within an operation.
			//
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					URI uri = initializingMethodPage.getURI();
					boolean validURI = initializingMethodPage.isValidURI();
					boolean isModel = initializingMethodPage.isModel();
					boolean initModel = initializingMethodPage.initModel();
					createEditingDomain();
					metamodelePackage = genericBindingSettingsHelper.initEditingModel(editingDomain, modelFile, uri, validURI, isModel, initModel, progressMonitor);
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
				// if second load a model, load it directly in second tab
				if (initializingMethodPage.isModel() && editor instanceof EditingModelEditor) {
					((EditingModelEditor) editor).getEditingDomainForOtherModel().getResourceSet().getResource(initializingMethodPage.getURI(), true);
				}
			} catch (PartInitException exception) {
				MessageDialog.openError(workbenchWindow.getShell(), EditingModelEditPlugin.INSTANCE.getString("_UI_OpenEditorError_label"), exception.getMessage());
				return false;
			}

			// create EEF Binding Setting service
			if (metamodelePackage != null) {
				final PDEHelper pdeHelper = new PDEHelper();
				getShell().getDisplay().syncExec(new Runnable() {
					public void run() {
						pdeHelper.createEEFBindingSettingService(getShell(), getContainer(), modelFile, metamodelePackage);
					}
				});
			}

			return true;
		} catch (Exception exception) {
			EditingModelEditPlugin.INSTANCE.log(exception);
			return false;
		}
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
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new EEFURIAwareResourceSet() {

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
		initializingMethodPage = new ModelToChoosePage(selection);
		initializingMethodPage.setEditingDomain(editingDomain);
		addPage(initializingMethodPage);

		// Create a page, set the title, and the initial model file name.
		//
		newFileCreationPage = new EditingModelModelWizardNewFileCreationPage("Whatever", selection);
		newFileCreationPage.setTitle(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelModelWizard_label"));
		newFileCreationPage.setDescription(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelModelWizard_description"));
		newFileCreationPage.setFileName(EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0));
		addPage(newFileCreationPage);

		initializingMethodPage.addModelInitializationListener(new ModelInitializationChangeListener() {

			public void modelInitializationChanged(String modelName) {
				if (modelName == ModelToChoosePage.DEFAULT_MODEL_NAME) {
					String filename = EditingModelEditPlugin.INSTANCE.getString("_UI_EditingModelEditorFilenameDefaultBase") + "." + FILE_EXTENSIONS.get(0);
					filename = calculateModelName(filename, modelName);
					newFileCreationPage.setFileName(filename);
				} else {
					String fileName = modelName + "." + FILE_EXTENSIONS.get(0);
					fileName = calculateModelName(fileName, modelName);
					newFileCreationPage.setFileName(fileName);
				}
			}
		});

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
					if (selectedResource.getName() != null) {
						defaultModelBaseFilename = selectedResource.getName();
					}
					String defaultModelFilenameExtension = FILE_EXTENSIONS.get(0);
					String modelFilename = defaultModelBaseFilename + "." + defaultModelFilenameExtension;
					for (int i = 1; ((IContainer) selectedResource).findMember(modelFilename) != null; ++i) {
						modelFilename = defaultModelBaseFilename + i + "." + defaultModelFilenameExtension;
					}
					newFileCreationPage.setFileName(modelFilename);
				}
			}
		}

	}

	/**
	 * Get the file from the page. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IFile getModelFile() {
		return newFileCreationPage.getModelFile();
	}

	/**
	 * @param modelName
	 * @param modelName2
	 * @return
	 */
	public String calculateModelName(String fileName, String modelName) {
		if (containerSelectedModel != null) {
			for (int i = 1; containerSelectedModel.findMember(fileName) != null; ++i) {
				fileName = modelName + i + "." + FILE_EXTENSIONS.get(0);
			}
		}
		return fileName;
	}


}
