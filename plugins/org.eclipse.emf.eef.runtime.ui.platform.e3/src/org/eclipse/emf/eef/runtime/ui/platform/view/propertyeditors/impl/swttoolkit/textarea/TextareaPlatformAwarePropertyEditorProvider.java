/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.textarea;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.textarea.TextareaPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.textarea.TextareaPropertyEditorProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextareaPlatformAwarePropertyEditorProvider extends TextareaPropertyEditorProvider{

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit toolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(UIConstants.FORM_TOOLKIT);
		if (toolkit != null) {
			return new TextareaPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement, new TextareaFormPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement));			
		} else {
			return super.getPropertyEditor(editorContext);
		}
	}

}
