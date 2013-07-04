/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.handlers.swt;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandlerFactory implements ViewHandlerFactory {
	
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
		if (view instanceof Class<?> ) {
			return isCompositeClass((Class<?>) view);
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
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
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getLogger()
	 */
	public EEFLogger getLogger() {
		return logger;
	}

}
