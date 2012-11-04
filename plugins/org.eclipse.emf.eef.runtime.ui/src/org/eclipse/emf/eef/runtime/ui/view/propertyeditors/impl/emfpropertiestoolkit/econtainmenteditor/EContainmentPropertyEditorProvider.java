/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor;

import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EContainmentPropertyEditorProvider implements WidgetPropertyEditorProvider {

	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();
	
	static {
		widget.setName("EContainementEditor");
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#getModel()
	 */
	public Widget getModel() {
		return widget;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#serviceFor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
	 */
	public PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit toolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(UIConstants.FORM_TOOLKIT);
		if (toolkit != null) {
			return new EContainmentPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement, new EEContainmentFormPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement));			
		} else {
			return new EContainmentPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement, new EContainmentSWTPropertyEditor(editorContext.view, (ElementEditor) editorContext.viewElement));
		}
	}

}
