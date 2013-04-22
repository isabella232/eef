/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.EEFService;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyProcessor extends EEFService<PropertiesEditingContext> {

	/**
	 * Processes a {@link EditingPolicyRequest}.
	 * @param editingContext the {@link PropertiesEditingContext} where to perform the editing.
	 * @param behavior behavior to execute.
	 */
	void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior);
	
}
