/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.LiveEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveEObjectEditingPolicy extends DomainEObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public LiveEObjectEditingPolicy(DomainAwarePropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveEditingPolicyProcessor(getEditingContext());
	}

}
