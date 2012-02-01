/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelPropertyEditorProvider<T extends EObject> extends PropertyEditorProvider {

	/**
	 * @return the model for this provider.
	 */
	public T getModel();
		
}
