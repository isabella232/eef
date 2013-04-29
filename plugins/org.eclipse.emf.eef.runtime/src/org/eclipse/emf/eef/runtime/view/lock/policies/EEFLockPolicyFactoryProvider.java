/**
 * 
 */
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
