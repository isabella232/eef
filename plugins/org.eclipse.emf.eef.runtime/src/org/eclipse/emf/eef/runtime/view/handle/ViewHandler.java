/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handle;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.binding.PropertiesEditingComponentImpl;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handle.exceptions.ViewHandlingException;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * FIXME: the set/add/remove contracts are too weak. How to handle this problem in the {@link PropertiesEditingComponentImpl} ?
 */
public interface ViewHandler<T> extends EEFService<View> {

	/**
	 * Returns a LockManager able to manage the given view.
	 * @param view the View to manager.
	 * @return the {@link EEFLockManager} able to manager the given view. 
	 */
	EEFLockManager getLockManager(Object view);

	/**
	 * @return the {@link EEFLogger} in the current configuration. 
	 */
	EEFLogger getLogger();

	/**
	 * Handled view getter.
	 * @param args argument to create the view.
	 * @throws ViewConstructionException An error occured during view construction.
	 * @return the handled view.
	 */
	T createView(PropertiesEditingComponent component, View descriptor, Object... args) throws ViewConstructionException;


	/**
	 * Initializes a view with the current EObject properties.
	 * @param component {@link PropertiesEditingComponent} to use for initialization.
	 * @param view the view to initialize.
	 */
	void initView(PropertiesEditingComponent component, T view);

	/**
	 * Sets a value to the given field.
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void setValue(Object view, Object field, Object value) throws ViewHandlingException;

	/**
	 * Unsets a value to the given field.
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @throws Exception an error occurred during view handling.
	 */
	void unsetValue(Object view, Object field) throws ViewHandlingException;
	
	/**
	 * Add a new value to the given field. 
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void addValue(Object view, Object field, Object newValue) throws ViewHandlingException;

	/**
	 * Add all the values to the given field. 
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param values the new values.
	 * @throws Exception an error occurred during view handling.
	 */
	void addAllValues(Object view, Object field, Collection<?> values) throws ViewHandlingException;

	/**
	 * Remove a value from the given field. 
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void removeValue(Object view, Object field, Object value) throws ViewHandlingException;

	/**
	 * Removes all the values to the given field. 
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param values the new values.
	 * @throws Exception an error occurred during view handling.
	 */
	void removeAllValues(Object view, Object field, Collection<?> values) throws ViewHandlingException;

	/**
	 * Moves a value at the given index in the field. 
	 * @param view view to operate. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @param newIndex the new index.
	 * @throws Exception an error occurred during view handling.
	 */
	void moveValue(Object view, Object field, Object value, int newIndex) throws ViewHandlingException;
	
	/**
	 * Dispose the given view.
	 * @param editingComponent the associated component.
	 * @param view view to dispose.
	 */
	void dispose(PropertiesEditingComponent editingComponent, Object view);
	
}
