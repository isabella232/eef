/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyProcessorProvider {
	
	/**
	 * Returns a policy process able to handle the given context.
	 * @param editingContext the {@link PropertiesEditingContext} to handle.
	 * @return an {@link EditingPolicyProcessor} able to handle the context.
	 */
	EditingPolicyProcessor getProcessor(PropertiesEditingContext editingContext);

}
