/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.checkbox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CheckboxPropertyEditor implements PropertyEditor, MonovaluedPropertyEditor {

	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<CheckBox> propertyEditorViewer;

	protected EStructuralFeature feature;
 
	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 * @param viewElement {@link ElementEditor} specifying the Property Editor.
	 */
	public CheckboxPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor, PropertyEditorViewer<CheckBox> propertyEditorViewer) {
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
		Object value = view.getEditingComponent().getEObject().eGet(feature);
		if (value instanceof Boolean) {
			propertyEditorViewer.getViewer().setSelected((Boolean) value);
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
			propertyEditorViewer.getViewer().setSelected((Boolean) value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorViewer.getViewer().setSelected(false);
	}

	private void initListeners() {
		propertyEditorViewer.getViewer().selectedProperty().addListener(new ChangeListener<Boolean>() {

			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (view.getEditingComponent() != null) {
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, newValue));
				}
			}
			
		});
		
	}

}
