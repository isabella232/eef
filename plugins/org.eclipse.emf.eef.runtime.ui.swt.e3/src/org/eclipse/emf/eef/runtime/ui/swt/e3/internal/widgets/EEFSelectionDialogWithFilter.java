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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets;

import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFSelectionDialogWithFilter extends EEFSelectionDialog {

	/**
	 * @param parent
	 * @param multi
	 */
	public EEFSelectionDialogWithFilter(Shell parent, boolean multi) {
		super(parent, multi);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog#createSelectionViewer(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected TreeViewer createSelectionViewer(Composite parent, int style) {
		FilteredTree tree = new FilteredTree(parent, style, new PatternFilter(), true);
		return tree.getViewer();
	}


}
