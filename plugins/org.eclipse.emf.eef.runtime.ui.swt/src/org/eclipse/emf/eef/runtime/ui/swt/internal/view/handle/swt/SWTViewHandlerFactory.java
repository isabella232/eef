/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.handle.swt;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTViewHandlerFactory extends ReflectViewHandlerFactory<Composite> {
	
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
	 * @see org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandlerFactory#serviceFor(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public boolean serviceFor(View view) {
		return super.serviceFor(view) && isCompositeClass((Class<?>) ((JavaView)view).getDefinition());
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
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object view) {
		return lockManagerProvider.getLockManager(view);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory#getLogger()
	 */
	public EEFLogger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.internal.view.handle.reflect.ReflectViewHandlerFactory#dispose(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	@Override
	public void dispose(PropertiesEditingComponent editingComponent, Object view) {
		if (view instanceof Composite) {
			((Composite)view).dispose();
		}
		super.dispose(editingComponent, view);
	}

}
