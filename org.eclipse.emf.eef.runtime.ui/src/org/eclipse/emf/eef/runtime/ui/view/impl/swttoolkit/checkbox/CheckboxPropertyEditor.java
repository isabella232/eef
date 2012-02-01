/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.impl.swttoolkit.checkbox;

import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEventImpl;
import org.eclipse.emf.eef.runtime.notify.TypedPropertyChangedEvent;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor;
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
	private Button checkbox;
 
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Composite parent) {
		checkbox = new Button(parent, SWT.CHECK);
		checkbox.setText(view.getViewHelper().getDescription(elementEditor.getQualifiedIdentifier(), elementEditor.getName()));
		checkbox.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (view.getEditingComponent() != null)
					view.getEditingComponent().fireViewChange(new PropertiesEditingEventImpl(view, elementEditor.getQualifiedIdentifier(), TypedPropertyChangedEvent.SET, null, new Boolean(checkbox.getSelection())));
			}

		});
		GridData gMFSpecificPropertiesViewsData = new GridData(GridData.FILL_HORIZONTAL);
		gMFSpecificPropertiesViewsData.horizontalSpan = 2;
		checkbox.setLayoutData(gMFSpecificPropertiesViewsData);
		view.getViewHelper().setID(checkbox, elementEditor.getQualifiedIdentifier());
		view.getViewHelper().setEEFtype(checkbox, "eef::Checkbox"); //$NON-NLS-1$
		view.getViewHelper().createHelpButton(parent, elementEditor);
	}

}
