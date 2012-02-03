/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.checkbox;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CheckboxPropertyEditor implements PropertyEditor {

	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	protected Button checkbox;
	protected EStructuralFeature feature;
 
	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 * @param elementEditor {@link ElementEditor} specifying the Property Editor.
	 */
	public CheckboxPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Composite parent) {
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setText(view.getViewHelper().getDescription(elementEditor, elementEditor.getName()));
		checkbox.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (view.getEditingComponent() != null)
					view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor, TypedPropertyChangedEvent.SET, null, new Boolean(checkbox.getSelection())));
			}

		});
		GridData gMFSpecificPropertiesViewsData = new GridData(GridData.FILL_HORIZONTAL);
		gMFSpecificPropertiesViewsData.horizontalSpan = 2;
		checkbox.setLayoutData(gMFSpecificPropertiesViewsData);
		view.getViewHelper().setID(checkbox, elementEditor.getQualifiedIdentifier());
		view.getViewHelper().setEEFtype(checkbox, "eef::Checkbox"); //$NON-NLS-1$
		view.getViewHelper().createHelpButton(parent, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#init(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public void init(EStructuralFeature feature) {
		this.feature = feature;
		Object value = ((EObject)view.getEditingComponent().getTarget()).eGet(feature);
		if (value instanceof Boolean) {
			checkbox.setSelection((Boolean) value);
		}
		
	}

}
