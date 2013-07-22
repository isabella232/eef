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
package org.eclipse.emf.eef.runtime.view.lock.policies;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockPolicyFactoryProvider {
	
	/**
	 * Returns all the LockPolicyFactory able to manage the given EObject.
	 * @param source {@link EObject} to check.
	 * @return a {@link Collection} of {@link EEFLockPolicyFactory} able to manage the given EObject.
	 */
	Collection<EEFLockPolicyFactory> getLockPolicyFactories(EObject source);
}
