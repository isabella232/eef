/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEditingPolicyWithProcessor implements EditingPolicyWithProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public final void execute() {
		getProcessor().process(getPolicyProcessing());
	}

	/**
	 * @return the {@link EditingPolicyProcessing} to execute by this {@link PropertiesEditingPolicy}.
	 */
	protected abstract EditingPolicyProcessing getPolicyProcessing();

}
