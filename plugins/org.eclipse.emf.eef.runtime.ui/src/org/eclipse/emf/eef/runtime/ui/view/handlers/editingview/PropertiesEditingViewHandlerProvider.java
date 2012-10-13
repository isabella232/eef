/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandlerProvider extends AbstractEEFComponent implements ViewHandlerProvider {

	private ViewServiceRegistry viewServiceRegistry;
	
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
	 * Returns the {@link ViewServiceRegistry} to use in the views created via this provider.
	 * @return the {@link ViewServiceRegistry} to use.
	 */
	public ViewServiceRegistry getViewServiceRegistry() {
		return viewServiceRegistry;
	}

	/**
	 * Defines the {@link ViewServiceRegistry} to use in the views created via this provider. 
	 * @param viewServiceRegistry the viewServiceRegistry to set
	 */
	public void setViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
		this.viewServiceRegistry = viewServiceRegistry;
	}

	/**
	 * Unsets the {@link ViewServiceRegistry} used in the views created via this provider. 
	 * @param viewServiceRegistry the viewServiceRegistry to unset
	 */
	public void unsetViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
		if (viewServiceRegistry == this.viewServiceRegistry) {
			this.viewServiceRegistry = null;
		}
	}

}
