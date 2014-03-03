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
package org.eclipse.emf.eef.view;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider.FontAndColorProvider;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction;
import org.eclipse.emf.eef.editor.internal.services.EMFService;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.presentation.util.EditingModelEditorResourceSet;
import org.eclipse.emf.eef.runtime.editingModel.provider.EditingModelItemProviderAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.E3EEFRuntimeUIPlatformPlugin;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EEFReflectiveView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.eef.view.EEFReflexiveView";

	private EMFService emfService;
	private EEFEditingService eefEditingService;
	private SelectionService selectionService;
	private ViewerService viewerService;
	private EditingContextFactoryProvider contextFactoryProvider;
	private ImageManager imageManager;

	private ComposedAdapterFactory adapterFactory;

	private FilteredTree modelViewer;

	private FormToolkit toolkit;

	private Composite pageContainer;

	private Form innerForm;

	private EEFViewer bindingPreviewViewer;

	private EditingDomain editingDomain;

	private SelectionBroker selectionBroker;

	/**
	 * Constructor.
	 */
	public EEFReflectiveView() {
		emfService = new EMFService();
		selectionService = new SelectionService();
		viewerService = new ViewerService(EditingModelEditPlugin.getPlugin().getLockManagerProvider());
		contextFactoryProvider = E3EEFRuntimeUIPlatformPlugin.getPlugin().getContextFactoryProvider();
		eefEditingService = EditingModelEditPlugin.getPlugin().getEEFEditingService();
		imageManager = EditingModelEditPlugin.getPlugin().getImageManager();
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		adapterFactory.addAdapterFactory(new ResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EditingModelItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
		editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack(), new EditingModelEditorResourceSet() {

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
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite composite) {
		toolkit = new FormToolkit(composite.getDisplay());
		innerForm = toolkit.createForm(composite);
		toolkit.decorateFormHeading(innerForm);
		refreshPageTitle();

		Composite parent = innerForm.getBody();
		parent.setLayout(new FillLayout());
		// pageContainer = toolkit.createComposite(parent);
		// pageContainer.setLayout(new FormLayout());

		pageContainer = new SashForm(parent, SWT.HORIZONTAL);
		pageContainer.setLayout(new FillLayout());

		Composite modelSection = createModelSection(toolkit, pageContainer);
		Composite previewSection = createPreviewSection(toolkit, pageContainer);
		layoutPage(modelSection, previewSection);
		initSelectionBroker();
		modelViewer.getViewer().addSelectionChangedListener(selectionBroker);
		modelViewer.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				refreshPageLayout();
			}
		});
		editingDomain.getResourceSet().eAdapters().add(new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(Notification notification) {
				if ((notification.getNotifier() instanceof ResourceSet || notification.getNotifier() instanceof Resource) && (notification.getEventType() == Notification.ADD || notification.getEventType() == Notification.REMOVE)) {
					super.notifyChanged(notification);
					modelViewer.getViewer().refresh();
					bindingPreviewViewer.refresh();
					refreshPageTitle();
				}
			}

		});
	}

	@Override
	public void setFocus() {
		

	}

	private Composite createModelSection(FormToolkit toolkit, Composite container) {
		Section modelSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		modelSection.setText("Models");
		Composite modelContainer = toolkit.createComposite(modelSection);
		modelContainer.setLayout(new GridLayout(1, false));
		createModelViewer(toolkit, modelContainer);
		modelSection.setClient(modelContainer);
		ToolBar actions = new ToolBar(modelSection, SWT.NONE);
		ToolItem load = new ToolItem(actions, SWT.PUSH);
		load.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Load"));
		load.setToolTipText("Load a model in the editor resources");
		load.addSelectionListener(new LoadSelectionAdapter(editingDomain));
		modelSection.setTextClient(actions);

		return modelSection;
	}

	private Composite createPreviewSection(FormToolkit toolkit, Composite container) {
		Section previewSection = toolkit.createSection(container, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		previewSection.setText("Preview");
		Composite previewContainer = toolkit.createComposite(previewSection);
		previewContainer.setLayout(new GridLayout(1, false));
		createPreviewSectionContents(toolkit, previewContainer);
		previewSection.setClient(previewContainer);
		return previewSection;
	}

	private void layoutPage(Composite modelSection, Composite previewSection) {
		FormData modelData = new FormData();
		modelData.top = new FormAttachment(0, 10);
		modelData.bottom = new FormAttachment(100, -10);
		modelData.left = new FormAttachment(0, 10);
		modelData.right = new FormAttachment(30, -5);
		modelSection.setLayoutData(modelData);
		FormData previewData = new FormData();
		previewData.top = new FormAttachment(0, 10);
		previewData.bottom = new FormAttachment(100, -10);
		previewData.left = new FormAttachment(modelSection, 10);
		previewData.right = new FormAttachment(100, -10);
		previewSection.setLayoutData(previewData);
	}

	private void createModelViewer(FormToolkit toolkit, Composite tabFolder) {
		modelViewer = new FilteredTree(tabFolder, SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE, new PatternFilter(), true);
		modelViewer.getViewer().setContentProvider(new AdapterFactoryContentProvider(adapterFactory) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
			 */
			@Override
			public Object[] getElements(Object object) {
				if (object instanceof ResourceSet) {
					List<EObject> result = Lists.newArrayList();
					for (Resource resource : ((ResourceSet) object).getResources()) {
						result.addAll(resource.getContents());
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
		FontAndColorProvider provider = new FontAndColorProvider(adapterFactory, modelViewer.getViewer()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getForeground(java.lang.Object)
			 */
			@Override
			public Color getForeground(Object object) {
				if (object instanceof EPackage || object instanceof EClass || object instanceof EStructuralFeature) {
					if (eefEditingService.referencingEEFElement((EObject) object).isEmpty()) {
						return modelViewer.getViewer().getControl().getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
					}

				}
				return super.getForeground(object);
			}

		};
		modelViewer.getViewer().setLabelProvider(provider);

		int dndOperations = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
		modelViewer.getViewer().addDragSupport(dndOperations, transfers, new ViewerDragAdapter(modelViewer.getViewer()));
		modelViewer.getViewer().addDropSupport(dndOperations, transfers, new org.eclipse.emf.eef.view.ViewerDropAdapter(modelViewer.getViewer()) {

			@Override
			public boolean validateDrop(Object target, int operation, TransferData transferType) {
				return true;
			}

			@Override
			public boolean performDrop(Object data) {
				if (data instanceof Resource) {
					editingDomain.getResourceSet().getResources().add((Resource) data);
				}
				return false;
			}
		});

		modelViewer.getViewer().setInput(editingDomain.getResourceSet());
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		bindingPreviewViewer = new EEFViewer(previewContainer, SWT.BORDER);
		bindingPreviewViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, bindingPreviewViewer);
	}

	private void refreshPageTitle() {
		if (innerForm != null && getEditingDomain() != null) {
			PropertiesEditingModel editedEditingModel = emfService.findEditedEditingModel(getEditingDomain().getResourceSet());
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

	private void initSelectionBroker() {
		selectionBroker = new SelectionBroker(selectionService, viewerService, modelViewer.getViewer());
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	/**
	 * @param editingDomain
	 *            the editingDomain to set
	 */
	public void setEditingDomain(EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
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

	private final class SelectionBroker implements ISelectionChangedListener {

		private SelectionService selectionService;

		private TreeViewer viewer;

		public SelectionBroker(SelectionService selectionService, ViewerService viewerService, TreeViewer metamodelViewer) {
			this.selectionService = selectionService;
			this.viewer = metamodelViewer;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSource() == viewer) {
				EObject selection = selectionService.unwrapSelection(event.getSelection());
				if (selection != null) {
					PropertiesEditingContext propertiesEditingContext = contextFactoryProvider.getEditingContextFactory(selection).createPropertiesEditingContext(adapterFactory, selection);
					propertiesEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
					EClassBinding binding = propertiesEditingContext.getEditingComponent().getBinding();
					PropertiesEditingContext reflectEditingContext = contextFactoryProvider.getEditingContextFactory(binding).createReflectivePropertiesEditingContext(adapterFactory, binding);
					reflectEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
					bindingPreviewViewer.setInput(propertiesEditingContext);
				}
			}

		}

	}

	public void setInput(EditingDomain editingDomain) {
		if (editingDomain == null) {
			setEditingDomain(null);
			modelViewer.getViewer().setInput(new ResourceSetImpl());
		} else {
			setEditingDomain(editingDomain);
			modelViewer.getViewer().setInput(editingDomain.getResourceSet());
		}
	}
}