/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProvider {

	/**
	 * Defines if this provider can provide a handle for the given view. 
	 * @param view view to handle.
	 * @return <code>true</code> if current handler can handle the given view.
	 */
	boolean canHandle(Object view);
	
	/**
	 * Provides a handler for the given view.
	 * @param view view to handle.
	 * @return a {@link ViewHandler} managing the given view.
	 */
	ViewHandler<?> getHandler(Object view);
	
}
