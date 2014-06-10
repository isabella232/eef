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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ArrayFeatureContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EContainmentPropertyEditor extends PropertyEditorImpl implements MultivaluedPropertyEditor {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer;

	private MultiLinePropertyViewerListener listener;

	public EContainmentPropertyEditor(EEFEditingServiceProvider eefEditingServiceProvider, EditUIProvidersFactory editUIProvidersFactory, PropertiesEditingView<Composite> view,
			ElementEditor elementEditor, PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init()
	 */
	public void init() {
		propertyEditorViewer.getViewer().setContentProvider(
				new ArrayFeatureContentProvider(eefEditingServiceProvider, (SWTViewService) view.getViewService(), view.getEditingComponent().getEditingContext(), elementEditor));
		PropertyBinding propertyBinding = view.getEditingComponent().getBinding().propertyBinding(elementEditor, view.getEditingComponent().getEditingContext().getOptions().autowire());
		ILabelProvider labelProvider;
		if (propertyBinding != null) {
			labelProvider = editUIProvidersFactory.createPropertyBindingLabelProvider(view.getEditingComponent().getEditingContext(), propertyBinding);
		} else {
			labelProvider = editUIProvidersFactory.createLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory());
		}
		propertyEditorViewer.getViewer().setLabelProvider(labelProvider);
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			propertyEditorViewer.getViewer().setLowerBound(((EStructuralFeatureBinding) propertyBinding).getFeature().getLowerBound());
			propertyEditorViewer.getViewer().setUpperBound(((EStructuralFeatureBinding) propertyBinding).getFeature().getUpperBound());
		}
		propertyEditorViewer.getViewer().setInput(view.getEditingComponent().getEObject());
		initListener();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		propertyEditorViewer.getViewer().setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#getPropertyEditorViewer()
	 */
	public PropertyEditorViewer<?> getPropertyEditorViewer() {
		return propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addValue(java.lang.Object)
	 */
	public void addValue(Object value) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addAllValues(java.util.Collection)
	 */
	public void addAllValues(Collection<?> values) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeValue(java.lang.Object)
	 */
	public void removeValue(Object value) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeAllValues(java.util.Collection)
	 */
	public void removeAllValues(Collection<?> values) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#moveValue(java.lang.Object,
	 *      int)
	 */
	public void moveValue(Object value, int newIndex) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	protected void initListener() {
		if (listener == null) {
			listener = new MultiLinePropertyViewerListener(this, view, elementEditor, propertyEditorViewer.getViewer()) {

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#removeAll(java.util
				 *      .Collection)
				 */
				public void removeAll(Collection<?> removedElements) {
					if (isEnabled()) {
						propertyEditor
								.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE_MANY, removedElements, null));
						viewer.refresh();
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#remove(java.lang.
				 *      Object)
				 */
				public void remove(Object removedElement) {
					if (isEnabled()) {
						propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE, removedElement, null));
						viewer.refresh();
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#moveUp(java.lang.
				 *      Object)
				 */
				public void moveUp(Object movedElement) {
					if (isEnabled()) {
						EObject editedElement = view.getEditingComponent().getEObject();
						Object currentValue = eefEditingServiceProvider.getEditingService(editedElement).getValue(view.getEditingComponent().getEditingContext(), editedElement, elementEditor);
						if (currentValue instanceof List<?>) {
							int oldIndex = ((List<?>) currentValue).indexOf(movedElement);
							if (oldIndex > 0) {
								propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex,
										oldIndex - 1));
								viewer.refresh();
							}
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#moveDown(java.lang.
				 *      Object)
				 */
				public void moveDown(Object movedElement) {
					if (isEnabled()) {
						EObject editedElement = view.getEditingComponent().getEObject();
						Object currentValue = eefEditingServiceProvider.getEditingService(editedElement).getValue(view.getEditingComponent().getEditingContext(), editedElement, elementEditor);
						if (currentValue instanceof List<?>) {
							int oldIndex = ((List<?>) currentValue).indexOf(movedElement);
							if (oldIndex < ((List<?>) currentValue).size()) {
								propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex,
										oldIndex + 1));
								viewer.refresh();
							}
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#edit(java.lang.
				 *      Object)
				 */
				public void edit(Object editedElement) {
					if (isEnabled()) {
						propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.EDIT, null, editedElement));
						viewer.refresh();
					}
				}

				/**
				 * {@inheritDoc}
				 * 
				 * @see 
				 *      org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer
				 *      .getViewer().ReferenceEditorListener#add()
				 */
				public void add() {
					if (isEnabled()) {
						propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, null));
						viewer.refresh();
					}
				}
			};
			propertyEditorViewer.getViewer().addReferenceEditorListener(listener);
		}
	}

}
