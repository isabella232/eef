/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.reflect;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectViewHandlerProvider extends AbstractEEFService<Object> implements ViewHandlerProvider {

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
		if (view instanceof Class<?>) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<Object> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new ReflectViewHandler<Object>(this, editingComponent, (Class<?>) view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getLockManager(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public EEFLockManager getLockManager(PropertiesEditingComponent component, Object view) {
		return bindingManagerProvider.getBindingManager(component).getLockManager(view);
	}

}
