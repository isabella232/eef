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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.hbox;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class HBoxFormPropertyEditor implements FormPropertyEditor<EEFControlWrapperViewer<Composite>> {

	private Container container;
	
	private EEFControlWrapperViewer<Composite> wrapperViewer;
	private Composite hbox;

	private GridLayout layout;
	
	/**
	 * @param container
	 */
	public HBoxFormPropertyEditor(Container container) {
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<Composite> getViewer() {
		if (wrapperViewer == null) {
			wrapperViewer = new EEFControlWrapperViewer<Composite>() {


				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				@Override
				public Composite getMainControl() {
					return hbox;
				}


			};
		}
		return wrapperViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		hbox = toolkit.createComposite(parent);
		GridData hboxData = new GridData(GridData.FILL_HORIZONTAL);
		hboxData.horizontalSpan = 3;
		hbox.setLayoutData(hboxData);
		layout = new GridLayout();
		layout.marginHeight = 1;
		layout.marginWidth = 0;
		layout.makeColumnsEqualWidth = false;
		int numColumns = 0;
		for (EObject element : container.eContents()) {
			if (element instanceof ViewElement) {
				numColumns += 3;
			} else if (element instanceof Container) {
				numColumns += 1;
			}
		}
		layout.numColumns = numColumns;
		hbox.setLayout(layout);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		// Do nothing		
	}

	
}
