/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.processors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.DomainEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class WizardDomainEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param editUIProvidersFactory
	 *            the editUIProvidersFactory to set
	 */
	public void setEditUIProvidersFactory(EditUIProvidersFactory editUIProvidersFactory) {
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return element instanceof SemanticPropertiesEditingContext && element instanceof DomainAwarePropertiesEditingContext && ((SemanticPropertiesEditingContext) element).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	@Override
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest behavior) {
		if (domainEditingContext instanceof SemanticPropertiesEditingContext) {
			PropertiesEditingEvent event = ((SemanticPropertiesEditingContext) domainEditingContext).getEditingEvent();
			Object newValue = behavior.getValue();
			switch (behavior.getProcessingKind()) {
			case EDIT:
				if (newValue != null) {
					PropertiesEditingContextFactory editingContextFactory = domainEditingContext.getContextFactoryProvider().getEditingContextFactory((EObject) newValue);
					PropertiesEditingContext context = editingContextFactory.createSemanticPropertiesEditingContext(domainEditingContext, event);
					context.getOptions().setBatchMode(true);
					context.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
					WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(domainEditingContext.getContextFactoryProvider(), emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, (SemanticPropertiesEditingContext) context);
					return wizardEditingCommand;
				}
			}
		}
		return null;
	}

}
