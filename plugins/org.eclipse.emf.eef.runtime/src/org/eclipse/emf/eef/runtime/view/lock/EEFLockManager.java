/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockManager {

	/**
	 * Locks a view towards a given {@link EEFLock}.
	 * @param lock the object describing the lock to apply in the view. 
	 */
	void lock(EEFLock lock);
	
	/**
	 * Removes the lock of the view.
	 */
	void clearViewLock();
	
	/**
	 * Removes the lock on a given editor.
	 * @param editor the editor to unlock
	 */
	void clearEditorLock(Object editor);
	
}
