/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.view.lock.policies.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFLockEventImpl implements EEFLockEvent {

	private EObject lockObject;
	private LockState state;
	
	public EEFLockEventImpl(EObject lockObject, LockState state) {
		this.lockObject = lockObject;
		this.state = state;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent#getLockedObject()
	 */
	public EObject getLockedObject() {
		return lockObject;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent#getState()
	 */
	public LockState getState() {
		return state;
	}

}
