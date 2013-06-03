/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.ui.swt.view.handlers.editingview.PropertiesEditingViewHandlerFactory;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PlatformAwarePropertiesEditingViewHandlerFactory extends PropertiesEditingViewHandlerFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object view) {
		return view instanceof View;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<?> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new PlatformAwarePropertiesEditingViewHandler(this, editingComponent, (View) view);
	}

}
