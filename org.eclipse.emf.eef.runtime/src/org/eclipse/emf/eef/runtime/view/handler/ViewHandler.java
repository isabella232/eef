/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler;

import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewConstructionException;
import org.eclipse.emf.eef.runtime.view.handler.exceptions.ViewHandlingException;



/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandler {

	/**
	 * Handled view getter.
	 * @param args argument to create the view.
	 * @throws ViewConstructionException An error occured during view construction.
	 * @return the handled view.
	 */
	Object createView(Object... args) throws ViewConstructionException;
	
	/**
	 * @return the handled view.
	 */
	Object getView();
	
	/**
	 * Sets a value to the given field. 
	 * @param field feature to process.
	 * @param value the new value.
	 * @throws Exception 
	 */
	void setValue(Object field, Object value) throws ViewHandlingException;

}
