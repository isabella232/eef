/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.runtime.editingModel.presentation;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.editor.EEFReflectiveEditor;
import org.eclipse.emf.eef.editor.EditingModelEditPlugin;
import org.eclipse.emf.eef.editor.internal.actions.ExtendedLoadResourceAction;
import org.eclipse.emf.eef.editor.internal.binding.settings.EditorBindingSettings;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.presentation.pages.EEFMasterDetailsPage;
import org.eclipse.emf.eef.runtime.editingModel.presentation.util.EditingModelEditorResourceSet;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl;
import org.eclipse.emf.eef.runtime.internal.context.EObjectPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.PropertiesBindingHandlerUIImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifierProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.google.common.base.Function;

/**
 * This is an example of a EditingModel model editor. <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * 
 * @generated NOT
 */
@SuppressWarnings("restriction")
public class EditingModelEditor extends EEFReflectiveEditor {

	/**
	 * Constants
	 */
	public static final String ID = "org.eclipse.emf.eef.editors.EditingModelEditor";

	/**
	 * Master detail page widgets
	 */
	private EEFMasterDetailsPage mdPage;
	private TreeViewer viewer;
	private ToolItem load;

	/**
	 * Editing domain for second tab (models)
	 */
	private EditingDomain editingDomainForOtherModel;

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.editor.EEFReflectiveEditor#createMainPage()
	 */
	@Override
	public EEFMasterDetailsPage createMainPage() {
		mdPage = new EEFMasterDetailsPage(this, "main", getString("_UI_SelectionPage_label")) {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.editingModel.presentation.pages.EEFMasterDetailsPage#addAdditionalViewers()
			 */
			@Override
			protected void addAdditionalViewers() {
				viewer = addTreeViewer("Model");
				viewer.addSelectionChangedListener(new ISelectionChangedListener() {

					/**
					 * Delegate to binding handler in first tab
					 */
					private DelegatingBindingHandlerProvider bindingHandlerProvider;

					/**
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
					 */
					public void selectionChanged(SelectionChangedEvent event) {
						Object selection = getSelectionService().unwrapSelection(event.getSelection());
						if (selection instanceof EObject) {
							final EObjectPropertiesEditingContext propertiesEditingContext = (EObjectPropertiesEditingContext) EditingModelEditPlugin.getPlugin().getEditingContextFactoryProvider().getEditingContextFactory((EObject) selection)
									.createPropertiesEditingContext(adapterFactory, (EObject) selection);
							propertiesEditingContext.setBindingManagerProvider(getBindingHandlerProvider(propertiesEditingContext));
							propertiesEditingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, mdPage.getToolkit());
							updateDetailsViewer(propertiesEditingContext);
						}

					}

					/**
					 * @param propertiesEditingContext
					 * @return the bindingHandlerProvider
					 */
					public DelegatingBindingHandlerProvider getBindingHandlerProvider(EObjectPropertiesEditingContext propertiesEditingContext) {
						if (bindingHandlerProvider == null) {
							bindingHandlerProvider = new DelegatingBindingHandlerProvider(propertiesEditingContext.getEMFServiceProvider(), propertiesEditingContext.getBindingManagerProvider(), getEditingDomain());
						}
						return bindingHandlerProvider;
					}
				});
				viewer.setInput(getEditingDomainForOtherModel().getResourceSet());
			}

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.editingModel.presentation.pages.EEFMasterDetailsPage#createFormContent(org.eclipse.ui.forms.IManagedForm)
			 */
			@Override
			protected void createFormContent(IManagedForm managedForm) {
				super.createFormContent(managedForm);

				// add toolbar for load action
				ToolBar actions = new ToolBar(getModelSection(), SWT.NONE);
				load = new ToolItem(actions, SWT.PUSH);
				load.setImage(getImageManager().getImage(EEFRuntimeUISWT.getResourceLocator(), "Load"));
				load.setToolTipText("Load a model in the editor resources");
				load.addSelectionListener(new LoadSelectionAdapter(EditingModelEditor.this, viewer));
				getModelSection().setTextClient(actions);
			}

		};
		mdPage.setAdapterFactory(getAdapterFactory());

		return mdPage;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.editor.EEFReflectiveEditor#createModel()
	 */
	@Override
	public void createModel() {
		super.createModel();
		// create another editing domain for second tab
		createEditingDomainForOtherModels();
	}

	/**
	 * Loads a Ecore resource into the given {@link EditingDomain}.
	 * 
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 */
	private static class LoadSelectionAdapter extends SelectionAdapter {

		private ExtendedLoadResourceAction action;
		private EditingModelEditor view;
		private TreeViewer viewer;

