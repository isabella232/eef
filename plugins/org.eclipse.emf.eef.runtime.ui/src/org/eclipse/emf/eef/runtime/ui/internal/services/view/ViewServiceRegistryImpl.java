/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.services.view;

import org.eclipse.emf.eef.runtime.services.impl.EEFServiceSimpleRegistry;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewServiceRegistryImpl extends EEFServiceSimpleRegistry<View, ViewService> implements ViewServiceRegistry {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceRegistry#getServiceForView(org.eclipse.emf.eef.views.View)
	 */
	public ViewService getServiceForView(View view) {
		return getServiceForElement(view);
	}

}
