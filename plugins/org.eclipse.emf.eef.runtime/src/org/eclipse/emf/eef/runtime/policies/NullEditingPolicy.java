/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;


/**
 * A null implementation of the {@link PropertiesEditingPolicy} interface.
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullEditingPolicy implements PropertiesEditingPolicy {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#validateEditing()
	 */
	public EditingPolicyValidation validateEditing() {
		return new EditingPolicyValidation(this, true);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#getBehavior()
	 */
	public EditingPolicyProcessing getBehavior() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public void execute() {
		//Do nothing.
	}

}
