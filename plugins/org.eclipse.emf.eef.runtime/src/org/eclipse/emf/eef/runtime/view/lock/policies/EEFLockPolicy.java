/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockPolicy {
	
	/**
	 * Defines if an {@link EObject} is locked.
	 * @param object the object to check.
	 * @return <code>true</code> if the given object is locked.
	 */
	boolean isLocked(EObject object);

	/**
	 * Defines if an {@link EStructuralFeature} of an object is locked.
	 * @param object the object to check.
	 * @param feature the feature to check.
	 * @return <code>true</code> if the given feature is locked.
	 */
	boolean isLocked(EObject object, EStructuralFeature feature);

	/**
	 * Defines the {@link EEFLockPolicyRegistry} owning the current policy. An {@link EEFLockPolicy} is intended to be
	 * owned by only one registry.
	 * @param registry the {@link EEFLockPolicyRegistry} owner.
	 */
	void setRegistry(EEFLockPolicyRegistry registry);
	
	/**
	 * Disposes the current policy.
	 */
	void dispose();
	
}
