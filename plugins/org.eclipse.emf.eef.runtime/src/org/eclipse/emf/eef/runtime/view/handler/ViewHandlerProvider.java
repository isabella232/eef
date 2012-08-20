/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProvider extends EEFService<Object> {

	/**
	 * Provides a handler for the given view.
	 * @param component {@link PropertiesEditingComponent} requests the {@link ViewHandler}. 
	 * @param view view to handle.
	 * @return a {@link ViewHandler} managing the given view.
	 */
	ViewHandler<?> getHandler(PropertiesEditingComponent component, Object view);
	
}
