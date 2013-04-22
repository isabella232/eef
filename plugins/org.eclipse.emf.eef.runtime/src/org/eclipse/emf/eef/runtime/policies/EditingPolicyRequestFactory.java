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
public interface EditingPolicyRequestFactory extends EEFService<PropertiesEditingContext> {
	
	/**
	 * Creates a {@link EditingPolicyRequest} from a given {@link PropertiesEditingContext}
	 * @param editingContext the source {@link PropertiesEditingContext}. 
	 * @return the created {@link EditingPolicyRequest}.
	 */
	EditingPolicyRequest createProcessing(PropertiesEditingContext editingContext);

}
