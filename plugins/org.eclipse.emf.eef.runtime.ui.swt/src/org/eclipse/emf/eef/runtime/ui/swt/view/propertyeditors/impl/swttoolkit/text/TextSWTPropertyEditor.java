/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.swt.services.view.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTControlPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
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
	public TextSWTPropertyEditor(PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		text = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(nameData);
		ViewService viewService = view.getViewService();
		if (viewService instanceof SWTViewService) {
			SWTViewService swtViewService = (SWTViewService)viewService;
			swtViewService.setID(text, elementEditor.getQualifiedIdentifier());
			swtViewService.setEEFtype(text, "eef::Text"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTControlPropertyEditor#getMainControl()
	 */
	public Text getMainControl() {
		return text;
	}

	
	
}
