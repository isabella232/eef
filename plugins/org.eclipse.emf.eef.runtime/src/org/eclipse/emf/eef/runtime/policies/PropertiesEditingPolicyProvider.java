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
public interface PropertiesEditingPolicyProvider extends EEFService<PropertiesEditingContext> {

	/**
	 * @param context
	 * @return
	 */
	PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context);
		
}
