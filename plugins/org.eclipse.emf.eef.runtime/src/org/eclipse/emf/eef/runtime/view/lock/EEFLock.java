/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLock {

	/**
	 * @return the locked {@link EObject}.
	 */
	EObject getLockedObject();
	
}
