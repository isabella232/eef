/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.ereference;

import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.LiveEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceLiveEditingPolicy extends EReferenceDomainEditingPolicy {

	/**
	 * @param context
	 */
	public EReferenceLiveEditingPolicy(SemanticDomainPropertiesEditingContext context) {
		super(context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveEditingPolicyProcessor(getEditingContext());
	}

}
