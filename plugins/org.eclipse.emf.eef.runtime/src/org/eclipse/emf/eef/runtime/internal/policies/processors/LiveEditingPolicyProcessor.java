/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticDomainPropertiesEditingContext && element.getOptions().liveMode();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.processors.DomainEditingPolicyProcessor#executeCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext, org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void executeCommand(DomainAwarePropertiesEditingContext editingContext, Command command) {
		editingContext.getEditingDomain().getCommandStack().execute(command);
	}

}
