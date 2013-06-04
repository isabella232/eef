/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.viewhandler;

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
	ViewHandlerFactory getHandlerFactory(Object view);
	
}
