/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.services;

import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewServiceRegistry;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewServiceRegistryImpl extends EEFServiceRegistry<View, ViewService> implements ViewServiceRegistry {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.services.ViewServiceRegistry#getServiceForView(org.eclipse.emf.eef.views.View)
	 */
	public ViewService getServiceForView(View view) {
		return getServiceForElement(view);
	}

}
