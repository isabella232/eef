/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.policies.eobject;

import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.processors.LiveWizardEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectLiveWizardEditingPolicy extends EObjectLiveEditingPolicy {

	/**
	 * @param editingContext
	 */
	public EObjectLiveWizardEditingPolicy(SemanticDomainPropertiesEditingContext editingContext) {
		super(editingContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveWizardEditingPolicyProcessor(this);
	}

}
