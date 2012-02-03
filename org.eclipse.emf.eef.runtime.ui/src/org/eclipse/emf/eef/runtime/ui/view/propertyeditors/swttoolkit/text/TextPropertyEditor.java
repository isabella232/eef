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
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SetUnsetPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPropertyEditor extends StandardPropertyEditor implements SetUnsetPropertyEditor {
	
	protected Text text;
	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param editorID
	 */
	public TextPropertyEditor(PropertiesEditingView view, ElementEditor editor) {
		super(view, editor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.StandardPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		text = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(nameData);
		text.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * TODO: Prevent sending event when value hasn't changed. 
			 */
			public void focusLost(FocusEvent e) {
				if (view.getEditingComponent() != null)
					view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, text.getText()));
			}

		});
		text.addKeyListener(new KeyAdapter() {

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
						view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, text.getText()));
				}
			}

		});
		view.getViewHelper().setID(text, elementEditor.getQualifiedIdentifier());
		view.getViewHelper().setEEFtype(text, "eef::Text"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		Object value = ((EObject)view.getEditingComponent().getTarget()).eGet(feature);
		if (value instanceof String) {
			text.setText((String) value);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SetUnsetPropertyEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object value) {
		if (value == null) {
			text.setText("");
		} else if (value instanceof String) {
			text.setText((String) value);
		} else if (value instanceof EObject) {
			Adapter adapt = view.getEditingComponent().getEditingContext().getAdapterFactory().adapt((EObject)value, IItemLabelProvider.class);
			if (adapt instanceof IItemLabelProvider) {
				text.setText(((IItemLabelProvider) adapt).getText(value));
			} else {
				text.setText(value.toString());
			}
		} else {
			text.setText(value.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.SetUnsetPropertyEditor#unsetValue()
	 */
	public void unsetValue() {
		text.setText("");
	}

}
