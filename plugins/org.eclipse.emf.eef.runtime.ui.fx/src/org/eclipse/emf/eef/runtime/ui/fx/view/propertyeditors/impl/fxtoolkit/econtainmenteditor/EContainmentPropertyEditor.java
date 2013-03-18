/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.econtainmenteditor;

import java.util.Collection;
import java.util.List;

import javafx.scene.layout.Pane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.fx.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.fx.widgets.MultiLinePropertyViewer.MultiLinePropertyViewerListener;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EContainmentPropertyEditor implements PropertyEditor, MultivaluedPropertyEditor {

	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer;

	protected EStructuralFeature feature;
	private MultiLinePropertyViewerListener listener;

	/**
	 * @param view
	 * @param viewElement
	 */
	public EContainmentPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor, PropertyEditorViewer<MultiLinePropertyViewer> propertyEditorViewer) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorViewer = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
//		propertyEditorViewer.getViewer().setContentProvider(new ArrayFeatureContentProvider(this.feature));
//		EditUIProvidersFactory providersFactory = view.getEditingComponent().getEditingContext().getServiceRegistry().getService(EditUIProvidersFactory.class, this);
//		PropertyBinding propertyBinding = view.getEditingComponent().getBinding().propertyBinding(elementEditor, view.getEditingComponent().getEditingContext().getOptions().autowire());
//		ILabelProvider labelProvider;
//		if (propertyBinding != null) {
//			labelProvider = providersFactory.createPropertyBindingLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory(), propertyBinding);
//		} else {
//			labelProvider = providersFactory.createLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory());
//		}
//		propertyEditorViewer.getViewer().setLabelProvider(labelProvider);
		MultiLinePropertyViewer viewer = propertyEditorViewer.getViewer();
		viewer.setLowerBound(feature.getLowerBound());
		viewer.setUpperBound(feature.getUpperBound());
		PropertiesEditingComponent editingComponent = view.getEditingComponent();
		viewer.setInput(editingComponent.getEditingContext().getAdapterFactory(), editingComponent.getEObject(), feature);
		initListener();
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
	protected void initListener() {
		if (listener == null) {
			listener = new MultiLinePropertyViewerListener() {

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#removeAll(java.util.Collection)
				 */
				public void removeAll(Collection<?> removedElements) {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE_MANY, removedElements, null));
					propertyEditorViewer.getViewer().refresh();
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#remove(java.lang.Object)
				 */
				public void remove(Object removedElement) {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.REMOVE, removedElement, null));
					propertyEditorViewer.getViewer().refresh();
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#moveUp(java.lang.Object)
				 */
				public void moveUp(Object movedElement) {
					EObject editedElement = view.getEditingComponent().getEObject();
					Object currentValue = editedElement.eGet(feature);
					if (currentValue instanceof List<?>) {
						int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
						if (oldIndex > 0) {
							view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex - 1));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#moveDown(java.lang.Object)
				 */
				public void moveDown(Object movedElement) {
					EObject editedElement = view.getEditingComponent().getEObject();
					Object currentValue = editedElement.eGet(feature);
					if (currentValue instanceof List<?>) {
						int oldIndex = ((List<?>)currentValue).indexOf(movedElement);
						if (oldIndex < ((List<?>) currentValue).size()) {
							view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.MOVE, oldIndex, oldIndex + 1));
							propertyEditorViewer.getViewer().refresh();
						}
					}
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#edit(java.lang.Object)
				 */
				public void edit(Object editedElement) {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.EDIT, null, editedElement));
					propertyEditorViewer.getViewer().refresh();
				}

				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#add()
				 */
				public void add() {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, null));
				}
			};
			propertyEditorViewer.getViewer().addReferenceEditorListener(listener);
		}
	}

}
