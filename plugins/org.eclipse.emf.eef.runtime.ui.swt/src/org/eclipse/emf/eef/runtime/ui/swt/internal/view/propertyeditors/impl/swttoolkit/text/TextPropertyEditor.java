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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify.EEFListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class TextPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorControl;
	
	private TextEEFListener listener;
	
	/**
	 * @param eefEditingServiceProvider
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public TextPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor,
			PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorViewer) {
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
		EObject eObject = view.getEditingComponent().getEObject();
		Object value = eefEditingServiceProvider.getEditingService(eObject).getValue(view.getEditingComponent().getEditingContext(), eObject, elementEditor);
		String text = "";
		if (value instanceof String) {
			text = (String) value;
		} else {
			text = value == null ? "" : value.toString();
		}
		if (text == null || !text.equals(propertyEditorControl.getViewer().getMainControl().getText())) {
			propertyEditorControl.getViewer().getMainControl().setText(text);
		}
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
		listener.disable();
		String text = "";
		if (value == null) {
			text = "";
		} else if (value instanceof String) {
			text = (String) value;
		} else if (value instanceof EObject) {
			Adapter adapt = view.getEditingComponent().getEditingContext().getAdapterFactory().adapt((EObject) value, IItemLabelProvider.class);
			if (adapt instanceof IItemLabelProvider) {
				text = ((IItemLabelProvider) adapt).getText(value);
			} else {
				text = value.toString();
			}
		} else {
			text = value.toString();
		}
		if (text == null || !text.equals(propertyEditorControl.getViewer().getMainControl().getText())) {
			propertyEditorControl.getViewer().getMainControl().setText(text);
		}
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorControl.getViewer().getMainControl().setText("");
	}

	private void initListeners() {
		EEFControlWrapperViewer<Text> viewer = propertyEditorControl.getViewer();
		listener = new TextEEFListener(this, view, elementEditor, viewer);
		viewer.getMainControl().addModifyListener(listener);
	}

	private static final class TextEEFListener extends EEFListener<EEFControlWrapperViewer<Text>> implements ModifyListener {
				
		public TextEEFListener(TextPropertyEditor propertyEditor, PropertiesEditingView<Composite> view, ElementEditor elementEditor, EEFControlWrapperViewer<Text> viewer) {
			super(propertyEditor, view, elementEditor, viewer);
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 */
		public void modifyText(ModifyEvent e) {
			if (isEnabled() && view.getEditingComponent() != null) {
				propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, viewer.getMainControl().getText(), true));
			}
		}
		
	}
	
}
