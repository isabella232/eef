/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEditingPolicyWithProcessor implements EditingPolicyWithProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public void execute() {
		getProcessor().process(getPolicyProcessing());
	}

	/**
	 * @return the {@link EditingPolicyProcessing} to execute by this {@link PropertiesEditingPolicy}.
	 */
	protected abstract EditingPolicyProcessing getPolicyProcessing();

}
