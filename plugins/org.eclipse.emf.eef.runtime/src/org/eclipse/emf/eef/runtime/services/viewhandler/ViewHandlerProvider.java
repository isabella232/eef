/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.viewhandler;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;

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
	
	/**
	 * Returns the best {@link EEFLockManager} for the given view.
	 * @param component {@link PropertiesEditingComponent} requests the {@link ViewHandler}. 
	 * @param view the view to manage.
	 * @return the {@link EEFLockManager} to use.
	 */
	EEFLockManager getLockManager(PropertiesEditingComponent component, Object view);

	
}
