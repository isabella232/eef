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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.combo;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor.ComboContentProviderInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class ComboPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<ComboViewer> propertyEditorControl;

	/**
	 * @param eefEditingServiceProvider
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public ComboPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<ComboViewer> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorControl = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init()
	 */
	public void init() {
		if (propertyEditorControl instanceof ComboUIPropertyEditor) {
			((ComboUIPropertyEditor) propertyEditorControl).initCombo(new ComboContentProviderInput(view.getEditingComponent().getEditingContext(), elementEditor));
		}
		EObject eObject = view.getEditingComponent().getEObject();
		setValue(eefEditingServiceProvider.getEditingService(eObject).getValue(view.getEditingComponent().getEditingContext(), eObject, elementEditor));
		initListeners();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorControl;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value instanceof ISelection) {
			propertyEditorControl.getViewer().setSelection((ISelection) value);
		} else {
			propertyEditorControl.getViewer().setSelection(new StructuredSelection(value));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
	}

	private void initListeners() {
		propertyEditorControl.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (view.getEditingComponent() != null) {
					Object value = ((StructuredSelection) propertyEditorControl.getViewer().getSelection()).getFirstElement();
					firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, value));
				}
			}
		});
	}

}
