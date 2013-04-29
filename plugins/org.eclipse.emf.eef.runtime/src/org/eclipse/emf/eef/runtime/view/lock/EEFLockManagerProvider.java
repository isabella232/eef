/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockManagerProvider {
	
	/**
	 * Returns the LockManager able to handle the given view.
	 * @param source the view to lock.
	 * @return the {@link EEFLockManager} to use to lock the view.
	 */
	EEFLockManager getLockManager(Object source);

}
