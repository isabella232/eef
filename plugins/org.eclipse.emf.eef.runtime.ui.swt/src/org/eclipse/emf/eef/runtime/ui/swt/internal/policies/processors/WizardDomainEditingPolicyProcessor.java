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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFWizardDialog;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class WizardDomainEditingPolicyProcessor extends DomainEditingPolicyProcessor {

	protected EMFServiceProvider emfServiceProvider;
	protected EEFEditingServiceProvider eefEditingServiceProvider;
	protected EditUIProvidersFactory editUIProvidersFactory;

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
	public boolean serviceFor(PropertiesEditingContext editingContext) {
		EEFEditingService editingService = eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject());
		return editingContext instanceof DomainAwarePropertiesEditingContext
				&& (editingService.isAddingInContainmentEvent(editingContext, ((SemanticPropertiesEditingContext) editingContext).getEditingEvent()) || ((SemanticPropertiesEditingContext) editingContext).getEditingEvent().getEventType() == PropertiesEditingEvent.EDIT);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.policies.processors.DomainEditingPolicyProcessor#convertToCommand(org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest)
	 */
	@Override
	protected Command convertToCommand(DomainAwarePropertiesEditingContext domainEditingContext, EditingPolicyRequest request) {
		if (domainEditingContext instanceof SemanticPropertiesEditingContext) {
			PropertiesEditingEvent event = ((SemanticPropertiesEditingContext) domainEditingContext).getEditingEvent();
			Object newValue = request.getValue();
			switch (request.getProcessingKind()) {
			case SET:
			case ADD: {
				EStructuralFeature feature = eefEditingServiceProvider.getEditingService(domainEditingContext.getEditingComponent().getEObject()).featureFromEditor(domainEditingContext, event.getAffectedEditor());
				if (feature instanceof EReference && ((EReference) feature).isContainment() && newValue == null) {
					newValue = defineEObjectToAdd((SemanticPropertiesEditingContext) domainEditingContext, (EReference) feature);
					return performAdd(domainEditingContext, domainEditingContext.getEditingComponent().getEObject(), newValue);
				}
			}
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

	/**
	 * @param editedReference
	 *            {@link EReference} to edit.
	 * @return the {@link EObject} to set in thce given {@link EReference}.
	 */
	protected EObject defineEObjectToAdd(SemanticPropertiesEditingContext editingContext, EReference editedReference) {
		return createObjectAndOpenWizard(editingContext, editedReference);
	}

	private EObject createObjectAndOpenWizard(final SemanticPropertiesEditingContext editingContext, EReference editedReference) {
		editingContext.getOptions().setOption(EEFSWTConstants.FORM_TOOLKIT, null);
		EEFEditingWizard wizard = new EEFEditingWizard(editingContext.getContextFactoryProvider(), emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, editingContext) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard#attachToResource(org.eclipse.emf.ecore.resource.Resource,
			 *      org.eclipse.emf.ecore.EObject)
			 */
			@Override
			protected void attachToResource(Resource resource, EObject eObject) {
				eefEditingServiceProvider.getEditingService(eObject).attachToResource(editingContext, resource, eObject);
				editingContext.startEditing();
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard#detachFromResource(org.eclipse.emf.ecore.EObject)
			 */
			@Override
			protected void detachFromResource(EObject eObject) {
				eefEditingServiceProvider.getEditingService(eObject).detachFromResource(editingContext, eObject);
			}

		};
		// TODO: use a UI helper for providing the shell
		EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
		int open = wDialog.open();
		if (open == Window.CANCEL) {
			return null;
		}

		return wizard.getCreatedObject();
	}

}
