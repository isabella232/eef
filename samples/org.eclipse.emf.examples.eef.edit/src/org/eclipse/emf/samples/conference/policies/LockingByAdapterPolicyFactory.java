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
package org.eclipse.emf.samples.conference.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.samples.conference.ConferencePackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LockingByAdapterPolicyFactory implements EEFLockPolicyFactory {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return ConferencePackage.eNS_URI.equals(element.eClass().getEPackage().getNsURI());
	}
	
	/**
	 * Sends a lockEvent to the given component.
	 * @param editingComponent the {@link PropertiesEditingComponent} to fire.
	 * @param lockEvent the {@link EEFLockEvent} to send.
	 */
	public void fireLockChanged(PropertiesEditingComponent editingComponent, EEFLockEvent lockEvent) {
		editingComponent.getEditingContext().getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).fireLockChanged(editingComponent, lockEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory#createLockPolicy()
	 */
	public EEFLockPolicy createLockPolicy() {
		return new LockingByAdapterPolicy(this);
	}

}
