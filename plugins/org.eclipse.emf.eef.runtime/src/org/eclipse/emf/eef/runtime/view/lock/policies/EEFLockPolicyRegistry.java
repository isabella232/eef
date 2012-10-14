/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock.policies;

import java.util.Collection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFLockPolicyRegistry {

	/**
	 * @return all the {@link EEFLockPolicy} in the current registry.
	 */
	Collection<EEFLockPolicy> getPolicies();
	
	/**
	 * Adds an {@link EEFLockPolicy} to the registry.
	 * @param policy the {@link EEFLockPolicy} to add.
	 */
	void addPolicy(EEFLockPolicy policy);
	
	/**
	 * Removes an {@link EEFLockPolicy}from the registry.
	 * @param policy the {@link EEFLockPolicy} to remove.
	 */
	void removePolicy(EEFLockPolicy policy);
	
	/**
	 * Notifies all the views managed by the {@link PropertiesEditingComponent} owning the current registry of a lock has change.
	 * @param event the {@link EEFLockEvent} describing the change.
	 */
//	void fireLockEvent(EEFLockEvent event);
	
	/**
	 * Disposes the current registry and all the owned policies.
	 */
	void dispose();
	
}
