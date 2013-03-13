/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.text;

import org.eclipse.emf.eef.runtime.ui.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.text.TextPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.text.TextPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.text.TextSWTPropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPlatformAwarePropertyEditorProvider extends TextPropertyEditorProvider{

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit toolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		if (toolkit != null) {
			return new TextPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement, new TextFormPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement));			
		} else {
			return new TextPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement, new TextSWTPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement));
		}
	}

}
