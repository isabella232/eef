/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.BatchEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BatchEObjectEditingPolicy extends DomainEObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public BatchEObjectEditingPolicy(DomainAwarePropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new BatchEditingPolicyProcessor(getEditingContext());
	}

}
