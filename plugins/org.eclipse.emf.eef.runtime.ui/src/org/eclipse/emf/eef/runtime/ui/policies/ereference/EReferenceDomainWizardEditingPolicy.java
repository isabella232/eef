/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.policies.ereference;

import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EReferenceDomainWizardEditingPolicy extends EReferenceWizardEditingPolicy {
	
	/**
	 * @param context
	 */
	public EReferenceDomainWizardEditingPolicy(SemanticDomainPropertiesEditingContext context) {
		super(context);
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
