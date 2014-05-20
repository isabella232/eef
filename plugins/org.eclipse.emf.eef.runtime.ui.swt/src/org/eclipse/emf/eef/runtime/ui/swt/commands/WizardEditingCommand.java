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
package org.eclipse.emf.eef.runtime.ui.swt.commands;

import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.swt.wizard.EEFWizardDialog;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class WizardEditingCommand extends AbstractBatchEditingCommand {

	private EditingContextFactoryProvider contextFactoryProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	/**
	 * @param contextFactoryProvider
	 * @param emfServiceProvider
	 * @param eefEditingServiceProvider
	 * @param editUIProvidersFactory
	 * @param editionContext
	 */
	public WizardEditingCommand(EditingContextFactoryProvider contextFactoryProvider, EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider, EditUIProvidersFactory editUIProvidersFactory, SemanticPropertiesEditingContext editionContext) {
		super(editionContext);
		this.contextFactoryProvider = contextFactoryProvider;
		this.emfServiceProvider = emfServiceProvider;
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand#prepareBatchEditing()
	 */
	@Override
	protected boolean prepareBatchEditing() {
		EEFEditingWizard wizard = new EEFEditingWizard(contextFactoryProvider, emfServiceProvider, eefEditingServiceProvider, editUIProvidersFactory, editingContext);
		// TODO: use a UI helper for providing the shell
		EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
		int open = wDialog.open();
		return (open == Window.OK);
	}

}
