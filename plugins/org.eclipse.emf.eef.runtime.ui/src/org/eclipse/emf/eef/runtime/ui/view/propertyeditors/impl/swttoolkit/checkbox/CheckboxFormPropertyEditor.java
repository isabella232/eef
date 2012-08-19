/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.checkbox;

import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CheckboxFormPropertyEditor implements FormPropertyEditor<EEFControlWrapperViewer<Button>> {
	protected PropertiesEditingView view;
	protected ElementEditor elementEditor;
	protected Button checkbox;
	
	private EEFControlWrapperViewer<Button> wrapperViewer;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public CheckboxFormPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<Button> getViewer() {
		if (wrapperViewer == null) {
			wrapperViewer = new EEFControlWrapperViewer<Button>() {


				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				@Override
				public Button getMainControl() {
					return checkbox;
				}


			};
		}
		return wrapperViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(FormToolkit toolkit, Composite parent) {
		checkbox = toolkit.createButton(parent, view.getViewService().getDescription(elementEditor, elementEditor.getName()), SWT.CHECK);
		GridData checkboxData = new GridData(GridData.FILL_HORIZONTAL);
		checkboxData.horizontalSpan = 2;
		checkbox.setLayoutData(checkboxData);
		view.getViewService().setID(checkbox, elementEditor.getQualifiedIdentifier());
		view.getViewService().setEEFtype(checkbox, "eef::Checkbox"); //$NON-NLS-1$
		view.getViewService().createHelpButton(parent, elementEditor);
	}

}
