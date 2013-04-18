/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.editing;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFEditingServiceProvider {

	/**
	 * Returns an {@link EEFEditingService} able to handle the given {@link EObject}.
	 * @param eObject the {@link EObject} to process.
	 * @return a {@link EEFEditingService}.
	 */
	EEFEditingService getEditingService(EObject eObject);
	
}
