/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.swt.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.MonovaluedPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.PropertyEditorImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPropertyEditor extends PropertyEditorImpl implements  MonovaluedPropertyEditor {

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorControl;

	protected EStructuralFeature feature;

	/**
	 * @param view
	 * @param viewElement
	 * @param propertyEditorViewer
	 */
	public TextPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor, PropertyEditorViewer<EEFControlWrapperViewer<Text>> propertyEditorViewer) {
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
			propertyEditorControl.getViewer().getMainControl().setText((String) value);
		} else {
			propertyEditorControl.getViewer().getMainControl().setText(value == null?"":value.toString());			
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
		propertyEditorControl.getViewer().getMainControl().addModifyListener(new ModifyListener() {
			
			public void modifyText(ModifyEvent e) {
				if (view.getEditingComponent() != null)
					firePropertiesChanged(view.getEditingComponent(), new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, propertyEditorControl.getViewer().getMainControl().getText(),true));
			}
		});
	}		

}
