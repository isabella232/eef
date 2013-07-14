/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormControlPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextFormPropertyEditor extends StandardFormControlPropertyEditor<Text> {

	private Text text;
	
	/**
	 * @param view
	 * @param viewElement
	 */
	public TextFormPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardFormPropertyEditor#createEditorContents(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(FormToolkit toolkit, Composite parent) {
		text = toolkit.createText(parent, "", SWT.NONE);
		text.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(parent);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(nameData);
		if (view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).setID(text, elementEditor.getQualifiedIdentifier());
			((SWTViewService) view.getViewService()).setEEFtype(text, "eef::Text"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardFormControlPropertyEditor#getMainControl()
	 */
	public Text getMainControl() {
		return text;
	}

	
}
