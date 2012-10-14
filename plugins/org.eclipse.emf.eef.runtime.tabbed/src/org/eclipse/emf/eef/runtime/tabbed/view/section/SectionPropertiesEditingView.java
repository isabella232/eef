/**
 * 
 */
package org.eclipse.emf.eef.runtime.tabbed.view.section;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.tabbed.EEFRuntimeTabbed;
import org.eclipse.emf.eef.runtime.tabbed.internal.view.util.DescriptorHelper;
import org.eclipse.emf.eef.runtime.tabbed.internal.view.util.ValidationMessageInjector;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.adapters.SemanticAdapter;
import org.eclipse.emf.eef.runtime.ui.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManagerImpl;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SectionPropertiesEditingView extends FormImplPropertiesEditingView implements ISection {

	/**
	 * Keep track of the {@link TabbedPropertySheetPage}.
	 */
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	/**
	 * The parent {@link Composite}.
	 */
	private Composite parentComposite;

	/**
	 * EEF Utility to display validation errors in the {@link TabbedPropertySheetPage}.
	 */
	private ValidationMessageInjector injector;

	/**
	 * The current selected object or the first object in the selection when multiple objects are selected.
	 */
	protected EObject eObject;

	/**
	 * The list of current selected objects.
	 */
	protected List<?> eObjectList;

	/**
	 * {@link EditingDomain} to use to edit the {@link EObject}.
	 */
	protected EditingDomain editingDomain;

	/**
	 * {@link AdapterFactory} to use to edit the {@link EObject}.
	 */
	protected AdapterFactory adapterFactory;

	private DescriptorHelper descriptorHelper;

	private ViewHandler<?> viewHandler;

	/**
	 * 
	 */
	public SectionPropertiesEditingView() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.propertyEditors = Maps.newHashMap();
		this.componentRegistry = EEFRuntimeTabbed.getPlugin().getEEFComponentRegistry();
	}


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent,TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		this.parentComposite = parent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		this.editingDomain = getViewService().getEditingDomain(part);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		EObject semanticObject = resolveSemanticObject(((IStructuredSelection)selection).getFirstElement());
		if (semanticObject != null) {
			EObject newEObject = semanticObject;
			if (newEObject != eObject) {
				eObject = newEObject;
				if (eObject != null) {
					disposeComponentIfExist();
					PropertiesEditingContext editingContext = EEFRuntimeTabbed.getPlugin().getEditingContextFactory().createPropertiesEditingContext(editingDomain, adapterFactory, eObject);
					editingContext.getOptions().setOption(UIConstants.FORM_TOOLKIT, tabbedPropertySheetPage.getWidgetFactory());
					editingContext.getOptions().setMessageManager(initMessageManager());
					editingComponent = editingContext.getEditingComponent();
					editingComponent.addEditingListener(this);
					refreshComponent();					
				}
			}
		}
		eObjectList = ((IStructuredSelection)selection).toList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	public void refresh() {
		if (editingComponent != null) {
			init();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#aboutToBeShown()
	 */
	public void aboutToBeShown() { }

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#aboutToBeHidden()
	 */
	public void aboutToBeHidden() {	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		if (injector != null) {
			injector.dispose();
			injector = null;
		}
		if (viewHandler != null) {
			viewHandler.dispose();
		}
		disposeComponentIfExist();
	}


	/**
	 * Dispose and null the editingComponent of the section if it's not null.
	 */
	private void disposeComponentIfExist() {
		if (editingComponent != null) {
			PropertiesEditingContext editingContext = editingComponent.getEditingContext();
			editingContext.dispose();
			editingComponent.dispose();
			editingComponent = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#getMinimumHeight()
	 */
	public int getMinimumHeight() {
		return SWT.DEFAULT;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	public boolean shouldUseExtraSpace() {
		return false;
	}

	/**
	 * This method analyze an input to exact the EObject to edit.
	 * First we try to adapt this object in {@link SemanticAdapter}. If this can't be done, 
	 * we check if this object is an {@link EObject}. Finally, if this object isn't an
	 * {@link EObject}, we try to adapt it in EObject.
	 * @param object element to test
	 * @return the EObject to edit with EEF.
	 */
	protected EObject resolveSemanticObject(Object object) {
		IAdaptable adaptable = null;
		if (object instanceof IAdaptable) {
			adaptable = (IAdaptable)object;
		}
		if (adaptable != null) {
			if (adaptable.getAdapter(SemanticAdapter.class) != null) {
				SemanticAdapter semanticAdapter = (SemanticAdapter)adaptable.getAdapter(SemanticAdapter.class);
				return semanticAdapter.getEObject();
			} 
		}
		if (object instanceof EObject) {
			return (EObject)object;
		} 
		if (adaptable != null) {
			if (adaptable.getAdapter(EObject.class) != null) {
				return (EObject)adaptable.getAdapter(EObject.class);
			}
		}
		return null;
	}

	private void refreshComponent() {
		injector = new ValidationMessageInjector(tabbedPropertySheetPage);
		viewDescriptor = searchViewFromDescriptor();
		if (this.viewDescriptor != null) {
			viewHandler = editingComponent.createViewHandler(viewDescriptor);
			if (viewHandler instanceof PropertiesEditingViewHandler) {
				((PropertiesEditingViewHandler)viewHandler).setView(this);
				if (parentComposite != null) {
					for (Control control : parentComposite.getChildren()) {
						control.dispose();
					}
				}
				setComponentRegistry(((PropertiesEditingViewHandlerProvider) viewHandler.getProvider()).getComponentRegistry());
				createContents(tabbedPropertySheetPage.getWidgetFactory(), parentComposite);
				parentComposite.layout();
				viewHandler.initView(editingComponent);
			}
		}
	}


	protected PropertiesEditingMessageManager initMessageManager() {
		PropertiesEditingMessageManager messageManager = new PropertiesEditingMessageManagerImpl() {

			@Override
			protected void updateStatus(String message) {
				if (injector != null) {
					injector.setMessage(message, IStatus.OK);
				}
			}

			@Override
			protected void updateError(String message) {
				if (injector != null) {
					injector.setMessage(message, IStatus.ERROR);
				}
			}

			@Override
			protected void updateWarning(String message) {
				if (injector != null) {
					injector.setMessage(message, IStatus.WARNING);
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManagerImpl#updateLock(java.lang.String)
			 */
			@Override
			protected void updateLock(String message) {
				if (injector != null) {
					injector.setMessage(message, EEFNotification.LOCK);
				}
			}

		};
		return messageManager;
	}

	private View searchViewFromDescriptor() {
		String descriptor = getDescriptorHelper().getDescriptor();
		for (Object view : editingComponent.getBinding().getViews()) {
			if (view instanceof EObjectView) {
				EObject definition = ((EObjectView) view).getDefinition();
				if (definition instanceof View) {
					String viewName = ((View) definition).getName();
					if (descriptor.equals(viewName)) {
						return (View) definition;
					}
				}
			}
		}
		return null;
	}

	private DescriptorHelper getDescriptorHelper() {
		if (descriptorHelper == null) {
			descriptorHelper = new DescriptorHelper(tabbedPropertySheetPage, this);
		}
		return descriptorHelper;
	}

}
