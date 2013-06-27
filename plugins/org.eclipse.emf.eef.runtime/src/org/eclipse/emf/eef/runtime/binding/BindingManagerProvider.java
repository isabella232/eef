/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface BindingManagerProvider {

	/**
	 * Returns a {@link PropertiesBindingManager} able to handle the given type of EObject.
	 * @param target the {@link EObject} to manage.
	 * @return the most appropriate {@link PropertiesBindingManager}.
	 */
	PropertiesBindingManager getBindingManager(EObject target);
	
}
