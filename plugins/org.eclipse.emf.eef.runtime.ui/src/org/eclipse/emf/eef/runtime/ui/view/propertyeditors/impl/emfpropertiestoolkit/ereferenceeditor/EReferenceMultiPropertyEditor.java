/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.ui.EEFRuntimeUI;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MultivaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.EEFSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor;
import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor.ReferenceEditorListener;
import org.eclipse.emf.eef.runtime.ui.widgets.util.ArrayFeatureContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.util.ChoiceOfValuesFilter;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.layout.GridData;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceMultiPropertyEditor implements PropertyEditor, MultivaluedPropertyEditor {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EReferenceEditor> propertyEditorViewer;
	
	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param viewElement
	 */
	public EReferenceMultiPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor, PropertyEditorViewer<EReferenceEditor> propertyEditorViewer) {
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
		AdapterFactory currentAdapterFactory = view.getEditingComponent().getEditingContext().getAdapterFactory();
		if (currentAdapterFactory == null) {
			currentAdapterFactory = EEFRuntimeUI.getPlugin().getRegistryAdapterFactory();
		}
		propertyEditorViewer.getViewer().setContentProvider(new ArrayFeatureContentProvider(this.feature));
		propertyEditorViewer.getViewer().setLabelProvider(new AdapterFactoryLabelProvider(currentAdapterFactory));
		propertyEditorViewer.getViewer().setLowerBound(feature.getLowerBound());
		propertyEditorViewer.getViewer().setUpperBound(feature.getUpperBound());
		propertyEditorViewer.getViewer().setInput(view.getEditingComponent().getEObject());
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
	 * Initialize the listener on the EReferenceEditor.
	 */
	protected void initListener() {
		propertyEditorViewer.getViewer().addReferenceEditorListener(new ReferenceEditorListener() {
			
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
				//TODO: We have to invoke the EditingPropertyPolicy
			}
			
			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.propertyEditorViewer.getViewer().ReferenceEditorListener#add()
			 */
			public void add() {
				EEFSelectionDialog dialog = new EEFSelectionDialog(propertyEditorViewer.getViewer().getControl().getShell(), true);
				dialog.setTitle("Choose the element to add to the " + feature.getName() + " reference:");
				dialog.setAdapterFactory(view.getEditingComponent().getEditingContext().getAdapterFactory());
				dialog.addFilter(
						new ChoiceOfValuesFilter(
								view.getEditingComponent().getEditingContext().getAdapterFactory(), 
								view.getEditingComponent().getEObject(), 
								EReferenceMultiPropertyEditor.this.feature, 
								view.getViewSettings().getSelectionMode()));
				dialog.setInput(view.getViewService().getBestInput(view.getEditingComponent().getEObject()));
				if (dialog.open() == Window.OK) {
					if (dialog.getSelection() != null) {
						if (dialog.getSelection() instanceof Collection<?>) {
							view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD_MANY, null, dialog.getSelection()));
						} else {
							view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, PropertiesEditingEvent.ADD, null, dialog.getSelection()));
						}
						propertyEditorViewer.getViewer().refresh();				
					}
				}
			}
		});
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		propertyEditorViewer.getViewer().setLayoutData(layoutData);
	}

}
