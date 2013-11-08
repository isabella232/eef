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
package org.eclipse.emf.eef.runtime.view.lock;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockManagerProvider {
	
	/**
	 * Returns the LockManager able to handle the given view.
	 * @param source the view to lock.
	 * @return the {@link EEFLockManager} to use to lock the view.
	 */
	EEFLockManager getLockManager(Object source);

}
