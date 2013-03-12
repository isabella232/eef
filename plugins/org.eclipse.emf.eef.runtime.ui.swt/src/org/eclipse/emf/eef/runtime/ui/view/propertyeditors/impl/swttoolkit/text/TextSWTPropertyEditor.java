/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTControlPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextSWTPropertyEditor extends StandardSWTControlPropertyEditor<Text> {

	protected Text text;

	/**
	 * @param view
	 * @param viewElement
	 */
	public TextSWTPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		text = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(nameData);
		view.getViewService().setID(text, elementEditor.getQualifiedIdentifier());
		view.getViewService().setEEFtype(text, "eef::Text"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTControlPropertyEditor#getMainControl()
	 */
	public Text getMainControl() {
		return text;
	}

	
	
}
