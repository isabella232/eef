/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingPolicy {
	
	/**
	 * @return an {@link EditingPolicyValidation} defining if the policy can be performed or not.
	 */
	EditingPolicyValidation validateEditing();

	/**
	 * Executes the policy behavior with the good processor.
	 */
	void execute();
	
}
