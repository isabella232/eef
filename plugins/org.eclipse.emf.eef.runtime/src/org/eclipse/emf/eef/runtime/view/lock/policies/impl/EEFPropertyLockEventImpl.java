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
