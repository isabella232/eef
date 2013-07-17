/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handle;

import org.eclipse.emf.eef.runtime.editingModel.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProvider {
	
	/**
	 * Returns the most appropriate view handler for the given view.
	 * @param view the view to operate.
	 * @return the most appropriate {@link ViewHandler} for the given view. 
	 */
	ViewHandler<?> getViewHandler(View view);
	
}
