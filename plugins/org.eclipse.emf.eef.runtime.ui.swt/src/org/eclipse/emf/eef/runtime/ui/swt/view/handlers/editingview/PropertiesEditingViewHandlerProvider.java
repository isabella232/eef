/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandlerProvider extends AbstractEEFService<Object> implements ViewHandlerProvider {

	private ViewServiceProvider viewServiceProvider;
	private EEFToolkitProvider eefToolkitProvider;
	private EEFLockManagerProvider lockManagerProvider;
	private EEFLogger logger;

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
	public boolean serviceFor(Object view) {
		return view instanceof View;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<?> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new PropertiesEditingViewHandler(this, editingComponent, (View) view);
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
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getLockManager(java.lang.Object)
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
