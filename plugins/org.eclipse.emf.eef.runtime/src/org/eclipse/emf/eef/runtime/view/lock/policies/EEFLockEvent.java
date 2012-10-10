/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.eef.runtime.view.lock.EEFLock;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFLockEvent {

	/**
	 * The {@link EEFLockPolicy} causing this event.
	 */
	public EEFLockPolicy policy;
	
	/**
	 * The new state of the lock.
	 */
	public State state;
	
	/**
	 * The processed {@link EEFLock}.
	 */
	public EEFLock lock;
	
	
	public enum State {
		
		ENABLE,
		DISABLE
	}
	
}
