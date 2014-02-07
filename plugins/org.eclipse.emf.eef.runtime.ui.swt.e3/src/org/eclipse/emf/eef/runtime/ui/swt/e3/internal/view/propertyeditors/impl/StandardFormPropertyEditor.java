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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class StandardFormPropertyEditor<VIEWER extends Viewer> implements FormPropertyEditor<VIEWER> {

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;

	/**
	 * @param view
	 *            {@link PropertiesEditingView} where the PropertyEditor is
	 *            built.
	 * @param viewElement
	 *            {@link ElementEditor} specifying the Property Editor.
	 */
	public StandardFormPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit,
	 *      org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		ViewService viewService = view.getViewService();
		if (viewService instanceof PlatformAwareViewService) {
			((PlatformAwareViewService) viewService).createLabel(view.getEditingComponent(), toolkit, parent, elementEditor, elementEditor.getName());
		} else if (viewService instanceof SWTViewService) {
			((SWTViewService) viewService).createLabel(view.getEditingComponent(), parent, elementEditor, elementEditor.getName());
		}
		createEditorContents(toolkit, parent);
		if (viewService instanceof PlatformAwareViewService) {
			((PlatformAwareViewService) viewService).createHelpButton(view.getEditingComponent(), toolkit, parent, elementEditor);
		} else if (viewService instanceof SWTViewService) {
			((SWTViewService) viewService).createHelpButton(view.getEditingComponent(), parent, elementEditor);
		}
	}

	/**
	 * Create the contents of the property editor in the owning Composite.
	 * 
	 * @param toolkit
	 *            {@link FormToolkit} to use to build the control.
	 * @param parent
	 *            the owning {@link Composite}.
	 */
	protected abstract void createEditorContents(FormToolkit toolkit, Composite parent);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		getViewer().getControl().setEnabled(false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		getViewer().getControl().setEnabled(true);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#isLocked()
	 */
	public boolean isLocked() {
		return !getViewer().getControl().isEnabled();
	}
}
