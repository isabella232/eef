/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyProcessor {

	/**
	 * Processes a {@link EditingPolicyProcessing}.
	 * @param editingContext the {@link PropertiesEditingContext} where to perform the editing.
	 * @param behavior behavior to execute.
	 */
	void process(PropertiesEditingContext editingContext, EditingPolicyProcessing behavior);
	
}
