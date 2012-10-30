/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.undefined.editor;

import org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.util.EEFControlWrapperViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UndefinedFormPropertyEditor extends AbstractUndefinedPropertyEditor implements FormPropertyEditor<EEFControlWrapperViewer<Label>> {

	private ViewElement viewElement;

	private EEFControlWrapperViewer<Label> wrapperViewer;
	private Label label;

	/**
	 * @param viewElement
	 */
	public UndefinedFormPropertyEditor(ViewElement viewElement) {
		this.viewElement = viewElement;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EEFControlWrapperViewer<Label> getViewer() {
		if (wrapperViewer == null) {
			wrapperViewer = new EEFControlWrapperViewer<Label>() {


				/**
				 * {@inheritDoc}
				 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.util.EEFControlWrapperViewer#getMainControl()
				 */
				@Override
				public Label getMainControl() {
					return label;
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
		label = toolkit.createLabel(parent, buildErrorMessage(viewElement));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		label.setLayoutData(gd);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		//Do nothing.
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		//Do nothing.
	}

}
