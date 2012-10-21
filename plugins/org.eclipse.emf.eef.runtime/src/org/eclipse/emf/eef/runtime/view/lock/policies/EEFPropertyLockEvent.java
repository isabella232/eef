/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFPropertyLockEvent extends EEFLockEvent {

	/**
	 * @return the locked {@link EStructuralFeature}.
	 */
	EStructuralFeature getLockedFeature();
	
}
