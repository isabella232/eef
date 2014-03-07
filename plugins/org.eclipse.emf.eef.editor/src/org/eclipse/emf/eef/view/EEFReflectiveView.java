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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider.FontAndColorProvider;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction;
import org.eclipse.emf.eef.editor.internal.binding.settings.EditorBindingSettings;
import org.eclipse.emf.eef.editor.internal.notify.Notifiable;
import org.eclipse.emf.eef.editor.internal.services.SelectionService;
import org.eclipse.emf.eef.editor.internal.services.ViewerService;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.provider.EditingModelItemProviderAdapterFactory;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl;
import org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.E3EEFRuntimeUIPlatformPlugin;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.PropertiesBindingHandlerUIImpl;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class EEFReflectiveView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.eef.view.EEFReflexiveView";

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

	private ToolItem load;

	/**
	 * Constructor.
	 */
	public EEFReflectiveView() {
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
		// innerForm.setText("Bindings");

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

		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (activeEditor instanceof EEFReflectiveEditor) {
				if (!((EEFReflectiveEditor) activeEditor).containsNotifiableView()) {
					((EEFReflectiveEditor) activeEditor).addNotifiable(new Notifiable() {

						public void notifyChanged(final Notification notification) {

							final EEFReflectiveView eefView = (EEFReflectiveView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(EEFReflectiveView.ID);
							if (eefView != null) {
								bindingPreviewViewer.getControl().getDisplay().asyncExec(new Runnable() {

									public void run() {
										eefView.refresh();
									}
								});
							}
						}

						public int getIndex() {
							return -1;
						}
					});
				}
			}
		}

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
		load = new ToolItem(actions, SWT.PUSH);
		load.setImage(imageManager.getImage(EEFRuntimeUISWT.getResourceLocator(), "Load"));
		load.setToolTipText("Load a model in the editor resources");
		load.addSelectionListener(new LoadSelectionAdapter(this));
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
		setInput(editingDomain);
	}

	private void createPreviewSectionContents(FormToolkit toolkit, Composite previewContainer) {
		bindingPreviewViewer = new EEFViewer(previewContainer, SWT.BORDER);
		bindingPreviewViewer.setContentProvider(new EEFContentProvider());
		viewerService.updateViewerBackground(toolkit, bindingPreviewViewer);
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
		private EEFReflectiveView view;

		/**
		 * @param domain
		 *            the {@link EditingDomain} to load.
		 */
		public LoadSelectionAdapter(EEFReflectiveView view) {
			this.view = view;
			this.action = new ExtendedLoadResourceAction();
			this.view = view;
			action.setEditingDomain(view.getEditingDomain());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (view.getEditingDomain() == null) {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
					IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					if (activeEditor instanceof EEFReflectiveEditor) {
						view.setInput(((EEFReflectiveEditor) activeEditor).getEditingDomainForOtherModel());
						action.setEditingDomain(((EEFReflectiveEditor) activeEditor).getEditingDomainForOtherModel());
					}
				}
			}
			if (!view.getEditingDomain().equals(action.getEditingDomain())) {
				action.setEditingDomain(view.getEditingDomain());
			}
			action.run();
		}

	}

	private final class SelectionBroker implements ISelectionChangedListener {

		private SelectionService selectionService;

		private TreeViewer viewer;

		private DelegatingBindingHandlerProvider bindingHandlerProvider;

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
					final EObjectPropertiesEditingContext propertiesEditingContext = (EObjectPropertiesEditingContext) contextFactoryProvider.getEditingContextFactory(selection).createPropertiesEditingContext(adapterFactory, selection);
					propertiesEditingContext.setBindingManagerProvider(getBindingHandlerProvider(propertiesEditingContext));
					propertiesEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, toolkit);
					bindingPreviewViewer.setInput(propertiesEditingContext);
				}
			}

		}

		/**
		 * @param propertiesEditingContext
		 * @return the bindingHandlerProvider
		 */
		public DelegatingBindingHandlerProvider getBindingHandlerProvider(EObjectPropertiesEditingContext propertiesEditingContext) {
			if (bindingHandlerProvider == null) {
				bindingHandlerProvider = new DelegatingBindingHandlerProvider(propertiesEditingContext.getEMFServiceProvider(), propertiesEditingContext.getBindingManagerProvider());
			}
			return bindingHandlerProvider;
		}

	}

	public void setInput(EditingDomain editingDomain) {
		if (editingDomain == null) {
			setEditingDomain(null);
			modelViewer.getViewer().setInput(new ResourceSetImpl());
			if (bindingPreviewViewer != null) {
				bindingPreviewViewer.setInput(null);
			}
		} else {
			setEditingDomain(editingDomain);
			modelViewer.getViewer().setInput(editingDomain.getResourceSet());
			getEditingDomain().getResourceSet().eAdapters().add(new EContentAdapter() {

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
					}
				}

			});
			if (bindingPreviewViewer != null) {
				bindingPreviewViewer.setInput(null);
			}
		}
	}

	/**
	 * Refresh
	 */
	public void refresh() {
		bindingPreviewViewer.refresh();
	}

	private class DelegatingBindingHandlerProvider implements BindingHandlerProvider {

		private EMFServiceProvider emfServiceProvider;
		private DelegatingBindingHandler bindingHandler;
		private BindingHandlerProvider delegatedBindingHandlerProvider;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param bindingHandlerProvider
		 */
		public DelegatingBindingHandlerProvider(EMFServiceProvider emfServiceProvider, BindingHandlerProvider bindingHandlerProvider) {
			this.emfServiceProvider = emfServiceProvider;
			this.delegatedBindingHandlerProvider = bindingHandlerProvider;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider#getBindingHandler(org.eclipse.emf.ecore.EObject)
		 */
		public PropertiesBindingHandler getBindingHandler(EObject target) {
			if (bindingHandler == null) {
				bindingHandler = new DelegatingBindingHandler(emfServiceProvider, (PropertiesBindingHandlerImpl) delegatedBindingHandlerProvider.getBindingHandler(target));
			}
			return bindingHandler;
		}

	}

	private class DelegatingBindingHandler extends PropertiesBindingHandlerUIImpl {

		private EEFBindingSettingsProvider bindingSettingsProvider;
		private PropertiesBindingHandlerImpl propertiesBindingHandler;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param propertiesBindingHandler
		 * @param delegatedBindingHandlerProvider
		 */
		public DelegatingBindingHandler(EMFServiceProvider emfServiceProvider, PropertiesBindingHandlerImpl propertiesBindingHandler) {
			this.propertiesBindingHandler = propertiesBindingHandler;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getBindingSettingsProvider()
		 */
		public EEFBindingSettingsProvider getBindingSettingsProvider() {
			if (bindingSettingsProvider == null) {
				bindingSettingsProvider = new DelegatingBindingSettingsProvider(getEMFServiceProvider(), propertiesBindingHandler.getBindingSettingsProvider());
			}
			return bindingSettingsProvider;
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getEventAdmin()
		 */
		public EventAdmin getEventAdmin() {
			return propertiesBindingHandler.getEventAdmin();
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getEMFServiceProvider()
		 */
		public EMFServiceProvider getEMFServiceProvider() {
			return propertiesBindingHandler.getEMFServiceProvider();
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getEEFEditingServiceProvider()
		 */
		public EEFEditingServiceProvider getEEFEditingServiceProvider() {
			return propertiesBindingHandler.getEEFEditingServiceProvider();
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getLockPolicyFactoryProvider()
		 */
		public EEFLockPolicyFactoryProvider getLockPolicyFactoryProvider() {
			return propertiesBindingHandler.getLockPolicyFactoryProvider();
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getLockManagerProvider()
		 */
		public EEFLockManagerProvider getLockManagerProvider() {
			return propertiesBindingHandler.getLockManagerProvider();
		}

		/**
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getPolicyProvider()
		 */
		public PropertiesEditingPolicyProvider getPolicyProvider() {
			return propertiesBindingHandler.getPolicyProvider();
		}

		/**
		 * @param editingContext
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#createComponent(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
		 */
		public PropertiesEditingComponent createComponent(PropertiesEditingContext editingContext) {
			return super.createComponent(editingContext);
		}

		/**
		 * @param component
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
		 */
		public void disposeComponent(PropertiesEditingComponent component) {
			super.disposeComponent(component);
		}

		/**
		 * @param view
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getLockManager(java.lang.Object)
		 */
		public EEFLockManager getLockManager(Object view) {
			return propertiesBindingHandler.getLockManager(view);
		}

		/**
		 * @param editingComponent
		 * @param event
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#delayedApplyingPropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
		 */
		public void delayedApplyingPropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent event) {
			super.delayedApplyingPropertiesChanged(editingComponent, event);
		}

		/**
		 * @param obj
		 * @return
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		/**
		 * @param editingComponent
		 * @param editingEvent
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
		 */
		public void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
			super.firePropertiesChanged(editingComponent, editingEvent);
		}

		/**
		 * @param editingComponent
		 * @param lockEvent
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#fireLockChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
		 */
		public void fireLockChanged(PropertiesEditingComponent editingComponent, EEFLockEvent lockEvent) {
			super.fireLockChanged(editingComponent, lockEvent);
		}

		/**
		 * @param editingComponent
		 * @param editingPolicy
		 * @param policyEditingContext
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#execute(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy,
		 *      org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
		 */
		public void execute(PropertiesEditingComponent editingComponent, PropertiesEditingPolicy editingPolicy, PropertiesEditingContext policyEditingContext) {
			super.execute(editingComponent, editingPolicy, policyEditingContext);
		}

		/**
		 * @param editingComponent
		 * @param function
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#executeOnViews(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      com.google.common.base.Function)
		 */
		public void executeOnViews(PropertiesEditingComponent editingComponent, Function<Object, Void> function) {
			super.executeOnViews(editingComponent, function);
		}

		/**
		 * @param event
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#handleEvent(org.osgi.service.event.Event)
		 */
		public void handleEvent(Event event) {
			super.handleEvent(event);
		}

		/**
		 * @return
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return super.hashCode();
		}

		/**
		 * @param eventAdmin
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setEventAdmin(org.osgi.service.event.EventAdmin)
		 */
		public void setEventAdmin(EventAdmin eventAdmin) {
			propertiesBindingHandler.setEventAdmin(eventAdmin);
		}

		/**
		 * @param bindingSettingsProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setBindingSettingsProvider(org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider)
		 */
		public void setBindingSettingsProvider(EEFBindingSettingsProvider bindingSettingsProvider) {
			propertiesBindingHandler.setBindingSettingsProvider(bindingSettingsProvider);
		}

		/**
		 * @param editingPolicyProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setEditingPolicyProvider(org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider)
		 */
		public void setEditingPolicyProvider(PropertiesEditingPolicyProvider editingPolicyProvider) {
			propertiesBindingHandler.setEditingPolicyProvider(editingPolicyProvider);
		}

		/**
		 * @param emfServiceProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setEMFServiceProvider(org.eclipse.emf.eef.runtime.util.EMFServiceProvider)
		 */
		public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
			propertiesBindingHandler.setEMFServiceProvider(emfServiceProvider);
		}

		/**
		 * @param eefEditingServiceProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setEEFEditingServiceProvider(org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider)
		 */
		public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
			propertiesBindingHandler.setEEFEditingServiceProvider(eefEditingServiceProvider);
		}

		/**
		 * @param eefNotifierProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setEEFNotifierProvider(org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider)
		 */
		public void setEEFNotifierProvider(EEFNotifierProvider eefNotifierProvider) {
			propertiesBindingHandler.setEEFNotifierProvider(eefNotifierProvider);
		}

		/**
		 * @param lockPolicyFactoryProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setLockPolicyFactoryProvider(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider)
		 */
		public void setLockPolicyFactoryProvider(EEFLockPolicyFactoryProvider lockPolicyFactoryProvider) {
			propertiesBindingHandler.setLockPolicyFactoryProvider(lockPolicyFactoryProvider);
		}

		/**
		 * @param lockManagerProvider
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#setLockManagerProvider(org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider)
		 */
		public void setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
			propertiesBindingHandler.setLockManagerProvider(lockManagerProvider);
		}

		/**
		 * @param element
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#serviceFor(org.eclipse.emf.ecore.EObject)
		 */
		public boolean serviceFor(EObject element) {
			return propertiesBindingHandler.serviceFor(element);
		}

		/**
		 * @param editingComponent
		 * @param msg
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#notifyModelChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.common.notify.Notification)
		 */
		public void notifyModelChanged(PropertiesEditingComponent editingComponent, Notification msg) {
			super.notifyModelChanged(editingComponent, msg);
		}

		/**
		 * @param editingComponent
		 * @param msg
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#notifySettingsChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
		 *      org.eclipse.emf.common.notify.Notification)
		 */
		public void notifySettingsChanged(PropertiesEditingComponent editingComponent, Notification msg) {
			super.notifySettingsChanged(editingComponent, msg);
		}

		/**
		 * @param editingComponent
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#initLockPolicies(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
		 */
		public void initLockPolicies(PropertiesEditingComponent editingComponent) {
			super.initLockPolicies(editingComponent);
		}

		/**
		 * @param notifier
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#initModelChangesNotifierIfNeeded(org.eclipse.emf.common.notify.Notifier)
		 */
		public void initModelChangesNotifierIfNeeded(Notifier notifier) {
			super.initModelChangesNotifierIfNeeded(notifier);
		}

		/**
		 * @param editingComponent
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#registerEditingComponentAsEventHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
		 */
		public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
			super.registerEditingComponentAsEventHandler(editingComponent);
		}

		/**
		 * @return
		 * @see java.lang.Object#toString()
		 */
		public String toString() {
			return super.toString();
		}

		/**
		 * @param editingComponent
		 * @return
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#validate(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
		 */
		public Diagnostic validate(PropertiesEditingComponent editingComponent) {
			return super.validate(editingComponent);
		}

		/**
		 * @param editingComponent
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#unregisterEditingComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
		 */
		public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
			super.unregisterEditingComponent(editingComponent);
		}

	}

	private class DelegatingBindingSettingsProvider implements EEFBindingSettingsProvider {

		private EMFServiceProvider emfServiceProvider;
		private EEFBindingSettingsProvider eefBindingSettingsProvider;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param eefBindingSettingsProvider
		 */
		public DelegatingBindingSettingsProvider(EMFServiceProvider emfServiceProvider, EEFBindingSettingsProvider eefBindingSettingsProvider) {
			this.emfServiceProvider = emfServiceProvider;
			this.eefBindingSettingsProvider = eefBindingSettingsProvider;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider#getBindingSettings(org.eclipse.emf.ecore.EPackage)
		 */
		public <T extends EObject> EEFBindingSettings<T> getBindingSettings(EPackage ePackage) {
			EditorBindingSettings editorBindingSettings = new EditorBindingSettings(eefBindingSettingsProvider);
			editorBindingSettings.setEMFServiceProvider(emfServiceProvider);
			return (EEFBindingSettings<T>) editorBindingSettings;
		}

	}

}
