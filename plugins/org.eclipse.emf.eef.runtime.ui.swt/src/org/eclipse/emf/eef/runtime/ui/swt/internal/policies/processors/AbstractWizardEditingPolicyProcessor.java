/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractWizardEditingPolicyProcessor extends AbstractEEFService<PropertiesEditingContext> implements EditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor#process(org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent)
	 */
	public void process(PropertiesEditingContext editingContext, EditingPolicyIntent behavior) {
		DomainAwarePropertiesEditingContext domainEditingContext = (DomainAwarePropertiesEditingContext) editingContext;
		Command convertToCommand = convertToCommand(domainEditingContext, behavior);
		if (convertToCommand != null) {
			executeCommand(domainEditingContext, convertToCommand);
		}
	}

	/**
	 * Executes the given command in the given context.
	 * @param domainEditingContext {@link DomainAwarePropertiesEditingContext} where to perform the command.
	 * @param command {@link Command} to execute.
	 */
	protected abstract void executeCommand(DomainAwarePropertiesEditingContext domainEditingContext, Command command);


	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyIntent behavior) {
		Object newValue = behavior.value;
		switch (behavior.processingKind) {
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
