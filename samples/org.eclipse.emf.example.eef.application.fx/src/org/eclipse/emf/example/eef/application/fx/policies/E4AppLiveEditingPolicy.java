package org.eclipse.emf.example.eef.application.fx.policies;

import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.example.eef.application.fx.policies.processors.E4AppEditingPolicyProcessor;


public class E4AppLiveEditingPolicy extends EObjectLiveEditingPolicy {

	/**
	 * @param editingContext
	 */
	public E4AppLiveEditingPolicy(SemanticDomainPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new E4AppEditingPolicyProcessor(this);
	}

}
