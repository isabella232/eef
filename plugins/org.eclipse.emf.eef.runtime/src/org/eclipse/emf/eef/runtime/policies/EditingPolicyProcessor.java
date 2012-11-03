/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyProcessor {

	/**
	 * Processes a {@link EditingPolicyProcessing}.
	 * @param behavior behavior to execute.
	 */
	void process(EditingPolicyProcessing behavior);
	
}
