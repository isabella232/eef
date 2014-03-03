/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.pages;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider.FontAndColorProvider;
import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction;
import org.eclipse.emf.eef.editor.internal.notify.Notifiable;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.editor.internal.services.util.ViewLockingSettings;
import org.eclipse.emf.eef.editor.internal.widgets.MultiEEFViewer;
import org.eclipse.emf.eef.editor.internal.widgets.MultiEEFViewer.SelectionInterpreter;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.NullPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelPackage;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class BindingsEditingPage extends FormPage {

	private static final int MODELS_SECTION_WIDTH = 30;

	private EMFService emfService;
	private EEFEditingService eefEditingService;
	private SelectionService selectionService;
	private ViewerService viewerService;
	private EditingContextFactoryProvider contextFactoryProvider;
	private ImageManager imageManager;

	private AdapterFactory adapterFactory;
	private ResourceSet input;

	private ToolBar bindingSettingsActions;

	private FilteredTree metamodelViewer;
	private MultiEEFViewer bindingSettingsViewer;

	private SelectionBroker selectionBroker;
	private FormToolkit toolkit;

	private Composite pageContainer;

	private Form innerForm;

	private EEFViewer bindingPreviewViewer;

	/**
	 * @param editor
	 * @param adapterFactory
	 */
	public BindingsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-binding-editing", "Bindings");
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param emfService
	 *            the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param eefEditingService
	 *            the eefEditingService to set
	 */
	public void setEEFEditingService(EEFEditingService eefEditingService) {
		this.eefEditingService = eefEditingService;
	}

	/**
	 * @param selectionService
	 *            the selectionService to set
	 */
	public void setSelectionService(SelectionService selectionService) {
		this.selectionService = selectionService;
	}

	/**
	 * @param editingContextFactoryProvider
	 *            the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider editingContextFactoryProvider) {
		this.contextFactoryProvider = editingContextFactoryProvider;
	}

	/**
	 * @param viewerService
	 *            the viewerService to set
	 */
	public void setViewerService(ViewerService viewerService) {
		this.viewerService = viewerService;
	}

	/**
	 * @param imageManager
	 *            the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * Defines the page input.
	 * 
	 * @param input
	 *            page input.
	 */
	public void setInput(Object input) {
		if (input instanceof ResourceSet) {
			this.input = (ResourceSet) input;
			if (metamodelViewer != null) {
				Collection<Resource> ecoreResources = emfService.ecoreResources(this.input);
				metamodelViewer.getViewer().setInput(ecoreResources);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		toolkit = managedForm.getToolkit();
		innerForm = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(innerForm);
		refreshPageTitle();
		Composite parent = innerForm.getBody();
		parent.setLayout(new FillLayout());
		pageContainer = toolkit.createComposite(parent);
		pageContainer.setLayout(new FormLayout());
		Composite modelSection = createModelSection(toolkit, pageContainer);
		Composite bindingSettingsSection = createBindingSettingsSection(toolkit, pageContainer);
		Composite previewSection = createPreviewSection(toolkit, pageContainer);
		layoutPage(modelSection, bindingSettingsSection, previewSection);
		initSelectionBroker();
		metamodelViewer.getViewer().addSelectionChangedListener(selectionBroker);
		metamodelViewer.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				refreshPageLayout();
			}
		});
		((EEFReflectiveEditor) getEditor()).addNotifiable(new Notifiable() {

			public void notifyChanged(final Notification notification) {

				metamodelViewer.getViewer().getControl().getDisplay().asyncExec(new Runnable() {

					public void run() {
						if (notification.getNotifier() instanceof EClassBinding || notification.getNotifier() instanceof PropertyBinding) {
							setBindingPreviewViewerInput();
						}
						metamodelViewer.getViewer().refresh();
						if (Notification.MOVE != notification.getEventType()) {
							bindingSettingsViewer.refresh();
						}
						bindingPreviewViewer.refresh();
						refreshPageTitle();

					}
				});
			}

			public int getIndex() {
				return 1;
			}
		});
	}

	private Composite createModelSection(FormToolkit toolkit, Composite container) {
		Section modelSection = toolkit.createSection(container, Section.TITLE_BAR);
		modelSection.setText("Models");
		Composite modelContainer = toolkit.createComposite(modelSection);
		modelContainer.setLayout(new GridLayout(1, false));
		createModelSectionContents(toolkit, modelContainer);
		modelSection.setClient(modelContainer);
		ToolBar actions = new ToolBar(modelSection, SWT.NONE);
		ToolItem load = new ToolItem(actions, SWT.PUSH);
		load.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Load"));
		load.setToolTipText("Load a model in the editor resources");
		load.addSelectionListener(new LoadSelectionAdapter(((IEditingDomainProvider) getEditor()).getEditingDomain()));
		final ToolItem filterModels = new ToolItem(actions, SWT.CHECK);
		filterModels.setImage(imageManager.getImage(EditingModelEditPlugin.getPlugin(), "Filter"));
		filterModels.setToolTipText("Only show ecore models referenced by an EEF editing model");
		filterModels.setSelection(true);
		filterModels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				metamodelViewer.getViewer().refresh();
			}
		});
		metamodelViewer.getViewer().addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if (filterModels.getSelection() && element instanceof EPackage) {
					return !eefEditingService.referencingEEFElement((EObject) element).isEmpty();
				}
				return true;
			}
		});
		modelSection.setTextClient(actions);

		return modelSection;
	}

	private Composite createBindingSettingsSection(FormToolkit toolkit, Composite container) {
		Section bindingSettingsSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		bindingSettingsSection.setText("Binding Settings");
		bindingSettingsActions = new ToolBar(bindingSettingsSection, SWT.NONE);
		ToolItem add = new ToolItem(bindingSettingsActions, SWT.PUSH);
		// FIXME: What the heck with the delegatingResourceLocator !!!
		add.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add"));
		add.setToolTipText("Add a binding linked to the selected element");
		add.setEnabled(false);
		ToolItem remove = new ToolItem(bindingSettingsActions, SWT.PUSH);
		remove.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Delete"));
		remove.setToolTipText("Remove the current binding");
		remove.setEnabled(false);
		bindingSettingsSection.setTextClient(bindingSettingsActions);
		Composite bindingSettingsContainer = toolkit.createComposite(bindingSettingsSection);
		bindingSettingsContainer.setLayout(new GridLayout(1, false));
		createBindingSettingsSectionContents(toolkit, bindingSettingsContainer);
		EditingDomain editingDomain = ((IEditingDomainProvider) getEditor()).getEditingDomain();
		add.addSelectionListener(new AddBindingAdapter(editingDomain, adapterFactory));
		remove.addSelectionListener(new DeleteBindingAdapter(editingDomain));
		bindingSettingsSection.setClient(bindingSettingsContainer);
		return bindingSettingsSection;
	}

	private Composite createPreviewSection(FormToolkit toolkit, Composite container) {
		Section previewSection = toolkit.createSection(container, Section.TITLE_BAR);
		previewSection.setText("Preview");
		Composite previewContainer = toolkit.createComposite(previewSection);
		previewContainer.setLayout(new GridLayout(1, false));
		createPreviewSectionContents(toolkit, previewContainer);
		previewSection.setClient(previewContainer);
		return previewSection;
	}

	private void layoutPage(Composite modelSection, Composite bindingSettingsSection, Composite previewSection) {
		FormData modelData = new FormData();
		modelData.top = new FormAttachment(0, 10);
		modelData.bottom = new FormAttachment(100, -10);
		modelData.left = new FormAttachment(0, 10);
		modelData.right = new FormAttachment(MODELS_SECTION_WIDTH, -5);
		modelSection.setLayoutData(modelData);
		FormData bindingSettingsData = new FormData();
		bindingSettingsData.top = new FormAttachment(0, 10);
		bindingSettingsData.left = new FormAttachment(modelSection, 5);
		bindingSettingsData.right = new FormAttachment(100, -10);
		bindingSettingsSection.setLayoutData(bindingSettingsData);
		FormData previewData = new FormData();
		previewData.top = new FormAttachment(bindingSettingsSection, 5);
		previewData.bottom = new FormAttachment(100, -10);
		previewData.left = new FormAttachment(modelSection, 10);
		previewData.right = new FormAttachment(100, -10);
		previewSection.setLayoutData(previewData);
	}

	private void createModelSectionContents(FormToolkit toolkit, Composite modelContainer) {
		CTabFolder tabFolder = new CTabFolder(modelContainer, SWT.BORDER);
		tabFolder.setSimple(false);
		toolkit.adapt(tabFolder);
		toolkit.getColors().initializeSectionToolBarColors();
		tabFolder.setSelectionBackground(new Color[] { toolkit.getColors().getColor(IFormColors.H_GRADIENT_START), toolkit.getColors().getColor(IFormColors.H_GRADIENT_END) }, new int[] { 50 }, true);
		tabFolder.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		tabFolder.setSelectionForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		tabFolder.setLayoutData(layoutData);
		createMetamodelViewer(toolkit, tabFolder);
	}

	private void createMetamodelViewer(FormToolkit toolkit, CTabFolder tabFolder) {
		metamodelViewer = new FilteredTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE, new PatternFilter(), true);
		metamodelViewer.getViewer().setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object object) {
				if (object instanceof ResourceSet) {
					List<EPackage> result = Lists.newArrayList();
					for (Resource resource : ((ResourceSet) object).getResources()) {
						for (EObject eObject : resource.getContents()) {
							if (eObject instanceof EPackage) {
								result.add((EPackage) eObject);
							}
						}
					}
					return result.toArray();
				}
				return super.getElements(object);
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
			 */
			@Override
			public Object[] getChildren(Object object) {
				if (object instanceof EClass) {
					return ((EClass) object).getEAllStructuralFeatures().toArray();
				}
				return super.getChildren(object);
			}

		});
		FontAndColorProvider provider = new FontAndColorProvider(adapterFactory, metamodelViewer.getViewer()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getForeground(java.lang.Object)
			 */
			@Override
			public Color getForeground(Object object) {
				if (object instanceof EPackage || object instanceof EClass || object instanceof EStructuralFeature) {
					if (eefEditingService.referencingEEFElement((EObject) object).isEmpty()) {
						return metamodelViewer.getViewer().getControl().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
					}

				}
				return super.getForeground(object);
			}

		};
		metamodelViewer.getViewer().setLabelProvider(provider);
		metamodelViewer.getViewer().addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return element instanceof EObject && eefEditingService.canBeReferencedByEditingModel((EObject) element);
			}
		});
		if (input != null) {
			ResourceSet resourceSet = ((IEditingDomainProvider) getEditor()).getEditingDomain().getResourceSet();
			EcoreUtil.resolveAll(resourceSet);
			metamodelViewer.getViewer().setInput(resourceSet);
		}
		CTabItem metamodelItem = new CTabItem(tabFolder, SWT.NONE);
		metamodelItem.setControl(metamodelViewer);
		metamodelItem.setText("Metamodel");
	}

	private void createBindingSettingsSectionContents(FormToolkit toolkit, Composite bindingSettingsContainer) {
		bindingSettingsViewer = new MultiEEFViewer(bindingSettingsContainer, SWT.BORDER);
		bindingSettingsViewer.setContentProvider(new EEFContentProvider());
		bindingSettingsViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		bindingSettingsViewer.setSelectionInterpreter(new SelectionInterpreter() {

			public PropertiesEditingContext createContextFromSelection(ISelection selection) {
				EObject selectedElement = selectionService.unwrapSelection(selection);
				PropertiesEditingContext context;
				if (selectedElement != null) {
					EditingDomain editingDomain = ((EEFReflectiveEditor) getEditor()).getEditingDomain();
					context = contextFactoryProvider.getEditingContextFactory(selectedElement).createPropertiesEditingContext(editingDomain, adapterFactory, selectedElement);
				} else {
					context = contextFactoryProvider.getEditingContextFactory(selectedElement).createNullEditingContext();
				}
				context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, BindingsEditingPage.this.toolkit);
				return context;
			}
		});
		bindingSettingsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				Object subViewerInput = bindingSettingsViewer.getSubViewer().getInput();
				if (subViewerInput == null || subViewerInput instanceof NullPropertiesEditingContext) {
					if (event.getSelection() == null || event.getSelection().isEmpty()) {
						viewerService.updateViewerBackground(BindingsEditingPage.this.toolkit, bindingSettingsViewer.getSubViewer());
					}
				}
			}
		});
		viewerService.updateViewerBackground(toolkit, bindingSettingsViewer.getSubViewer());
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		bindingPreviewViewer = new EEFViewer(previewContainer, SWT.BORDER);
		bindingPreviewViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, bindingPreviewViewer);
	}

	private void refreshPageTitle() {
		if (innerForm != null) {
			PropertiesEditingModel editedEditingModel = emfService.findEditedEditingModel(((IEditingDomainProvider) getEditor()).getEditingDomain().getResourceSet());
			String formLabel = "Bindings";
			if (editedEditingModel != null) {
				IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory.adapt(editedEditingModel, IItemLabelProvider.class);
				if (labelProvider != null) {
					formLabel = labelProvider.getText(editedEditingModel);
					Image image = imageManager.getImageFromObject(labelProvider.getImage(editedEditingModel));
					if (image != null) {
						innerForm.setImage(image);
					}
				}
			}
			innerForm.setText(formLabel);
		}
	}

	private void refreshPageLayout() {
		pageContainer.layout(true);
		pageContainer.getParent().layout(true);
	}

	private void updateBindingSettingsActionsState(EObject selection) {
		assert bindingSettingsActions.getItemCount() == 2 : "Bad toolbar configuration.";
		ToolItem add = bindingSettingsActions.getItem(0);
		ToolItem delete = bindingSettingsActions.getItem(1);
		if (selection instanceof EClass || ((selection instanceof EStructuralFeature) && findCurrentEClassBinding() != null)) {
			add.setEnabled(true);
			Collection<EObject> referencingEEFElement = eefEditingService.referencingEEFElement(selection);
			if (referencingEEFElement.size() > 0) {
				delete.setEnabled(true);
			} else {
				delete.setEnabled(false);
			}
		} else {
			add.setEnabled(false);
			delete.setEnabled(false);
		}
	}

	private void initSelectionBroker() {
		selectionBroker = new SelectionBroker(selectionService, viewerService, metamodelViewer.getViewer(), bindingSettingsViewer);
	}

	/**
	 * @return
	 */
	private EClassBinding findCurrentEClassBinding() {
		EClassBinding editedBinding = null;
		EClass currentClass = findCurrentEClass();
		if (currentClass != null) {
			Collection<EObject> referencingEEFElement = eefEditingService.referencingEEFElement(currentClass);
			if (referencingEEFElement.size() > 0) {
				editedBinding = (EClassBinding) referencingEEFElement.iterator().next();
			}
		}
		return editedBinding;
	}

	private EClass findCurrentEClass() {
		ISelection metamodelSelection = metamodelViewer.getViewer().getSelection();
		if (metamodelSelection instanceof TreeSelection) {
			TreePath[] paths = ((TreeSelection) metamodelSelection).getPaths();
			if (paths.length > 0) {
				TreePath selectionPath = paths[0];
				for (int i = selectionPath.getSegmentCount() - 1; i >= 0; i--) {
					Object segment = selectionPath.getSegment(i);
					if (segment instanceof EClass) {
						return (EClass) segment;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public void setBindingPreviewViewerInput() {
		if (bindingSettingsViewer.getSubViewer().getInput() instanceof PropertiesEditingContext && ((PropertiesEditingContext) bindingSettingsViewer.getSubViewer().getInput()).getEditingComponent() != null) {
			EObject view = ((PropertiesEditingContext) bindingSettingsViewer.getSubViewer().getInput()).getEditingComponent().getEObject();
			PropertiesEditingContext reflectEditingContext = contextFactoryProvider.getEditingContextFactory(view).createReflectivePropertiesEditingContext(adapterFactory, view);
			reflectEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, BindingsEditingPage.this.toolkit);
			bindingPreviewViewer.setInput(reflectEditingContext);
		}
	}

	private final class SelectionBroker implements ISelectionChangedListener {

		private static final String BINDING_VIEW_ID = "editingModel::Binding";
		private static final String BINDING_E_CLASS_EDITOR_ID = "editingModel::Binding::eClass";
		private static final String PROPERTY_VIEW_ID = "editingModel::Property Binding";
		private static final String BINDING_FEATURE_EDITOR_ID = "editingModel::Property Binding::feature";

		private SelectionService selectionService;
		private ViewerService viewerService;

		private TreeViewer metamodelViewer;
		private MultiEEFViewer bindingSettingsViewer;

		public SelectionBroker(SelectionService selectionService, ViewerService viewerService, TreeViewer metamodelViewer, MultiEEFViewer bindingSettingsViewer) {
			this.selectionService = selectionService;
			this.viewerService = viewerService;
			this.metamodelViewer = metamodelViewer;
			this.bindingSettingsViewer = bindingSettingsViewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == metamodelViewer) {
				EObject selection = selectionService.unwrapSelection(event.getSelection());
				EObject currentSelection = (EObject) bindingSettingsViewer.getInput();
				if (!selection.equals(currentSelection)) {
					bindingSettingsViewer.setInput(selection);
					lockFields();
					updateBindingSettingsActionsState(selection);

					setBindingPreviewViewerInput();
				}
			}

		}

		public void lockFields() {
			EEFViewer subViewer = bindingSettingsViewer.getSubViewer();
			Object input = subViewer.getInput();
			if (input instanceof PropertiesEditingContext) {
				PropertiesEditingComponent editingComponent = ((PropertiesEditingContext) input).getEditingComponent();
				ViewLockingSettings lockingSettings = ViewLockingSettings.builder().addLockSettings(BINDING_VIEW_ID, BINDING_E_CLASS_EDITOR_ID).addLockSettings(PROPERTY_VIEW_ID, BINDING_FEATURE_EDITOR_ID).build();
				viewerService.lockEditors(editingComponent, lockingSettings);
			}
		}

	}

	/**
	 * Loads a Ecore resource into the given {@link EditingDomain}.
	 * 
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 */
	private static class LoadSelectionAdapter extends SelectionAdapter {

		private ExtendedLoadResourceAction action;

		/**
		 * @param domain
		 *            the {@link EditingDomain} to load.
		 */
		public LoadSelectionAdapter(EditingDomain domain) {
			this.action = new ExtendedLoadResourceAction();
			action.setEditingDomain(domain);
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			action.run();
		}

	}

	private class AddBindingAdapter extends SelectionAdapter {

		private EditingDomain editingDomain;
		private AdapterFactory adapterFactory;

		public AddBindingAdapter(EditingDomain editingDomain, AdapterFactory adapterFactory) {
			this.editingDomain = editingDomain;
			this.adapterFactory = adapterFactory;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			EObject selection = selectionService.unwrapSelection(metamodelViewer.getViewer().getSelection());
			if (selection instanceof EClass) {
				PropertiesEditingModel editedModel = emfService.findEditedEditingModel(editingDomain.getResourceSet());
				if (editedModel != null) {
					EClassBinding binding = EditingModelFactory.eINSTANCE.createEClassBinding();
					binding.setEClass((EClass) selection);
					IEditingDomainItemProvider provider = (IEditingDomainItemProvider) adapterFactory.adapt(editedModel, IEditingDomainItemProvider.class);
					Command cmd = provider.createCommand(editedModel, editingDomain, AddCommand.class, new CommandParameter(editedModel, EditingModelPackage.Literals.PROPERTIES_EDITING_MODEL__BINDINGS, Lists.newArrayList(binding)));
					editingDomain.getCommandStack().execute(cmd);
					EObject eObj = selectionService.unwrapSelection(metamodelViewer.getViewer().getSelection());
					updateBindingSettingsActionsState(eObj);
					refreshPageLayout();
				} else {
					// TODO: log error, I'm unable to find the editingModel!!
				}
			} else if (selection instanceof EStructuralFeature) {
				EClassBinding editedBinding = findCurrentEClassBinding();
				EStructuralFeature structuralFeature = (EStructuralFeature) selection;
				if (editedBinding != null) {
					EStructuralFeatureBinding binding = EditingModelFactory.eINSTANCE.createEStructuralFeatureBinding();
					binding.setFeature(structuralFeature);
					IEditingDomainItemProvider provider = (IEditingDomainItemProvider) adapterFactory.adapt(editedBinding, IEditingDomainItemProvider.class);
					Command cmd = provider.createCommand(editedBinding, editingDomain, AddCommand.class, new CommandParameter(editedBinding, EditingModelPackage.Literals.ECLASS_BINDING__PROPERTY_BINDINGS, Lists.newArrayList(binding)));
					editingDomain.getCommandStack().execute(cmd);
					EObject eObj = selectionService.unwrapSelection(metamodelViewer.getViewer().getSelection());
					updateBindingSettingsActionsState(eObj);
					refreshPageLayout();
				}
			}

		}

	}

	private class DeleteBindingAdapter extends SelectionAdapter {

		private EditingDomain editingDomain;

		public DeleteBindingAdapter(EditingDomain editingDomain) {
			this.editingDomain = editingDomain;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			ISelection selection = bindingSettingsViewer.getSelection();
			if (selection != null && !selection.isEmpty()) {
				EObject binding = selectionService.unwrapSelection(selection);
				Command cmd = editingDomain.createCommand(DeleteCommand.class, new CommandParameter(null, null, Lists.newArrayList(binding)));
				editingDomain.getCommandStack().execute(cmd);
				EObject eObj = selectionService.unwrapSelection(metamodelViewer.getViewer().getSelection());
				updateBindingSettingsActionsState(eObj);
				refreshPageLayout();
			}
		}

	}

}
