/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelPropertyEditorProvider<T extends EObject,U> extends PropertyEditorProvider<U> {

	/**
	 * @return the model for this provider.
	 */
	public T getModel();
		
}
