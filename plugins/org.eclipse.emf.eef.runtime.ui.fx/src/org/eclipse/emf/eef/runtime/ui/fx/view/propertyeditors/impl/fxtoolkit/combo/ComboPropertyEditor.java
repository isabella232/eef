/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo.ComboUIPropertyEditor.ComboContentProviderInput;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComboPropertyEditor implements PropertyEditor, MonovaluedPropertyEditor {

	protected PropertiesEditingView<Pane> view;
	protected PropertyEditorViewer<ComboBox<Object>> propertyEditorControl;
	protected ElementEditor elementEditor;

	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param viewElement
	 * @param propertyEditorViewer
	 */
	public ComboPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor, PropertyEditorViewer<ComboBox<Object>> propertyEditorViewer) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.propertyEditorControl = propertyEditorViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		if (propertyEditorControl instanceof ComboUIPropertyEditor) {
			((ComboUIPropertyEditor) propertyEditorControl).initCombo(new ComboContentProviderInput(view.getEditingComponent().getEditingContext(), feature));
		}
		setValue(view.getEditingComponent().getEObject().eGet(feature));
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
		propertyEditorControl.getViewer().setValue(value);			
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorControl.getViewer().setValue(null);
	}

	private void initListeners() {
		propertyEditorControl.getViewer().valueProperty().addListener(new ChangeListener<Object>() {

			/**
			 * {@inheritDoc}
			 * @see javafx.beans.value.ChangeListener#changed(javafx.beans.value.ObservableValue, java.lang.Object, java.lang.Object)
			 */
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
				if (view.getEditingComponent() != null) {
					if (newValue instanceof Enumerator) {
						Enumerator enumerator = (Enumerator)newValue;
						view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, newValue, true));
					}
				}
			}
			
		});
		
	}		

}
