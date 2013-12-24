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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog#buildLoadModelMenu(org.eclipse.swt.widgets.Menu)
	 */
	@Override
	protected void buildLoadModelMenu(Menu menu) {
		MenuItem filesystemItem = new MenuItem (menu, SWT.PUSH);
		filesystemItem.setText("From workspace...");
		filesystemItem.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				WorkspaceFileSelectionDialog dialog = new WorkspaceFileSelectionDialog(getShell(), false);
				dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
				if (dialog.open() == Window.OK) {
					List<Object> result = dialog.getResult();
					if (result.size() > 0 && result.get(0) instanceof IFile) {
						IFile file = (IFile) result.get(0);
						String pathName = file.getFullPath().toOSString();
						URI uri = URI.createPlatformResourceURI(pathName, false);
						addURIsToLoad(uri.toString());
					}
				}
			}

		});
		super.buildLoadModelMenu(menu);
	}

}
