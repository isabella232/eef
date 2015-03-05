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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EditorSettings;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.EEFViewerInput;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilderProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboPropertyEditor extends PropertyEditorImpl implements MonovaluedPropertyEditor {

	protected EMFServiceProvider emfServiceProvider;
	protected EEFEditingServiceProvider eefEditingServiceProvider;
	protected EditUIProvidersFactory editUIProvidersFactory;
	protected ImageManager imageManager;
	protected ViewerFilterBuilderProvider filterBuilderProvider;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer;
	private SingleLinePropertyViewerListener listener;

	public EComboPropertyEditor(EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider, EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, ViewerFilterBuilderProvider filterBuilderProvider, PropertiesEditingView<Composite> view,
			ElementEditor elementEditor, PropertyEditorViewer<SingleLinePropertyViewer> propertyEditorViewer) {
		this.emfServiceProvider = emfServiceProvider;
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
		this.filterBuilderProvider = filterBuilderProvider;
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
		EEFViewerInput input = new EEFViewerInput(eefEditingServiceProvider, view.getEditingComponent().getEditingContext(), elementEditor);
		propertyEditorViewer.getViewer().setInput(input);
		propertyEditorViewer.getViewer().setValue();
		PropertyBinding propertyBinding = view.getEditingComponent().getBinding().propertyBinding(elementEditor, view.getEditingComponent().getEditingContext().getOptions().autowire());
		if (propertyBinding != null) {
			EList<EditorSettings> settings = propertyBinding.getSettings();
			for (EditorSettings editorSettings : settings) {
				if (editorSettings instanceof EReferenceFilter) {
					EReferenceFilter eReferenceFilter = (EReferenceFilter) editorSettings;
					ViewerFilter viewerFilter = filterBuilderProvider.getFilterBuilder(eReferenceFilter).buildFilter(view.getEditingComponent().getEditingContext(), view, eReferenceFilter);
					((FilterablePropertyEditor) propertyEditorViewer).addFilter(viewerFilter);
				}
			}
		}
		initListener();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		propertyEditorViewer.getViewer().getControl().setLayoutData(layoutData);
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		listener.disable();
		propertyEditorViewer.getViewer().refresh();
		listener.enable();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		listener.disable();
		propertyEditorViewer.getViewer().setInput(null);
		listener.enable();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	private void initListener() {
		if (listener == null) {
			listener = createPropertyViewerListener();
			propertyEditorViewer.getViewer().addSingleLinePropertyViewerListener(listener);
		}
	}

	/**
	 * Creates the listener to add to the viewer in order to process viewer
	 * events.
	 * 
	 * @return the {@link SingleLinePropertyViewerListener} to add to the
	 *         viewer.
	 */
	protected SingleLinePropertyViewerListener createPropertyViewerListener() {
		return new SingleLinePropertyViewerListener(this, view, elementEditor, propertyEditorViewer.getViewer()) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#set()
			 */
			public void set() {
				if (isEnabled()) {
					EEFSelectionDialog dialog = new EEFSelectionDialog(viewer.getControl().getShell(), true);
					dialog.setTitle("Choose the element to set to the reference:");
					dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
					dialog.setEditUIProvidersFactory(editUIProvidersFactory);
					dialog.setImageManager(imageManager);
					dialog.addFilter(new ChoiceOfValuesFilter(eefEditingServiceProvider, view.getEditingComponent().getEditingContext(), view.getEditingComponent().getEObject(), elementEditor, EEFSWTConstants.DEFAULT_SELECTION_MODE));
					Collection<ViewerFilter> filters = ((FilterablePropertyEditor) propertyEditorViewer).getFilters();
					if (!filters.isEmpty()) {
						for (ViewerFilter viewerFilter : filters) {
							dialog.addFilter(viewerFilter);
						}
					}
					dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() != null) {
							firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.SET, null, dialog.getSelection()));
							viewer.refresh();
						}
					}
				}
			}

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor.EComboListener#clear()
			 */
			public void clear() {
				if (isEnabled()) {
					propertyEditor.firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.UNSET, null, null));
					viewer.refresh();
				}
			}

		};
	}

}
