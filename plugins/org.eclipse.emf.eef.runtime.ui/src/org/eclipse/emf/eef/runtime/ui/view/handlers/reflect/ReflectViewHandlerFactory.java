/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.reflect;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ReflectViewHandlerFactory implements ViewHandlerFactory {

	private EEFLockManagerProvider lockManagerProvider;
	private EEFLogger logger;
	
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
	public boolean serviceFor(Object view) {
		if (view instanceof Class<?>) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<Object> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new ReflectViewHandler<Object>(this, editingComponent, (Class<?>) view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getLockManager(java.lang.Object)
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

}
