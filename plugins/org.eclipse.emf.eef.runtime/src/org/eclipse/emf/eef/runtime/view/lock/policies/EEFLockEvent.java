/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockEvent {

	/**
	 * @return the locked {@link EObject}.
	 */
	EObject getLockedObject();
	
	/**
	 * @return the lock state.
	 */
	LockState getState();
	
	
	static enum LockState {
		LOCKED,
		UNLOCKED
	}
	
}
