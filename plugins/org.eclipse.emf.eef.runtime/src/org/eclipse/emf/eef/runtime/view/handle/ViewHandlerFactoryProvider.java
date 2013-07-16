/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handle;

import org.eclipse.emf.eef.runtime.editingModel.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerFactoryProvider {
	
	/**
	 * Returns the most appropriate handler factory for the given view.
	 * @param view the view to manage.
	 * @return the most appropriate {@link ViewHandlerFactory} for the given view. 
	 */
	ViewHandlerFactory<?> getHandlerFactory(View view);
	
}
