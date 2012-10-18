/**
 * 
 */
package org.eclipse.emf.samples.conferences.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory;
import org.eclipse.emf.samples.conference.ConferencePackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LockingByAdapterPolicyFactory extends AbstractEEFService<EObject> implements EEFLockPolicyFactory {
	
	public LockingByAdapterPolicyFactory() {
		System.out.println();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	@Override
	public boolean serviceFor(EObject element) {
		return ConferencePackage.eNS_URI.equals(element.eClass().getEPackage().getNsURI());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyFactory#createLockPolicy()
	 */
	@Override
	public EEFLockPolicy createLockPolicy() {
		return new LockingByAdapterPolicy();
	}

}
