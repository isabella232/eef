/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyWithProcessor extends PropertiesEditingPolicy {
	
	/**
	 * Defines the processor to use for the current {@link PropertiesEditingPolicy} execution.
	 * @return the {@link PropertiesEditingPolicyProvider} to use.
	 */
	EditingPolicyProcessor getProcessor();

}
