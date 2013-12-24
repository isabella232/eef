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
package org.eclipse.emf.eef.editor.internal.propertyeditors.extended.treecontents;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.editor.internal.widgets.FormTreeEEFViewer;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TreeContentsFormPropertyEditor implements FormPropertyEditor<TreeEEFViewer> {

	private EMFServiceProvider emfServiceProvider;
	private ImageManager imageManager;

	private EditingDomain editingDomain;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	
	private FormTreeEEFViewer viewer;

	/**
	 * @param editingDomain can be null;
	 */
	public TreeContentsFormPropertyEditor(EMFServiceProvider emfServiceProvider, ImageManager imageManager, EditingDomain editingDomain, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.emfServiceProvider = emfServiceProvider;
		this.imageManager = imageManager;
		this.editingDomain = editingDomain;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		this.viewer = new FormTreeEEFViewer(toolkit, parent, SWT.NONE);
		viewer.setEMFServiceProvider(emfServiceProvider);
		viewer.setImageManager(imageManager);
		viewer.setEditingDomain(editingDomain);
		viewer.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 3;
		viewer.getControl().setLayoutData(layoutData);		
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public TreeEEFViewer getViewer() {
		return viewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		// TODO Auto-generated method stub
		
	}

}
