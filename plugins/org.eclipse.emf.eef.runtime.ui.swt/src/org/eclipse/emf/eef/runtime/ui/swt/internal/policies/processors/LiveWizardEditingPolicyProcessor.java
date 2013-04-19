/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveWizardEditingPolicyProcessor extends AbstractWizardEditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
			return element instanceof SemanticPropertiesEditingContext 
				&& element instanceof DomainAwarePropertiesEditingContext
				&& ((SemanticPropertiesEditingContext)element).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT
				&& element.getOptions().liveMode();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors.AbstractWizardEditingPolicyProcessor#executeCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext, org.eclipse.emf.common.command.Command)
	 */
	@Override
	protected void executeCommand(DomainAwarePropertiesEditingContext editingContext, Command command) {
		editingContext.getEditingDomain().getCommandStack().execute(command);
	}
	
}
