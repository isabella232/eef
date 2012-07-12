/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandler<T> {

	/**
	 * Handled view getter.
	 * @param args argument to create the view.
	 * @throws ViewConstructionException An error occured during view construction.
	 * @return the handled view.
	 */
	T createView(Object... args) throws ViewConstructionException;
	
	/**
	 * @return the handled view.
	 */
	T getView();
	
	/**
	 * @return the {@link ViewHandlerProvider} that created the current {@link ViewHandlerProvider};
	 */
	ViewHandlerProvider getProvider();
	
	/**
	 * Initialize a view with the current EObject properties.
	 * @param component {@link PropertiesEditingComponent} to use for initialization.
	 */
	void initView(PropertiesEditingComponent component);

	/**
	 * Sets a value to the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void setValue(Object field, Object value) throws ViewHandlingException;

	/**
	 * Unsets a value to the given field.
	 * @param field feature to process.
	 * @throws Exception an error occurred during view handling.
	 */
	void unsetValue(Object field) throws ViewHandlingException;
	
	/**
	 * Add a new value to the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void addValue(Object field, Object newValue) throws ViewHandlingException;

	/**
	 * Add all the values to the given field. 
	 * @param field feature to process.
	 * @param values the new values.
	 * @throws Exception an error occurred during view handling.
	 */
	void addAllValues(Object field, Collection<?> values) throws ViewHandlingException;

	/**
	 * Remove a value from the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occurred during view handling.
	 */
	void removeValue(Object field, Object value) throws ViewHandlingException;

	/**
	 * Removes all the values to the given field. 
	 * @param field feature to process.
	 * @param values the new values.
	 * @throws Exception an error occurred during view handling.
	 */
	void removeAllValues(Object field, Collection<?> values) throws ViewHandlingException;

	/**
	 * Moves a value at the given index in the field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @param newIndex the new index.
	 * @throws Exception an error occurred during view handling.
	 */
	void moveValue(Object field, Object value, int newIndex) throws ViewHandlingException;

}
