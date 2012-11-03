/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.eobject;

import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class EObjectDomainEditingPolicy extends EObjectEditingPolicy {

	/**
	 * @param editingContext
	 */
	public EObjectDomainEditingPolicy(SemanticDomainPropertiesEditingContext editingContext) {
		super(editingContext);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.eobject.EObjectEditingPolicy#getEditingContext()
	 */
	@Override
	public DomainAwarePropertiesEditingContext getEditingContext() {
		return (DomainAwarePropertiesEditingContext) super.getEditingContext();
	}

}
