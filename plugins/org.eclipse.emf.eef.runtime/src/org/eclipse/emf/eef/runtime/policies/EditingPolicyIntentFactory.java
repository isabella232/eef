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
public interface EditingPolicyIntentFactory extends EEFService<PropertiesEditingContext> {
	
	/**
	 * Creates a {@link EditingPolicyIntent} from a given {@link PropertiesEditingContext}
	 * @param editingContext the source {@link PropertiesEditingContext}. 
	 * @return the created {@link EditingPolicyIntent}.
	 */
	EditingPolicyIntent createProcessing(PropertiesEditingContext editingContext);

}
