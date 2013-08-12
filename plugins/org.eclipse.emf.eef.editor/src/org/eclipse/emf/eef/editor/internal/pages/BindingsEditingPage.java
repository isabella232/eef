/**
 * 
 */
package org.eclipse.emf.eef.editor.internal.pages;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider.FontAndColorProvider;
import org.eclipse.emf.eef.UIConstants;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.editor.internal.services.util.ViewLockingSettings;
import org.eclipse.emf.eef.editor.internal.widgets.MultiEEFViewer;
import org.eclipse.emf.eef.editor.internal.widgets.MultiEEFViewer.SelectionInterpreter;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
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
	
	private TreeViewer metamodelViewer;
	private TreeViewer modelViewer;
	private MultiEEFViewer bindingSettingsViewer;

	private SelectionBroker selectionBroker;
	private FormToolkit toolkit;

	/**
	 * @param editor
	 * @param adapterFactory 
	 */
	public BindingsEditingPage(FormEditor editor, AdapterFactory adapterFactory) {
		super(editor, "eef-binding-editing", "Bindings");
		this.adapterFactory = adapterFactory;
	}
	
	/**
	 * @param emfService the emfService to set
	 */
	public void setEMFService(EMFService emfService) {
		this.emfService = emfService;
	}

	/**
	 * @param eefEditingService the eefEditingService to set
	 */
	public void setEEFEditingService(EEFEditingService eefEditingService) {
		this.eefEditingService = eefEditingService;
	}

	/**
	 * @param selectionService the selectionService to set
	 */
	public void setSelectionService(SelectionService selectionService) {
		this.selectionService = selectionService;
	}

	/**
	 * @param editingContextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider editingContextFactoryProvider) {
		this.contextFactoryProvider = editingContextFactoryProvider;
	}

	/**
	 * @param viewerService the viewerService to set
	 */
	public void setViewerService(ViewerService viewerService) {
		this.viewerService = viewerService;
	}

	/**
	 * @param imageManager the imageManager to set
	 */
	public void setImageManager(ImageManager imageManager) {
		this.imageManager = imageManager;
	}

	/**
	 * Defines the page input.
	 * @param input page input.
	 */
	public void setInput(Object input) {
		if (input instanceof ResourceSet) {
			this.input = (ResourceSet)input;
			if (metamodelViewer != null) {
				Collection<Resource> ecoreResources = emfService.ecoreResources(this.input);
				metamodelViewer.setInput(ecoreResources);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.forms.editor.FormPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void createFormContent(IManagedForm managedForm) {
		toolkit = managedForm.getToolkit();
		Form form = managedForm.getForm().getForm();
		toolkit.decorateFormHeading(form);
		Composite parent = form.getBody();
		parent.setLayout(new FillLayout());
		final Composite container = toolkit.createComposite(parent);
		container.setLayout(new FormLayout());
		Composite modelSection = createModelSection(toolkit, container);
		Composite bindingSettingsSection = createBindingSettingsSection(toolkit, container);
		Composite previewSection = createPreviewSection(toolkit, container);
		layoutPage(modelSection, bindingSettingsSection, previewSection);
		initSelectionBroker();
		metamodelViewer.addSelectionChangedListener(selectionBroker);
		metamodelViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			public void selectionChanged(SelectionChangedEvent event) {
				container.layout(true);
				container.getParent().layout(true);
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
		load.setImage(imageManager.getImage(EditingModelEditPlugin.getPlugin(), "Load"));
		load.setToolTipText("Load a model in the editor resources");
		load.addSelectionListener(new LoadSelectionAdapter(((IEditingDomainProvider)getEditor()).getEditingDomain()));
		final ToolItem filterModels = new ToolItem(actions, SWT.CHECK);
		filterModels.setImage(imageManager.getImage(EditingModelEditPlugin.getPlugin(), "Filter"));
		filterModels.setToolTipText("Only show ecore models referenced by an EEF editing model");
		filterModels.setSelection(true);
		filterModels.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				metamodelViewer.refresh();
			}
		});
		metamodelViewer.addFilter(new ViewerFilter() {
			
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
		ToolBar actions = new ToolBar(bindingSettingsSection, SWT.NONE);
		ToolItem add = new ToolItem(actions, SWT.PUSH);
		// FIXME: What the heck with the delegatingResourceLocator !!!
		add.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Add"));
		ToolItem remove = new ToolItem(actions, SWT.PUSH);
		remove.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Delete"));
		bindingSettingsSection.setTextClient(actions);
		Composite bindingSettingsContainer = toolkit.createComposite(bindingSettingsSection);
		bindingSettingsContainer.setLayout(new GridLayout(1, false));
		createBindingSettingsSectionContents(toolkit, bindingSettingsContainer);
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
		tabFolder.setSelectionBackground(
				new Color[] {toolkit.getColors().getColor(IFormColors.H_GRADIENT_START), toolkit.getColors().getColor(IFormColors.H_GRADIENT_END)}, 
				new int[] {50}, 
				true);
		tabFolder.setForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		tabFolder.setSelectionForeground(toolkit.getColors().getColor(IFormColors.TITLE));
		GridData layoutData = new GridData(GridData.FILL_BOTH);
		layoutData.widthHint = UIConstants.WIDTH_HINT;
		tabFolder.setLayoutData(layoutData);
		createMetamodelViewer(toolkit, tabFolder);
		createModelViewer(toolkit, tabFolder);
	}

	private void createMetamodelViewer(FormToolkit toolkit, CTabFolder tabFolder) {
		final Tree metamodelTree = toolkit.createTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE);
		metamodelViewer = new TreeViewer(metamodelTree);
		metamodelViewer.setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			/**
			 * {@inheritDoc}
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
			
		});
		FontAndColorProvider provider = new FontAndColorProvider(adapterFactory, metamodelViewer) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getForeground(java.lang.Object)
			 */
			@Override
			public Color getForeground(Object object) {
				if (object instanceof EPackage || object instanceof EClass || object instanceof EStructuralFeature) {
					if (eefEditingService.referencingEEFElement((EObject) object).isEmpty()) {
						return metamodelTree.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
					}

				}
				return super.getForeground(object);
			}
			
		};
		metamodelViewer.setLabelProvider(provider);
		metamodelViewer.addFilter(new ViewerFilter() {
			
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				return element instanceof EObject && eefEditingService.canBeReferencedByEditingModel((EObject) element);
			}
		});
		if (input != null) {
			ResourceSet resourceSet = ((IEditingDomainProvider)getEditor()).getEditingDomain().getResourceSet();
			EcoreUtil.resolveAll(resourceSet);
			metamodelViewer.setInput(resourceSet);
		}
		CTabItem metamodelItem = new CTabItem(tabFolder, SWT.NONE);
		metamodelItem.setControl(metamodelTree);
		metamodelItem.setText("Metamodel");
	}

	private void createModelViewer(FormToolkit toolkit, CTabFolder tabFolder) {
		Tree modelTree = toolkit.createTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL);
		modelViewer = new TreeViewer(modelTree);
		CTabItem modelItem = new CTabItem(tabFolder, SWT.NONE);
		modelItem.setControl(modelTree);
		modelItem.setText("Model");
	}

	private void createBindingSettingsSectionContents(FormToolkit toolkit, Composite bindingSettingsContainer) {
		bindingSettingsViewer = new MultiEEFViewer(bindingSettingsContainer, SWT.NONE);
		bindingSettingsViewer.setContentProvider(new EEFContentProvider());
		bindingSettingsViewer.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		bindingSettingsViewer.setSelectionInterpreter(new SelectionInterpreter() {
			
			public PropertiesEditingContext createContextFromSelection(ISelection selection) {
				EObject selectedElement = selectionService.unwrapSelection(selection);
				if (selectedElement != null) {
					EditingDomain editingDomain = ((EEFReflectiveEditor)getEditor()).getEditingDomain();
					PropertiesEditingContext context = contextFactoryProvider.getEditingContextFactory(selectedElement).createPropertiesEditingContext(editingDomain, adapterFactory, selectedElement);
					context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, BindingsEditingPage.this.toolkit);
					return context;
				}
				return null;
			}
		});
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		toolkit.createLabel(previewContainer, "Preview");		
	}
	
	/**
	 * @return the selectionBroker
	 */
	public void initSelectionBroker() {
		selectionBroker = new SelectionBroker(metamodelViewer, bindingSettingsViewer, selectionService, viewerService);
	}

	private static final class SelectionBroker implements ISelectionChangedListener {

		private static final String BINDING_VIEW_ID = "editingModel::Binding";
		private static final String BINDING_E_CLASS_EDITOR_ID = "editingModel::Binding::eClass";

		private TreeViewer metamodelViewer;
		private MultiEEFViewer bindingSettingsViewer;
		private SelectionService selectionService;
		private ViewerService viewerService;
		
		public SelectionBroker(TreeViewer metamodelViewer, MultiEEFViewer bindingSettingsViewer, SelectionService selectionService, ViewerService viewerService) {
			this.metamodelViewer = metamodelViewer;
			this.bindingSettingsViewer = bindingSettingsViewer;
			this.selectionService = selectionService;
			this.viewerService = viewerService;
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == metamodelViewer) {
				EObject selection = selectionService.unwrapSelection(event.getSelection());
				bindingSettingsViewer.setInput(selection);
				lockFields();
			}
			
		}
		
		public void lockFields() {
			EEFViewer subViewer = bindingSettingsViewer.getSubViewer();
			Object input = subViewer.getInput();
			if (input instanceof PropertiesEditingContext) {
				PropertiesEditingComponent editingComponent = ((PropertiesEditingContext) input).getEditingComponent();
				ViewLockingSettings lockingSettings = ViewLockingSettings.builder()
																			.addLockSettings(BINDING_VIEW_ID, BINDING_E_CLASS_EDITOR_ID)
																			.build();
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
		 * @param domain the {@link EditingDomain} to load.
		 */
		public LoadSelectionAdapter(EditingDomain domain) {
			this.action = new ExtendedLoadResourceAction();
			action.setEditingDomain(domain);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			action.run();
		}
		
	}

}
