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
package org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFEditAwareLockPolicyFactory implements EEFLockPolicyFactory {
	
	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory#createLockPolicy()
	 */
	public EEFLockPolicy createLockPolicy() {
		EMFEditAwareLockPolicy emfEditAwareLockPolicy = new EMFEditAwareLockPolicy();
		emfEditAwareLockPolicy.setEMFServiceProvider(emfServiceProvider);
		return emfEditAwareLockPolicy;
	}

}
