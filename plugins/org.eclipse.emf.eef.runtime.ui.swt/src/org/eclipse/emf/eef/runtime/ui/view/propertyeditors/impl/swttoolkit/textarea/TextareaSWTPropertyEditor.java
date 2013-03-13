/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.textarea;

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
public class TextareaSWTPropertyEditor extends StandardSWTControlPropertyEditor<Text> {

	protected Text text;

	/**
	 * @param view
	 * @param viewElement
	 */
	public TextareaSWTPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		text = view.getViewService().createScrollableText(parent, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
		GridData textData = new GridData(GridData.FILL_HORIZONTAL);
		textData.heightHint = 80;
		textData.widthHint = 200;
		text.setLayoutData(textData);
		view.getViewService().setID(text, elementEditor.getQualifiedIdentifier());
		view.getViewService().setEEFtype(text, "eef::Textarea"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTControlPropertyEditor#getMainControl()
	 */
	public Text getMainControl() {
		return text;
	}

	
	
}