		/**
		 * @param viewer
		 * @param domain
		 *            the {@link EditingDomain} to load.
		 */
		public LoadSelectionAdapter(EditingModelEditor view, TreeViewer viewer) {
			this.view = view;
			this.viewer = viewer;
			this.action = new ExtendedLoadResourceAction();
			this.view = view;
			action.setEditingDomain(view.getEditingDomainForOtherModel());
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (view.getEditingDomainForOtherModel() == null) {
				if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
					IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
					if (activeEditor instanceof EditingModelEditor) {
						viewer.setInput(((EditingModelEditor) activeEditor).getEditingDomainForOtherModel());
						action.setEditingDomain(((EditingModelEditor) activeEditor).getEditingDomainForOtherModel());
					}
				}
			}
			action.setEditingDomain(view.getEditingDomainForOtherModel());
			action.run();
		}

	}

	/**
	 * Create editing domain for other models
	 */
	public void createEditingDomainForOtherModels() {
		BasicCommandStack commandStack = new BasicCommandStack();
		editingDomainForOtherModel = new AdapterFactoryEditingDomain(adapterFactory, commandStack, new EditingModelEditorResourceSet() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
			 */
			public EditingDomain getEditingDomain() {
				return editingDomainForOtherModel;
			}

		});
	}

	/**
	 * @return the editingDomainForOtherModel
	 */
	public EditingDomain getEditingDomainForOtherModel() {
		return editingDomainForOtherModel;
	}

	/**
	 * @param editingDomainForOtherModel
	 *            the editingDomainForOtherModel to set
	 */
	public void setEditingDomainForOtherModel(AdapterFactoryEditingDomain editingDomainForOtherModel) {
		this.editingDomainForOtherModel = editingDomainForOtherModel;
	}

	private class DelegatingBindingHandlerProvider implements BindingHandlerProvider {

		private EMFServiceProvider emfServiceProvider;
		private DelegatingBindingHandler bindingHandler;
		private BindingHandlerProvider delegatedBindingHandlerProvider;
		private EditingDomain editingDomain;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param bindingHandlerProvider
		 * @param editingDomain
		 */
		public DelegatingBindingHandlerProvider(EMFServiceProvider emfServiceProvider, BindingHandlerProvider bindingHandlerProvider, EditingDomain editingDomain) {
			this.emfServiceProvider = emfServiceProvider;
			this.delegatedBindingHandlerProvider = bindingHandlerProvider;
			this.editingDomain = editingDomain;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider#getBindingHandler(org.eclipse.emf.ecore.EObject)
		 */
		public PropertiesBindingHandler getBindingHandler(EObject target) {
			if (bindingHandler == null) {
				bindingHandler = new DelegatingBindingHandler(emfServiceProvider, (PropertiesBindingHandlerImpl) delegatedBindingHandlerProvider.getBindingHandler(target), editingDomain);
			}
			return bindingHandler;
		}

	}

	private class DelegatingBindingHandler extends PropertiesBindingHandlerUIImpl {

		private EEFBindingSettingsProvider bindingSettingsProvider;
		private PropertiesBindingHandlerImpl propertiesBindingHandler;
		private EditingDomain editingDomain;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param propertiesBindingHandler
		 * @param delegatedBindingHandlerProvider
		 */
		public DelegatingBindingHandler(EMFServiceProvider emfServiceProvider, PropertiesBindingHandlerImpl propertiesBindingHandler, EditingDomain editingDomain) {
			this.propertiesBindingHandler = propertiesBindingHandler;
			this.editingDomain = editingDomain;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getBindingSettingsProvider()
		 */
		public EEFBindingSettingsProvider getBindingSettingsProvider() {
			if (bindingSettingsProvider == null) {
				bindingSettingsProvider = new DelegatingBindingSettingsProvider(getEMFServiceProvider(), propertiesBindingHandler.getBindingSettingsProvider(), editingDomain);
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
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getViewHandlerProvider()
		 */
		@Override
		public ViewHandlerProvider getViewHandlerProvider() {
			return propertiesBindingHandler.getViewHandlerProvider();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getEditingPolicyProvider()
		 */
		@Override
		public PropertiesEditingPolicyProvider getEditingPolicyProvider() {
			return propertiesBindingHandler.getEditingPolicyProvider();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.internal.binding.PropertiesBindingHandlerImpl#getEEFNotifierProvider()
		 */
		@Override
		public EEFNotifierProvider getEEFNotifierProvider() {
			return propertiesBindingHandler.getEEFNotifierProvider();
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
		private EditingDomain editingDomain;

		/**
		 * @param emfServiceProvider
		 *            EMFServiceProvider
		 * @param eefBindingSettingsProvider
		 */
		public DelegatingBindingSettingsProvider(EMFServiceProvider emfServiceProvider, EEFBindingSettingsProvider eefBindingSettingsProvider, EditingDomain editingDomain) {
			this.emfServiceProvider = emfServiceProvider;
			this.eefBindingSettingsProvider = eefBindingSettingsProvider;
			this.editingDomain = editingDomain;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider#getBindingSettings(org.eclipse.emf.ecore.EPackage)
		 */
		@SuppressWarnings("unchecked")
		public <T extends EObject> EEFBindingSettings<T> getBindingSettings(EPackage ePackage) {
			EditorBindingSettings editorBindingSettings = new EditorBindingSettings(eefBindingSettingsProvider, editingDomain);
			editorBindingSettings.setEMFServiceProvider(emfServiceProvider);
			return (EEFBindingSettings<T>) editorBindingSettings;
		}

	}
}
