/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.eobject;

import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.DirectEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectDirectEditingPolicy extends EObjectEditingPolicy {

	/**
	 * @param editingContext
	 */
	public EObjectDirectEditingPolicy(SemanticPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new DirectEditingPolicyProcessor();
	}
	
}
