/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.ereference;

import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.processors.DirectEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceDirectEditingPolicy extends EReferenceEditingPolicy {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new DirectEditingPolicyProcessor();
	}

}
