/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.eobject;

import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.LiveWizardEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectLiveWizardEditingPolicy extends EObjectLiveEditingPolicy {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveWizardEditingPolicyProcessor();
	}

}
