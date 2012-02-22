/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.text;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.impl.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPropertyEditor implements PropertyEditor, MonovaluedPropertyEditor {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorControl;

	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param elementEditor
	 * @param propertyEditorViewer
	 */
	public TextPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor, PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorViewer) {
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
		Object value = ((EObject)view.getEditingComponent().getTarget()).eGet(feature);
		if (value instanceof String) {
			propertyEditorControl.getViewer().getMainControl().setText((String) value);
		}
		initListeners();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value == null) {
			propertyEditorControl.getViewer().getMainControl().setText("");
		} else if (value instanceof String) {
			propertyEditorControl.getViewer().getMainControl().setText((String) value);
		} else if (value instanceof EObject) {
			Adapter adapt = view.getEditingComponent().getEditingContext().getAdapterFactory().adapt((EObject)value, IItemLabelProvider.class);
			if (adapt instanceof IItemLabelProvider) {
				propertyEditorControl.getViewer().getMainControl().setText(((IItemLabelProvider) adapt).getText(value));
			} else {
				propertyEditorControl.getViewer().getMainControl().setText(value.toString());
			}
		} else {
			propertyEditorControl.getViewer().getMainControl().setText(value.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		propertyEditorControl.getViewer().getMainControl().setText("");
	}

	private void initListeners() {
		propertyEditorControl.getViewer().getMainControl().addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * TODO: Prevent sending event when value hasn't changed. 
			 */
			public void focusLost(FocusEvent e) {
				if (view.getEditingComponent() != null)
					view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, propertyEditorControl.getViewer().getMainControl().getText()));
			}

		});
		propertyEditorControl.getViewer().getMainControl().addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * TODO: Prevent sending event when value hasn't changed. 
			 * 
			 */
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (view.getEditingComponent() != null)
						view.getEditingComponent().firePropertiesChanged(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, propertyEditorControl.getViewer().getMainControl().getText()));
				}
			}

		});
	}		

}
