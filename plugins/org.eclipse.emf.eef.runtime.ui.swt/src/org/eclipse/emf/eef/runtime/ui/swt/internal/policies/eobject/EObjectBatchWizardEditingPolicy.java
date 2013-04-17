package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.eobject;

import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.BatchWizardEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EObjectBatchWizardEditingPolicy extends EObjectBatchEditingPolicy {


	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new BatchWizardEditingPolicyProcessor();
	}

}
