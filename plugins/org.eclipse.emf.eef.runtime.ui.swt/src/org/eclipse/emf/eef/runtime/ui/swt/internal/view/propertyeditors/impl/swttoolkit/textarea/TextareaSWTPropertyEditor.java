/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.textarea;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.StandardSWTControlPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
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
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		ViewService viewService = view.getViewService();
		SWTViewService swtViewService = null;
		if (viewService instanceof SWTViewService) {
			swtViewService = (SWTViewService)viewService;
		}
		if (swtViewService != null) {
			GridData textData = new GridData(GridData.FILL_HORIZONTAL);
			text = swtViewService.createScrollableText(parent, SWT.BORDER | SWT.WRAP | SWT.MULTI | SWT.V_SCROLL);
			textData.heightHint = 80;
			textData.widthHint = 200;
			text.setLayoutData(textData);
			swtViewService.setID(text, elementEditor.getQualifiedIdentifier());
			swtViewService.setEEFtype(text, "eef::Textarea"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.StandardSWTControlPropertyEditor#getMainControl()
	 */
	public Text getMainControl() {
		return text;
	}

	
	
}
