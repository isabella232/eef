/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFLockPolicyFactoryProviderImpl extends EEFServiceProviderImpl<EObject, EEFLockPolicyFactory> implements EEFLockPolicyFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactoryProvider#getLockPolicyFactories(org.eclipse.emf.ecore.EObject)
	 */
	public Collection<EEFLockPolicyFactory> getLockPolicyFactories(EObject source) {
		return getServicesFor(source);
	}

}
