/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFPropertyLockEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFPropertyLockEventImpl extends EEFLockEventImpl implements EEFPropertyLockEvent {

	private EStructuralFeature lockedFeature;
	
	public EEFPropertyLockEventImpl(EObject lockObject, EStructuralFeature lockedFeature, LockState state) {
		super(lockObject, state);
		this.lockedFeature = lockedFeature;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFPropertyLockEvent#getLockedFeature()
	 */
	public EStructuralFeature getLockedFeature() {
		return lockedFeature;
	}

}
