/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyIntentFactoryProvider {
	
	/**
	 * @param editingContext
	 * @return
	 */
	EditingPolicyIntentFactory getProcessingFactory(PropertiesEditingContext editingContext);

}
