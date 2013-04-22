/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.processors.DomainEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class WizardEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
			return element instanceof SemanticPropertiesEditingContext
				&& element instanceof DomainAwarePropertiesEditingContext
				&& ((SemanticPropertiesEditingContext)element).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext, org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent)
	 */
	@Override
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyIntent behavior) {
		Object newValue = behavior.getNewIndex();
		switch (behavior.getProcessingKind()) {
		case EDIT:
			if (newValue != null) {
				PropertiesEditingContextFactory editingContextFactory = domainEditingContext.getServiceRegistry().getService(PropertiesEditingContextFactory.class, (EObject)newValue);
				PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(domainEditingContext, (EObject)newValue);
				context.getOptions().setBatchMode(true);
				context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
				WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(context);
				return wizardEditingCommand;
			}
		}
		return null;
	}
	
}
