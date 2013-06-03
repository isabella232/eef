/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.handlers.editingview;

import java.util.Collection;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.services.viewhandler.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.impl.SWTImplPropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.swt.services.view.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandler implements ViewHandler<PropertiesEditingView<Composite>> {

	protected View viewDescriptor;
	protected PropertiesEditingViewHandlerFactory handlerProvider;
	protected PropertiesEditingComponent editingComponent;
	protected PropertiesEditingView<Composite> view;
	
	/**
	 * @param handlerProvider 
	 * @param viewDescriptor {@link View} to handle.
	 */
	public PropertiesEditingViewHandler(PropertiesEditingViewHandlerFactory handlerProvider, PropertiesEditingComponent editingComponent, View viewDescriptor) {
		this.handlerProvider = handlerProvider;
		this.editingComponent = editingComponent;
		this.viewDescriptor = viewDescriptor;
	}

	/**
	 * @return the viewDescriptor
	 */
	public View getViewDescriptor() {
		return viewDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#createView(java.lang.Object[])
	 */
	public PropertiesEditingView<Composite> createView(Object... args) throws ViewConstructionException {
		if (view == null) {
			if (args.length > 1 && args[0] instanceof PropertiesEditingComponent && args[1] instanceof Composite) {
				PropertiesEditingComponent editingComponent = (PropertiesEditingComponent) args[0];
				view = new SWTImplPropertiesEditingView(editingComponent, viewDescriptor);					
				view.setViewServiceProvider(handlerProvider.getViewServiceProvider());
				view.setToolkitPropertyEditorFactory(handlerProvider.getEEFToolkitProvider());
				((SWTImplPropertiesEditingView) view).createContents((Composite)args[1]);
			}
		}
		return view;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#initView(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void initView(PropertiesEditingComponent component) {
		if (view != null) {
			view.init();
			EEFLockManager lockManager = handlerProvider.getLockManager(view);
			lockManager.initView(view);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#canHandle(java.lang.Object)
	 */
	public boolean canHandle(Object editor) {
		for (TreeIterator<EObject> iterator = viewDescriptor.eAllContents(); iterator.hasNext();) {
			EObject next = iterator.next();
			if (next == editor) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#getView()
	 */
	public PropertiesEditingView<Composite> getView() { 
		return view;
	}

	/**
	 * Force the view of this {@link ViewHandler}.
	 * @param propertiesEditingView the view to define for this handler.
	 */
	public void setView(PropertiesEditingView<Composite> propertiesEditingView) {
		view = propertiesEditingView;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#getProvider()
	 */
	public ViewHandlerFactory getProvider() {
		return handlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#setValue(java.lang.Object, java.lang.Object)
	 */
	public void setValue(final Object field, final Object value) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.setValue(field, value);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#unsetValue(java.lang.Object)
	 */
	public void unsetValue(final Object field) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.unsetValue(field);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#addValue(java.lang.Object, java.lang.Object)
	 */
	public void addValue(final Object field, final Object newValue) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.addValue(field, newValue);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#addAllValues(java.lang.Object, java.util.Collection)
	 */
	public void addAllValues(final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {	
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.addAllValues(field, values);
				}
			});

		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#removeValue(java.lang.Object, java.lang.Object)
	 */
	public void removeValue(final Object field, final Object value) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.removeValue(field, value);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#removeAllValues(java.lang.Object, java.util.Collection)
	 */
	public void removeAllValues(final Object field, final Collection<?> values) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.removeAllValues(field, values);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#moveValue(java.lang.Object, java.lang.Object, int)
	 */
	public void moveValue(final Object field, final Object value, final int newIndex) throws ViewHandlingException {
		if (view != null && view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).executeSyncUIRunnable(view.getContents().getDisplay(), new Runnable() {
				public void run() {
					view.moveValue(field, value, newIndex);
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler#dispose()
	 */
	public void dispose() {
		this.editingComponent.unregisterViewHandler(this);
	}
	
}
