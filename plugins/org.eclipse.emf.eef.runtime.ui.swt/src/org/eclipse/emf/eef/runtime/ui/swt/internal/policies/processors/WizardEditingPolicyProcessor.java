/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.processors.DomainEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class WizardEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;
	
	/**
	 * @param contextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param editUIProvidersFactory the editUIProvidersFactory to set
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

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
	 * @see org.eclipse.emf.eef.runtime.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext, org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	@Override
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest behavior) {
		Object newValue = behavior.getNewIndex();
		switch (behavior.getProcessingKind()) {
		case EDIT:
			if (newValue != null) {
				PropertiesEditingContextFactory editingContextFactory = contextFactoryProvider.getEditingContextFactory((EObject)newValue);
				PropertiesEditingContext context = editingContextFactory.createPropertiesEditingContext(domainEditingContext, (EObject)newValue);
				context.getOptions().setBatchMode(true);
				context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
				WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(contextFactoryProvider, emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, context);
				return wizardEditingCommand;
			}
		}
		return null;
	}
	
}
