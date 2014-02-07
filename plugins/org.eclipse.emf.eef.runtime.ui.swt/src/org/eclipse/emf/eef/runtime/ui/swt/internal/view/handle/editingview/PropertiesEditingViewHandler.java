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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class PropertiesEditingViewHandler implements ViewHandler<PropertiesEditingView<Composite>> {

	private EMFServiceProvider emfServiceProvider;
	protected EEFEditingServiceProvider eefEditingServiceProvider;
	private ViewServiceProvider viewServiceProvider;
	private EEFToolkitProvider eefToolkitProvider;
	private EEFLockManagerProvider lockManagerProvider;
	private EEFLogger logger;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param viewServiceProvider
	 *            the viewServiceProvider to set
	 */
	public void setViewServiceProvider(ViewServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

	/**
	 * @param eefToolkitProvider
	 *            the eefToolkitProvider to set
	 */
	public void setEEFToolkitProvider(EEFToolkitProvider eefToolkitProvider) {
		this.eefToolkitProvider = eefToolkitProvider;
	}

	/**
	 * @param lockManagerProvider
	 *            the lockManagerProvider to set
	 */
	public void setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
		this.lockManagerProvider = lockManagerProvider;
	}

	/**
	 * @param logger
	 *            the logger to set
	 */
	public void setLogger(EEFLogger logger) {
		this.logger = logger;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(org.eclipse.emf.eef.runtime.editingModel.View view) {
		return view instanceof EObjectView && ((EObjectView) view).getDefinition() instanceof View;
	}

	/**
	 * @return
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return emfServiceProvider;
	}

	/**
	 * @return the eefEditingServiceProvider
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return eefEditingServiceProvider;
	}

	/**
	 * @return the viewServiceProvider
	 */
	public ViewServiceProvider getViewServiceProvider() {
		return viewServiceProvider;
	}

	/**
	 * @return the {@link EEFToolkitProvider}
	 */
	public EEFToolkitProvider getEEFToolkitProvider() {
		return eefToolkitProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#getLockManagerProvider()
	 */
	public EEFLockManagerProvider getLockManagerProvider() {
		return lockManagerProvider;
	}

	/**
	 * @return the logger
	 */
	public EEFLogger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#createView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      org.eclipse.emf.eef.runtime.editingModel.View, java.lang.Object[])
	 */
	public PropertiesEditingView<Composite> createView(PropertiesEditingComponent editingComponent, org.eclipse.emf.eef.runtime.editingModel.View viewDescriptor, Object... args) throws ViewConstructionException {
		if (viewDescriptor instanceof EObjectView && ((EObjectView) viewDescriptor).getDefinition() instanceof View && args.length > 0 && args[0] instanceof Composite) {
			PropertiesEditingView<Composite> view = new SWTImplPropertiesEditingView(editingComponent, (View) ((EObjectView) viewDescriptor).getDefinition());
			((SWTImplPropertiesEditingView) view).setEEFEditingServiceProvider(eefEditingServiceProvider);
			((SWTImplPropertiesEditingView) view).setViewServiceProvider(viewServiceProvider);
			((SWTImplPropertiesEditingView) view).setToolkitPropertyEditorFactory(getEEFToolkitProvider());
			((SWTImplPropertiesEditingView) view).setLockManagerProvider(getLockManagerProvider());
			((SWTImplPropertiesEditingView) view).createContents((Composite) args[0]);
			editingComponent.setViewForDescriptor(viewDescriptor, view);
			return view;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      java.lang.Object)
	 */
	public void initView(PropertiesEditingComponent editingComponent, PropertiesEditingView<Composite> view) {
		if (view != null && !eefEditingServiceProvider.getEditingService(editingComponent.getBinding()).isReflectiveBinding(editingComponent.getBinding())) {
			UnmodifiableIterator<ElementEditor> elementEditors = Iterators.filter(view.getViewModel().eAllContents(), ElementEditor.class);
			while (elementEditors.hasNext()) {
				ElementEditor elementEditor = elementEditors.next();
				EStructuralFeature bindingFeature = editingComponent.getBinding().feature(elementEditor, editingComponent.getEditingContext().getOptions().autowire());
				EObject editedObject = editingComponent.getEObject();
				EMFService emfService = emfServiceProvider.getEMFService(editedObject.eClass().getEPackage());
				EStructuralFeature feature = emfService.mapFeature(editedObject, bindingFeature);
				if (feature != null) {
					PropertyEditor propertyEditor = view.getPropertyEditor(elementEditor);
					propertyEditor.init(feature);
				}
			}
			EEFLockManager lockManager = lockManagerProvider.getLockManager(view);
			lockManager.initView(view);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#setValue(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void setValue(final Object view, final Object field, final Object value) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MonovaluedPropertyEditor) {
								((MonovaluedPropertyEditor) propertyEditor).setValue(value);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#unsetValue(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void unsetValue(final Object view, final Object field) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MonovaluedPropertyEditor) {
								((MonovaluedPropertyEditor) propertyEditor).unsetValue();
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#addValue(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void addValue(final Object view, final Object field, final Object newValue) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MultivaluedPropertyEditor) {
								((MultivaluedPropertyEditor) propertyEditor).addValue(newValue);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#addAllValues(java.lang.Object,
	 *      java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(final Object view, final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MultivaluedPropertyEditor) {
								((MultivaluedPropertyEditor) propertyEditor).addAllValues(values);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#removeValue(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void removeValue(final Object view, final Object field, final Object value) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MultivaluedPropertyEditor) {
								((MultivaluedPropertyEditor) propertyEditor).removeValue(value);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#removeAllValues(java.lang.Object,
	 *      java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(final Object view, final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MultivaluedPropertyEditor) {
								((MultivaluedPropertyEditor) propertyEditor).removeAllValues(values);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#moveValue(java.lang.Object,
	 *      java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(final Object view, final Object field, final Object value, final int newIndex) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (!((Composite) editingView.getContents()).isDisposed() && editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						if (field instanceof ElementEditor) {
							PropertyEditor propertyEditor = editingView.getPropertyEditor((ViewElement) field);
							if (propertyEditor instanceof MultivaluedPropertyEditor) {
								((MultivaluedPropertyEditor) propertyEditor).moveValue(value, newIndex);
							}
						}
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandler#dispose(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      java.lang.Object)
	 */
	public void dispose(PropertiesEditingComponent editingComponent, Object view) {
		if (view instanceof PropertiesEditingView<?>) {
			editingComponent.removeEditingListener((PropertiesEditingListener) view);
		}
		editingComponent.removeView(view);
	}

}
