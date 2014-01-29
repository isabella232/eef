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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.edatepicker;

import java.util.Date;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFDatePickerDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EDatePackerPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	
	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer;
	private PropertyBinding propertyBinding;
	private SingleLinePropertyViewerListener listener;

	/**
	 * @param eefEditingServiceProvider
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public EDatePackerPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	public void init(PropertyBinding propertyBinding) {
		this.propertyBinding = propertyBinding;
		PropertiesEditingComponent editingComponent = view.getEditingComponent();
		PropertiesEditingContext editingContext = editingComponent.getEditingContext();
		if (eefEditingServiceProvider.getEditingService(editingComponent.getEObject()).getValue(editingContext, editingComponent.getEObject(), propertyBinding) != null) {
			EEFViewerInput input = new EEFViewerInput(eefEditingServiceProvider, editingContext, propertyBinding);
			propertyEditorViewer.getViewer().setInput(input);
		}
		initListener();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		propertyEditorViewer.getViewer().getControl().setLayoutData(layoutData);
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
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	protected void initListener() {
		if (listener == null) {
			listener = new SingleLinePropertyViewerListener() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#set()
				 */
				public void set() {
					EEFDatePickerDialog dialog = new EEFDatePickerDialog(propertyEditorViewer.getViewer().getControl().getShell());
					dialog.setTitle("Choose the date to set to the attribute:");
					PropertiesEditingComponent editingComponent = view.getEditingComponent();
					PropertiesEditingContext editingContext = editingComponent.getEditingContext();
					Date date = (Date) eefEditingServiceProvider.getEditingService(editingComponent.getEObject()).getValue(editingContext, editingComponent.getEObject(), propertyBinding);
					dialog.setDate(date);
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, dialog.getSelection()));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#clear()
				 */
				public void clear() {
					firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.UNSET, null, null));
					propertyEditorViewer.getViewer().refresh();
				}
				
			};
			propertyEditorViewer.getViewer().addSingleLinePropertyViewerListener(listener);
		}
	}
}
