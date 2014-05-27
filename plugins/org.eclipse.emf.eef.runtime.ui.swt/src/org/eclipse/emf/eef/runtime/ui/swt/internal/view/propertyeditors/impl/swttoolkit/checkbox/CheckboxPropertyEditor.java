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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.checkbox;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify.EEFListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CheckboxPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	
	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EEFControlWrapperViewer<Button>> propertyEditorViewer;
	
	private CheckboxListener listener;

	/**
	 * @param eefEditingServiceProvider
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public CheckboxPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<EEFControlWrapperViewer<Button>> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init()
	 */
	public void init() {
		EObject eObject = view.getEditingComponent().getEObject();
		Object value = eefEditingServiceProvider.getEditingService(eObject).getValue(view.getEditingComponent().getEditingContext(), eObject, elementEditor);
		if (value instanceof Boolean) {
			propertyEditorViewer.getViewer().getMainControl().setSelection((Boolean) value);
		}
		initListeners();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value instanceof Boolean) {
			listener.disable();
			propertyEditorViewer.getViewer().getMainControl().setSelection((Boolean) value);
			listener.enable();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorViewer.getViewer().getMainControl().setSelection(false);
	}

	private void initListeners() {
		EEFControlWrapperViewer<Button> viewer = propertyEditorViewer.getViewer();
		listener = new CheckboxListener(this, view, elementEditor, viewer);
		viewer.getMainControl().addSelectionListener(listener);
	}
	
	private static final class CheckboxListener extends EEFListener<EEFControlWrapperViewer<Button>> implements SelectionListener {

		public CheckboxListener(PropertyEditor propertyEditor, PropertiesEditingView<Composite> view, ElementEditor elementEditor, EEFControlWrapperViewer<Button> viewer) {
			super(propertyEditor, view, elementEditor, viewer);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			if (isEnabled() && view.getEditingComponent() != null)
				propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, new Boolean(viewer.getMainControl().getSelection())));
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetDefaultSelected(SelectionEvent e) {
			// Nothing to do...
		}
		
		
		
	}

}
