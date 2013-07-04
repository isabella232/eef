/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.services.DefaultService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class NullEditingPolicyProcessor implements EditingPolicyProcessor, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior) {
		// Do
	}

}
