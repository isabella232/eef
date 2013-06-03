/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.services.view;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceProvider;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewServiceProviderImpl extends EEFServiceProviderImpl<View, ViewService> implements ViewServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceProvider#getViewService(org.eclipse.emf.eef.views.View)
	 */
	public ViewService getViewService(View view) {
		return getService(view);
	}

}
