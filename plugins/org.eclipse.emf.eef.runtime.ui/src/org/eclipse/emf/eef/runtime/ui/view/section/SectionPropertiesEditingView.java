/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.section;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.DomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.notify.EditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.adapters.SemanticAdapter;
import org.eclipse.emf.eef.runtime.ui.internal.view.impl.FormImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.PropertiesEditingMessageManager;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ValidationMessageInjector;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandler;
import org.eclipse.emf.eef.runtime.ui.view.handlers.editingview.PropertiesEditingViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

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
	 * Validation message manager for the {@link Section}.
	 */
	private PropertiesEditingMessageManager messageManager;

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

	/**
	 * 
	 */
	public SectionPropertiesEditingView() {
		adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.propertyEditors = new HashMap<ElementEditor, PropertyEditor>();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		this.parentComposite = parent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		this.editingDomain = getViewHelper().getEditingDomain(part);
		if (!(selection instanceof IStructuredSelection)) {
			return;
		}
		EObject semanticObject = resolveSemanticObject(((IStructuredSelection)selection).getFirstElement());
		if (semanticObject != null) {
			EObject newEObject = semanticObject;
			if (newEObject != eObject) {
				eObject = newEObject;
				if (eObject != null) {
					injector = new ValidationMessageInjector(tabbedPropertySheetPage);
					messageManager = new PropertiesEditingMessageManager() {

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

					};
					dispose();
					refreshComponent();
				}
			}
		}
		eObjectList = ((IStructuredSelection)selection).toList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#aboutToBeShown()
	 */
	public void aboutToBeShown() {
		/* empty default implementation */
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#aboutToBeHidden()
	 */
	public void aboutToBeHidden() {
		if (injector != null) {
			injector.dispose();
			injector = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	public void dispose() {
		if (editingComponent != null) {
			PropertiesEditingContext editingContext = editingComponent.getEditingContext();
			editingComponent.dispose();
			editingContext.dispose();
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
	 * {@inheritDoc}
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	public void refresh() {
		init();
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
		DomainPropertiesEditingContext editingContext = new DomainPropertiesEditingContext(editingDomain, adapterFactory, eObject);
		editingContext.getOptions().setOption(UIConstants.FORM_TOOLKIT, tabbedPropertySheetPage.getWidgetFactory());
		editingComponent = editingContext.getEditingComponent();
		initToolkit();
		editingComponent.addEditingListener(this);
		viewDescriptor = searchViewFromDescriptor();
		if (this.viewDescriptor != null) {
			ViewHandler<?> viewHandler = editingComponent.getViewHandler(viewDescriptor);
			if (viewHandler instanceof PropertiesEditingViewHandler) {
				((PropertiesEditingViewHandler)viewHandler).setView(this);
				if (parentComposite != null) {
					for (Control control : parentComposite.getChildren()) {
						control.dispose();
					}
				}
				setPropertyEditorProvider(((PropertiesEditingViewHandlerProvider) viewHandler.getProvider()).getPropertyEditorProvider());
				createContents(tabbedPropertySheetPage.getWidgetFactory(), parentComposite);
				if (messageManager != null) {
					messageManager.processMessage(new PropertiesValidationEditingEvent(null, Diagnostic.OK_INSTANCE));
					// I think I can create many dead EditingListener like this.
					editingComponent.addEditingListener(new EditingListener() {

						public void firePropertiesChanged(PropertiesEditingEvent event) {
							messageManager.processMessage(event);
						}
					});
				}
			}
		}
	}

	private View searchViewFromDescriptor() {
		for (Object view : editingComponent.getBinding().getViews()) {
			if (view instanceof EObjectView) {
				EObject definition = ((EObjectView) view).getDefinition();
				if (definition instanceof View && getDescriptor().equals(((View) definition).getName())) {
					return (View) definition;
				}
			}
		}
		return null;
	}

	/**
	 * Magic method For eclipse 3.2 & 3.3 & 3.4 & 3.5
	 * 
	 * @return
	 */
	protected String getDescriptor() {
		Map<?, ?> descriptor = getPageDescriptor(tabbedPropertySheetPage);
		for (Iterator<?> iterator = descriptor.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			Object tab = descriptor.get(key);
			Method getSectionAtIndex = getMethod(tab, "getSectionAtIndex", int.class); //$NON-NLS-1$
			if (getSectionAtIndex != null) {
				Object result = callMethod(tab, getSectionAtIndex, new Integer(0));
				if (result == this) {
					Method getId = getMethod(key, "getId"); //$NON-NLS-1$
					if (getId != null) {
						String id = (String)callMethod(key, getId);
						return id;
					}
				}
			}
		}
		return ""; //$NON-NLS-1$
	}

	private Map<?, ?> getPageDescriptor(TabbedPropertySheetPage propertySheetPage) {
		Field descriptorToTabField = null;
		boolean oldAccessible = false;
		try {
			Class<?> cls = propertySheetPage.getClass();
			while (!cls.equals(TabbedPropertySheetPage.class)) {
				cls = cls.getSuperclass();
			}
			descriptorToTabField = cls.getDeclaredField("descriptorToTab"); //$NON-NLS-1$
			oldAccessible = descriptorToTabField.isAccessible();
			descriptorToTabField.setAccessible(true);
			return (Map<?, ?>)descriptorToTabField.get(propertySheetPage);

		} catch (SecurityException e) {
			EEFRuntimeUI.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (NoSuchFieldException e) {
			EEFRuntimeUI.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (IllegalArgumentException e) {
			EEFRuntimeUI.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} catch (IllegalAccessException e) {
			EEFRuntimeUI.getPlugin().logError("Unable to access the descriptorToTab method", e);
		} finally {
			if (descriptorToTabField != null) {
				descriptorToTabField.setAccessible(oldAccessible);
			}
		}
		return null;
	}

	/**
	 * @param source
	 *            the source object
	 * @param name
	 *            the method to get
	 * @param argsType
	 *            the method arguments type
	 * @return the given method
	 */
	private Method getMethod(Object source, String name, Class<?>... argsType) {
		try {
			return source.getClass().getDeclaredMethod(name, argsType);
		} catch (Exception e) {
			EEFRuntimeUI.getPlugin().logError("Unable to access a method", e);
		}
		return null;
	}

	/**
	 * @param source
	 *            the source object
	 * @param name
	 *            the method to get
	 * @param argsType
	 *            the method arguments type
	 * @return the result of the given method
	 */
	private Object callMethod(Object source, Method method, Object... args) {
		try {
			return method.invoke(source, args);
		} catch (Exception e) {
			EEFRuntimeUI.getPlugin().logError("Unable to invoke a method", e);
		}
		return null;
	}
}
