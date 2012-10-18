/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockPolicyFactory extends EEFService<EObject> {

	/**
	 * Instantiates a {@link EEFLockPolicy}.
	 * @return the create policy.
	 */
	EEFLockPolicy createLockPolicy();
	
}
