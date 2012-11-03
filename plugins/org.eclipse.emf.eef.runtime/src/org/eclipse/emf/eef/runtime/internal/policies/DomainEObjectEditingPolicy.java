/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class DomainEObjectEditingPolicy extends EObjectEditingPolicy {

	/**
	 * @param editingContext
	 * @param editingEvent
	 */
	public DomainEObjectEditingPolicy(DomainAwarePropertiesEditingContext editingContext, PropertiesEditingEvent editingEvent) {
		super(editingContext, editingEvent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.EObjectEditingPolicy#getEditingContext()
	 */
	@Override
	public DomainAwarePropertiesEditingContext getEditingContext() {
		return (DomainAwarePropertiesEditingContext) super.getEditingContext();
	}

}
