/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.impl;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;

/**
 * A null implementation of the {@link EEFLockManager} interface.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullLockManager implements EEFLockManager, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#initView(java.lang.Object)
	 */
	public void initView(Object view) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lockView(java.lang.Object)
	 */
	public void lockView(Object view) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#lockEditor(java.lang.Object, java.lang.Object)
	 */
	public void lockEditor(Object view, Object editor) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearViewLock(java.lang.Object)
	 */
	public void clearViewLock(Object view) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#clearEditorLock(java.lang.Object, java.lang.Object)
	 */
	public void clearEditorLock(Object view, Object editor) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManager#fireLockChange(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object, org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent)
	 */
	public void fireLockChange(PropertiesEditingComponent editingComponent, Object view, EEFLockEvent lockEvent) {
		// Do nothing
	}

}
