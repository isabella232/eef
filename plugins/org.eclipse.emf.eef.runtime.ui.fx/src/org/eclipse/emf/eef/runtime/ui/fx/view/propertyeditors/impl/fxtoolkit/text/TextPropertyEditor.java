/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.text;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
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
public class TextPropertyEditor implements PropertyEditor, MonovaluedPropertyEditor {

	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<TextField> propertyEditorControl;

	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param viewElement
	 * @param propertyEditorViewer
	 */
	public TextPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor, PropertyEditorViewer<TextField> propertyEditorViewer) {
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
		Object value = view.getEditingComponent().getEObject().eGet(feature);
		if (value instanceof String) {
			propertyEditorControl.getViewer().setText((String) value);
		} else {
			propertyEditorControl.getViewer().setText(value == null?"":value.toString());			
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
		String textValue;
		if (value == null) {
			textValue = "";
		} else if (value instanceof String) {
			textValue = (String) value;
		} else if (value instanceof EObject) {
			Adapter adapt = view.getEditingComponent().getEditingContext().getAdapterFactory().adapt((EObject)value, IItemLabelProvider.class);
			if (adapt instanceof IItemLabelProvider) {
				textValue = ((IItemLabelProvider) adapt).getText(value);
			} else {
				textValue = value.toString();
			}
		} else {
			textValue = value.toString();
		}
		TextField viewer = propertyEditorControl.getViewer();
		if (!textValue.equals(viewer.getText())) {
			viewer.setText(textValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorControl.getViewer().setText("");
	}

	private void initListeners() {
		propertyEditorControl.getViewer().setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, propertyEditorControl.getViewer().getText(),true));				
			}
			
		});
		
	}		

}
