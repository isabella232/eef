/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.samples.gettingstarted.internal.actions.showingmodel;

import org.eclipse.emf.eef.samples.gettingstarted.actions.EEFGettingStartedAction;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ShowingAModelWithEEF implements EEFGettingStartedAction {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.samples.gettingstarted.actions.EEFGettingStartedAction#getTitle()
	 */
	public String getTitle() {
		return "1 - Showing an arbitrary model with EEF";
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.samples.gettingstarted.actions.EEFGettingStartedAction#execute()
	 */
	public void execute() {
		ShowingAModelWithEEFDialog dialog = new ShowingAModelWithEEFDialog(new Shell());
		dialog.open();
	}


}
