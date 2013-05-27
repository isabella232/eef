/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.handlers.swt;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandlerProvider extends AbstractEEFService<Object> implements ViewHandlerProvider {
	
	private BindingManagerProvider bindingManagerProvider;

	/**
	 * @param bindingManagerProvider the bindingManagerProvider to set
	 */
	public void setBindingManagerProvider(BindingManagerProvider bindingManagerProvider) {
		this.bindingManagerProvider = bindingManagerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object view) {
		if (view instanceof Class<?> ) {
			return isCompositeClass((Class<?>) view);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public ViewHandler<? extends Composite> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new SWTViewHandler(this, editingComponent, (Class<? extends Composite>) view);
	}

	/**
	 * @param view
	 * @return
	 */
	private boolean isCompositeClass(Class<?> view) {
		if (view == Composite.class) {
			return true;
		} else if (view.getSuperclass() != null) {
			return isCompositeClass(view.getSuperclass());
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getLockManager(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public EEFLockManager getLockManager(PropertiesEditingComponent component, Object view) {
		return bindingManagerProvider.getBindingManager(component).getLockManager(view);
	}

}
