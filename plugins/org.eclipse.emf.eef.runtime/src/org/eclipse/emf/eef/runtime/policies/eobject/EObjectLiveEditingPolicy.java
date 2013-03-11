/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.eobject;

import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.processors.LiveEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectLiveEditingPolicy extends EObjectDomainEditingPolicy {

	/**
	 * @param editingContext
	 */
	public EObjectLiveEditingPolicy(SemanticDomainPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveEditingPolicyProcessor(this);
	}

}
