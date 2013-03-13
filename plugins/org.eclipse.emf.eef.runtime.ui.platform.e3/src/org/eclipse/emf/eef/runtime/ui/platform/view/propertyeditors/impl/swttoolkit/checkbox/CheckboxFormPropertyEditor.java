/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.checkbox;

import org.eclipse.emf.eef.runtime.ui.platform.services.view.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.swt.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.swt.services.view.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
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
	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	protected Button checkbox;
	
	private EEFControlWrapperViewer<Button> wrapperViewer;

	/**
	 * @param view
	 * @param viewElement
	 */
	public CheckboxFormPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
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
				 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
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
		ViewService viewService = view.getViewService();
		checkbox = toolkit.createButton(parent, viewService.getDescription(elementEditor, elementEditor.getName()), SWT.CHECK);
		GridData checkboxData = new GridData(GridData.FILL_HORIZONTAL);
		checkboxData.horizontalSpan = 2;
		checkbox.setLayoutData(checkboxData);
		if (viewService instanceof SWTViewService) {
			SWTViewService swtViewService = (SWTViewService)viewService;
			swtViewService.setID(checkbox, elementEditor.getQualifiedIdentifier());
			swtViewService.setEEFtype(checkbox, "eef::Checkbox"); //$NON-NLS-1$
			if (viewService instanceof PlatformAwareViewService) {
				((PlatformAwareViewService)viewService).createHelpButton(toolkit, parent, elementEditor);
			} else {
				swtViewService.createHelpButton(parent, elementEditor);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		checkbox.setEnabled(false);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		checkbox.setEnabled(true);
	}

}
