/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.eobject;

import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.BatchEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectBatchEditingPolicy extends EObjectDomainEditingPolicy {

	/**
	 * @param editingContext
	 */
	public EObjectBatchEditingPolicy(SemanticDomainPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new BatchEditingPolicyProcessor(getEditingContext());
	}

}
