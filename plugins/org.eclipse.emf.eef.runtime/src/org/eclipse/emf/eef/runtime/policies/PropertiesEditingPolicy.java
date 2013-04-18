/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingPolicy {
	
	/**
	 * @param editingContext the {@link SemanticPropertiesEditingContext} to process.
	 * @return an {@link EditingPolicyValidation} defining if the policy can be performed with the given {@link SemanticPropertiesEditingContext}.
	 */
	EditingPolicyValidation validateEditing(PropertiesEditingContext editingContext);

	/**
	 * Executes the policy for the given {@link SemanticPropertiesEditingContext}.
	 * @param editingContext the {@link SemanticPropertiesEditingContext} to process.
	 */
	void execute(PropertiesEditingContext editingContext);
	
}
