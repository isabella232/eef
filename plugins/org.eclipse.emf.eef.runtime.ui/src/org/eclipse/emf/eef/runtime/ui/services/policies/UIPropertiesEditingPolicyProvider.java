/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.services.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.StandardPropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceBatchWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceDirectWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceLiveWizardEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UIPropertiesEditingPolicyProvider extends StandardPropertiesEditingPolicyProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.StandardPropertiesEditingPolicyProvider#createEReferenceDirectEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	@Override
	public PropertiesEditingPolicy createEReferenceDirectEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceDirectWizardEditingPolicy((SemanticPropertiesEditingContext) context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.StandardPropertiesEditingPolicyProvider#createEReferenceBatchEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	@Override
	public PropertiesEditingPolicy createEReferenceBatchEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceBatchWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.StandardPropertiesEditingPolicyProvider#createEReferenceLiveEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	@Override
	public PropertiesEditingPolicy createEReferenceLiveEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceLiveWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

}
