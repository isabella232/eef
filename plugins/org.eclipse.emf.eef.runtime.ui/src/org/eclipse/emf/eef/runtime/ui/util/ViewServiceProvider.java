/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.util;

import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewServiceProvider {
	
	/**
	 * Returns the most appropriate {@link ViewService} for the given view.
	 * @param view the input view.
	 * @return the most appropriate {@link ViewService}.
	 */
	ViewService getViewService(View view);

}
