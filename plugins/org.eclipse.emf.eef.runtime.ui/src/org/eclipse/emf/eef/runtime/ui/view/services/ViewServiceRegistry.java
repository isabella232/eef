/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.services;

import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewServiceRegistry {
	
	/**
	 * @param view to process.
	 * @return the {@link ViewService} to use for the given view.
	 */
	ViewService getServiceForView(View view);

}
