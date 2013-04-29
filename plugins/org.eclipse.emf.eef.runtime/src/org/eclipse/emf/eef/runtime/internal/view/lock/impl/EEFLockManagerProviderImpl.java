/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.lock.impl;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFLockManagerProviderImpl extends EEFServiceProviderImpl<Object, EEFLockManager> implements EEFLockManagerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider#getLockManager(java.lang.Object)
	 */
	public EEFLockManager getLockManager(Object source) {
		return getService(source);
	}

}
