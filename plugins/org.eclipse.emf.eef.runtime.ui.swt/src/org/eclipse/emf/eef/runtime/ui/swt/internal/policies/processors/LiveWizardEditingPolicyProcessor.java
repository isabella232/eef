/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.internal.policies.processors.LiveEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LiveWizardEditingPolicyProcessor extends LiveEditingPolicyProcessor {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.internal.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent)
	 */
	@Override
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
		return super.convertToCommand(domainEditingContext, behavior);
	}
	
}
