/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.commands;


import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFEditingWizard;
import org.eclipse.emf.eef.runtime.ui.wizard.EEFWizardDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class WizardEditingCommand extends AbstractBatchEditingCommand {

	public WizardEditingCommand(PropertiesEditingContext editionContext) {
		super(editionContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.commands.AbstractBatchEditingCommand#prepareBatchEditing()
	 */
	@Override
	protected boolean prepareBatchEditing() {
		EEFEditingWizard wizard = new EEFEditingWizard(editingContext);
		//TODO: use a UI helper for providing the shell 
		EEFWizardDialog wDialog = new EEFWizardDialog(new Shell(), wizard);
		int open = wDialog.open();
		return (open == Window.OK);
	}

	
}
