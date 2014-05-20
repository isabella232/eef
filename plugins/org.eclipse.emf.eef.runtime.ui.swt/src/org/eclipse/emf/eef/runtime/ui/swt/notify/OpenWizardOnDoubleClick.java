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
package org.eclipse.emf.eef.runtime.ui.swt.notify;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.commands.WizardEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class OpenWizardOnDoubleClick implements IDoubleClickListener {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	private EditingDomain domain;
	private AdapterFactory adapterFactory;

	/**
	 * @param domain
	 *            {@link EditingDomain} to use for editing actions.
	 * @param adapterFactory
	 *            {@link AdapterFactory} to use for element editing.
	 */
	public OpenWizardOnDoubleClick(EditingDomain domain, AdapterFactory adapterFactory) {
		this.domain = domain;
		this.adapterFactory = adapterFactory;
	}

	/**
	 * @param contextFactoryProvider
	 *            the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

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
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		StructuredSelection selection = (StructuredSelection) event.getSelection();
		PropertiesEditingContext context;
		if (selection.getFirstElement() instanceof EObject) {
			EObject eObject = (EObject) selection.getFirstElement();
			PropertiesEditingContextFactory editingContextFactory = contextFactoryProvider.getEditingContextFactory(eObject);
			context = editingContextFactory.createPropertiesEditingContext(domain, adapterFactory, eObject);
			context.getOptions().setBatchMode(true);
			PropertiesEditingEvent editingEvent = new PropertiesEditingEventImpl(null, null, PropertiesEditingEvent.EDIT, null, eObject);
			SemanticPropertiesEditingContext semanticContext = (SemanticPropertiesEditingContext) editingContextFactory.createSemanticPropertiesEditingContext(context, editingEvent);
			WizardEditingCommand wizardEditingCommand = new WizardEditingCommand(contextFactoryProvider, emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, semanticContext);
			domain.getCommandStack().execute(wizardEditingCommand);
			context.dispose();
		}
	}

}
