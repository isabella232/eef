/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.editingview;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EObjectView;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandlerFactory implements ViewHandlerFactory<PropertiesEditingView<Composite>> {

	private EMFServiceProvider emfServiceProvider;
	private ViewServiceProvider viewServiceProvider;
	private EEFToolkitProvider eefToolkitProvider;
	private EEFLockManagerProvider lockManagerProvider;
	private EEFLogger logger;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param viewServiceProvider the viewServiceProvider to set
	 */
	public void setViewServiceProvider(ViewServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

	/**
	 * @param eefToolkitProvider the eefToolkitProvider to set
	 */
	public void setEEFToolkitProvider(EEFToolkitProvider eefToolkitProvider) {
		this.eefToolkitProvider = eefToolkitProvider;
	}

	/**
	 * @param lockManagerProvider the lockManagerProvider to set
	 */
	public void setLockManagerProvider(EEFLockManagerProvider lockManagerProvider) {
		this.lockManagerProvider = lockManagerProvider;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(EEFLogger logger) {
		this.logger = logger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(org.eclipse.emf.eef.runtime.editingModel.View view) {
		return view instanceof EObjectView && ((EObjectView)view).getDefinition() instanceof View;
	}

	/**
	 * @return
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return emfServiceProvider;
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
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * @return the logger
	 */
	public EEFLogger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#createView(java.lang.Object, java.lang.Object[])
	 */
	public PropertiesEditingView<Composite> createView(Object viewDescriptor, Object... args) throws ViewConstructionException {
		if (viewDescriptor instanceof View && args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
			PropertiesEditingComponent editingComponent = (PropertiesEditingComponent) args[0];
			PropertiesEditingView<Composite> view = new SWTImplPropertiesEditingView(editingComponent, (View) viewDescriptor);					
			view.setViewServiceProvider(viewServiceProvider);
			view.setToolkitPropertyEditorFactory(eefToolkitProvider);
			((SWTImplPropertiesEditingView) view).createContents((Composite)args[1]);
			return view;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public void initView(PropertiesEditingComponent component, PropertiesEditingView<Composite> view) {
		if (view != null) {
			view.init();
			EEFLockManager lockManager = lockManagerProvider.getLockManager(view);
			lockManager.initView(view);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#setValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setValue(final Object view, final Object field, final Object value) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.setValue(field, value);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#unsetValue(java.lang.Object, java.lang.Object)
	 */
	public void unsetValue(final Object view, final Object field) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.unsetValue(field);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#addValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void addValue(final Object view, final Object field, final Object newValue) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.addValue(field, newValue);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#addAllValues(java.lang.Object, java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(final Object view, final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.addAllValues(field, values);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#removeValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void removeValue(final Object view, final Object field, final Object value) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.removeValue(field, value);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#removeAllValues(java.lang.Object, java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(final Object view, final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.removeAllValues(field, values);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#moveValue(java.lang.Object, java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(final Object view, final Object field, final Object value, final int newIndex) throws ViewHandlingException {
		if (view instanceof PropertiesEditingView) {
			final PropertiesEditingView<?> editingView = ((PropertiesEditingView<?>) view);
			if (editingView.getViewService() instanceof SWTViewService && editingView.getContents() instanceof Composite) {
				((SWTViewService) editingView.getViewService()).executeSyncUIRunnable(((Composite) editingView.getContents()).getDisplay(), new Runnable() {
					public void run() {
						editingView.moveValue(field, value, newIndex);
					}
				});
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#dispose(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public void dispose(PropertiesEditingComponent editingComponent, Object view) {
		editingComponent.removeView(view);		
	}

}
