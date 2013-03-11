/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	/**
	 * @param editingPolicy
	 */
	public LiveEditingPolicyProcessor(EditingPolicyWithProcessor editingPolicy) {
		super(editingPolicy);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.processors.DomainEditingPolicyProcessor#executeCommand(org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void executeCommand(Command command) {
		editingContext.getEditingDomain().getCommandStack().execute(command);
	}

}
