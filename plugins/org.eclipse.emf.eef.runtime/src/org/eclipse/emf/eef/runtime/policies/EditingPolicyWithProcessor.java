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
public interface EditingPolicyWithProcessor extends PropertiesEditingPolicy, EEFService<PropertiesEditingContext> {
	
	/**
	 * Defines the processor to use for the current {@link PropertiesEditingPolicy} execution.
	 * @return the {@link PropertiesEditingPolicyProvider} to use.
	 */
	EditingPolicyProcessor getProcessor();

}
