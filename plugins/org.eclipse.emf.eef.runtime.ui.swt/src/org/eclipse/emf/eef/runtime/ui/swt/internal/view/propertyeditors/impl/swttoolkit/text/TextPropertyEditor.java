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
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
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
public class TextPropertyEditor extends PropertyEditorImpl implements  MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	
	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorControl;

	protected PropertyBinding propertyBinding;

	/**
	 * @param eefEditingServiceProvider
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public TextPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorControl = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public void init(PropertyBinding propertyBinding) {
		this.propertyBinding = propertyBinding;
		EObject eObject = view.getEditingComponent().getEObject();
		Object value = eefEditingServiceProvider.getEditingService(eObject).getValue(view.getEditingComponent().getEditingContext(), eObject, propertyBinding);
		if (value instanceof String) {
			propertyEditorControl.getViewer().getMainControl().setText((String) value);
		} else {
			propertyEditorControl.getViewer().getMainControl().setText(value == null?"":value.toString());			
		}
		initListeners();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorControl;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value == null) {
			propertyEditorControl.getViewer().getMainControl().setText("");
		} else if (value instanceof String) {
			propertyEditorControl.getViewer().getMainControl().setText((String) value);
		} else if (value instanceof EObject) {
			Adapter adapt = view.getEditingComponent().getEditingContext().getAdapterFactory().adapt((EObject)value, IItemLabelProvider.class);
			if (adapt instanceof IItemLabelProvider) {
				propertyEditorControl.getViewer().getMainControl().setText(((IItemLabelProvider) adapt).getText(value));
			} else {
				propertyEditorControl.getViewer().getMainControl().setText(value.toString());
			}
		} else {
			propertyEditorControl.getViewer().getMainControl().setText(value.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorControl.getViewer().getMainControl().setText("");
	}

	private void initListeners() {
		propertyEditorControl.getViewer().getMainControl().addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				if (view.getEditingComponent() != null)
					firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, propertyEditorControl.getViewer().getMainControl().getText(),true));
			}
		});
	}		

}
