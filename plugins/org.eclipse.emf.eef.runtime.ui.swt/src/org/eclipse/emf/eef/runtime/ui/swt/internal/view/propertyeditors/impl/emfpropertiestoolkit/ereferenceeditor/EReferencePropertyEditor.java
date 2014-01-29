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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EReferenceFilter;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditorSettings;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ArrayFeatureContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilderProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferencePropertyEditor extends PropertyEditorImpl implements MultivaluedPropertyEditor {

	protected EMFServiceProvider emfServiceProvider;
	protected EEFEditingServiceProvider eefEditingServiceProvider;
	protected EditUIProvidersFactory editUIProvidersFactory;
	protected ImageManager imageManager;
	protected ViewerFilterBuilderProvider filterBuilderProvider;

	protected PropertiesEditingView<Composite> view;
	protected PropertyBinding propertyBinding;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer;

	private MultiLinePropertyViewerListener listener;

	public EReferencePropertyEditor(EMFServiceProvider emfServiceProvider, EEFEditingServiceProvider eefEditingServiceProvider, EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, ViewerFilterBuilderProvider filterBuilderProvider, PropertiesEditingView<Composite> view, PropertyBinding propertyBinding, ElementEditor elementEditor, PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		this.emfServiceProvider = emfServiceProvider;
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
		this.filterBuilderProvider = filterBuilderProvider;
		this.propertyEditorViewer = propertyEditorViewer;
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyBinding = propertyBinding;
	}

	public void init(PropertyBinding propertyBinding) {
		this.propertyBinding = propertyBinding;
		propertyEditorViewer.getViewer().setContentProvider(new ArrayFeatureContentProvider(eefEditingServiceProvider, view.getEditingComponent().getEditingContext(), propertyBinding));
		ILabelProvider labelProvider;
		if (propertyBinding != null) {
			labelProvider = editUIProvidersFactory.createPropertyBindingLabelProvider(view.getEditingComponent().getEditingContext(), propertyBinding);
		} else {
			labelProvider = editUIProvidersFactory.createLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory());
		}
		propertyEditorViewer.getViewer().setLabelProvider(labelProvider);
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			EStructuralFeature feature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
			propertyEditorViewer.getViewer().setLowerBound(feature.getLowerBound());
			propertyEditorViewer.getViewer().setUpperBound(feature.getUpperBound());
		}
		propertyEditorViewer.getViewer().setInput(view.getEditingComponent().getEObject());
		if (propertyBinding != null) {
			EList<EditorSettings> settings = propertyBinding.getSettings();
			for (EditorSettings editorSettings : settings) {
				if (editorSettings instanceof EReferenceFilter) {
					EReferenceFilter eReferenceFilter = (EReferenceFilter) editorSettings;
					ViewerFilter viewerFilter = filterBuilderProvider.getFilterBuilder(eReferenceFilter).buildFilter(view.getEditingComponent().getEditingContext(), view, eReferenceFilter);
					((FilterablePropertyEditor)propertyEditorViewer).addFilter(viewerFilter);
				}
			}
		}
		initListener();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		propertyEditorViewer.getViewer().setLayoutData(layoutData);
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addValue(java.lang.Object)
	 */
	public void addValue(Object value) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#addAllValues(java.util.Collection)
	 */
	public void addAllValues(Collection<?> values) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeValue(java.lang.Object)
	 */
	public void removeValue(Object value) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#removeAllValues(java.util.Collection)
	 */
	public void removeAllValues(Collection<?> values) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor#moveValue(java.lang.Object, int)
	 */
	public void moveValue(Object value, int newIndex) {
		propertyEditorViewer.getViewer().refresh();
	}

	/**
	 * Initialize the listener on the MultiLinePropertyViewer.
	 */
	private void initListener() {
		if (listener == null) {
			listener = createPropertyViewerListener();
			propertyEditorViewer.getViewer().addReferenceEditorListener(listener);
		}
	}

	/**
	 * Creates the listener to add to the viewer in order to process viewer events. 
	 * @return the {@link MultiLinePropertyViewerListener} to add to the viewer.
	 */
	protected MultiLinePropertyViewerListener createPropertyViewerListener() {
		return new MultiLinePropertyViewerListener() {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#removeAll(java.util.Collection)
			 */
			public void removeAll(Collection<?> removedElements) {
				firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE_MANY, removedElements, null));
				propertyEditorViewer.getViewer().refresh();
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#remove(java.lang.Object)
			 */
			public void remove(Object removedElement) {
				firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE, removedElement, null));
				propertyEditorViewer.getViewer().refresh();
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#moveUp(java.lang.Object)
			 */
			public void moveUp(Object movedElement) {
				PropertiesEditingComponent editingComponent = view.getEditingComponent();
				EObject editedObject = editingComponent.getEObject();
				EObject editedElement = editedObject;
				Object currentValue = eefEditingServiceProvider.getEditingService(editedObject).getValue(editingComponent.getEditingContext(), editedElement, propertyBinding);
				if (currentValue instanceof List<?>) {
					int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
					if (oldIndex > 0) {
						firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex - 1));
						propertyEditorViewer.getViewer().refresh();
					}
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#moveDown(java.lang.Object)
			 */
			public void moveDown(Object movedElement) {
				PropertiesEditingComponent editingComponent = view.getEditingComponent();
				EObject editedObject = editingComponent.getEObject();
				EObject editedElement = editedObject;
				Object currentValue = eefEditingServiceProvider.getEditingService(editedObject).getValue(editingComponent.getEditingContext(), editedElement, propertyBinding);
				if (currentValue instanceof List<?>) {
					int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
					if (oldIndex < ((List<?>) currentValue).size()) {
						firePropertiesChanged(editingComponent, new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex + 1));
						propertyEditorViewer.getViewer().refresh();
					}
				}
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#edit(java.lang.Object)
			 */
			public void edit(Object editedElement) {
				//TODO: We have to invoke the EditingPropertyPolicy
			}

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener#add()
			 */
			public void add() {
				EEFSelectionDialog dialog = new EEFSelectionDialog(propertyEditorViewer.getViewer().getControl().getShell(), true);
				dialog.setTitle("Choose the element to add to the reference:");
				dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
				dialog.setEditUIProvidersFactory(editUIProvidersFactory);
				dialog.setImageManager(imageManager);
				dialog.addFilter(
						new ChoiceOfValuesFilter(
								emfServiceProvider,
								eefEditingServiceProvider,
								view.getEditingComponent().getEditingContext(), 
								view.getEditingComponent().getEObject(), 
								propertyBinding, 
								EEFSWTConstants.DEFAULT_SELECTION_MODE));
				Collection<ViewerFilter> filters = ((FilterablePropertyEditor)propertyEditorViewer).getFilters();
				if (!filters.isEmpty()) {
					for (ViewerFilter viewerFilter : filters) {
						dialog.addFilter(viewerFilter);
					}
				}
				dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
				if (dialog.open() == Window.OK) {
					if (dialog.getSelection() != null) {
						if (dialog.getSelection() instanceof Collection<?>) {
							firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD_MANY, null, dialog.getSelection()));
						} else {
							firePropertiesChanged(view.getEditingComponent(),new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, dialog.getSelection()));
						}
						propertyEditorViewer.getViewer().refresh();				
					}
				}
			}
		};
	}

}
