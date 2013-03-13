/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.processors.BatchEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.ui.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.commands.WizardEditingCommand;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BatchWizardEditingPolicyProcessor extends BatchEditingPolicyProcessor {

	/**
	 * @param editingPolicy
	 */
	public BatchWizardEditingPolicyProcessor(EditingPolicyWithProcessor editingPolicy) {
		super(editingPolicy);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.internal.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing)
	 */
	@Override
	protected Command convertToCommand(EditingPolicyProcessing behavior) {
		Object newValue = behavior.value;
		switch (behavior.processingKind) {
		case EDIT:
			if (newValue != null) {
				PropertiesEditingContextFactory editingContextFactory = editingContext.getServiceRegistry().getService(PropertiesEditingContextFactory.class, (EObject)newValue);
				PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(editingContext, (EObject)newValue);
				context.getOptions().setBatchMode(true);
				context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
				WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(context);
				return wizardEditingCommand;
			}
		}
		return super.convertToCommand(behavior);
	}
	
}
