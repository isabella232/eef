/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

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
	 * Sets a value to the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occured du view handling.
	 */
	void setValue(Object field, Object value) throws ViewHandlingException;

	/**
	 * Unsets a value to the given field.
	 * @param field feature to process.
	 * @throws Exception an error occured du view handling.
	 */
	void unsetValue(Object field) throws ViewHandlingException;
	
	/**
	 * Add a new value to the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occured du view handling.
	 */
	void addValue(Object field, Object newValue) throws ViewHandlingException;

	/**
	 * Remove a value from the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception an error occured du view handling.
	 */
	void removeValue(Object field, Object value) throws ViewHandlingException;

	/**
	 * Initialize a view with the current EObject properties.
	 * @param component {@link PropertiesEditingComponent} to use for initialization.
	 */
	void initView(PropertiesEditingComponent component);


}
