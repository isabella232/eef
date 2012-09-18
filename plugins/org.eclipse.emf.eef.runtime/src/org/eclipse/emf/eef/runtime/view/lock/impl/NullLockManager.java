/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.impl;

import org.eclipse.emf.eef.runtime.view.lock.EEFLock;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;

/**
 * A null implementation of the {@link EEFLockManager} interface.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullLockManager implements EEFLockManager {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lock(org.eclipse.emf.eef.runtime.view.lock.EEFLock)
	 */
	public void lock(EEFLock lock) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearViewLock()
	 */
	public void clearViewLock() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearEditorLock(java.lang.Object)
	 */
	public void clearEditorLock(Object editor) {
		// Do nothing
	}

}
