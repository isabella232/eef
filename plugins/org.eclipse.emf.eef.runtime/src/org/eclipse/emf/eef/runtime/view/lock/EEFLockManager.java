/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockManager extends EEFService<Object> {

	/**
	 * Initialize the view locks.
	 * @param view the view to process.
	 */
	void initView(Object view);
	
	/**
	 * Locks the given view.
	 * @param view the view to lock.
	 */
	void lockView(Object view);
	
	/**
	 * Locks an editor in the given view.
	 * @param view the view to process.
	 * @param editor the editor to lock.
	 */
	void lockEditor(Object view, Object editor);
	
	/**
	 * Removes the lock of the view.
	 * @param view the view to process.
	 */
	void clearViewLock(Object view);
	
	/**
	 * Removes the lock on a given editor.
	 * @param view the view to process.
	 * @param editor the editor to unlock
	 */
	void clearEditorLock(Object view, Object editor);
	
}
