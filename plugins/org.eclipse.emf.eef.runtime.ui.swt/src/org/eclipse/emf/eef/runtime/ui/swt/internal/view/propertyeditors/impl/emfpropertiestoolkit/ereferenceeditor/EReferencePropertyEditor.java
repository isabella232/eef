/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferencePropertyEditor extends PropertyEditorImpl implements MultivaluedPropertyEditor {

	protected EditUIProvidersFactory editUIProvidersFactory;
	protected ImageManager imageManager;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer;

	protected EStructuralFeature feature;
	private MultiLinePropertyViewerListener listener;

	public EReferencePropertyEditor(EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		propertyEditorViewer.getViewer().setContentProvider(new ArrayFeatureContentProvider(this.feature));
		PropertyBinding propertyBinding = view.getEditingComponent().getBinding().propertyBinding(elementEditor, view.getEditingComponent().getEditingContext().getOptions().autowire());
		ILabelProvider labelProvider;
		if (propertyBinding != null) {
			labelProvider = editUIProvidersFactory.createPropertyBindingLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory(), propertyBinding);
		} else {
			labelProvider = editUIProvidersFactory.createLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory());
		}
		propertyEditorViewer.getViewer().setLabelProvider(labelProvider);
		propertyEditorViewer.getViewer().setLowerBound(feature.getLowerBound());
		propertyEditorViewer.getViewer().setUpperBound(feature.getUpperBound());
		propertyEditorViewer.getViewer().setInput(view.getEditingComponent().getEObject());
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
				EObject editedElement = view.getEditingComponent().getEObject();
				Object currentValue = editedElement.eGet(feature);
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
				EObject editedElement = view.getEditingComponent().getEObject();
				Object currentValue = editedElement.eGet(feature);
				if (currentValue instanceof List<?>) {
					int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
					if (oldIndex < ((List<?>) currentValue).size()) {
						firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex + 1));
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
				dialog.setTitle("Choose the element to add to the " + feature.getName() + " reference:");
				dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
				dialog.setEditUIProvidersFactory(editUIProvidersFactory);
				dialog.setImageManager(imageManager);
				dialog.addFilter(
						new ChoiceOfValuesFilter(
								view.getEditingComponent().getEditingContext().getAdapterFactory(), 
								view.getEditingComponent().getEObject(), 
								EReferencePropertyEditor.this.feature, 
								EEFSWTConstants.DEFAULT_SELECTION_MODE));
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
