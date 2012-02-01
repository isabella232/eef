/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.impl.swttoolkit.text;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.WidgetPropertyEditorProvider;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextPropertyEditorProvider implements WidgetPropertyEditorProvider {

	private Widget widget;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.ModelPropertyEditorProvider#getModel()
	 */
	public Widget getModel() {
		if (widget == null) {
			widget = ToolkitsFactory.eINSTANCE.createWidget();
			widget.setName("Text");
		}
		return widget;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.ModelPropertyEditorProvider#canHandle(org.eclipse.emf.eef.views.ElementEditor)
	 */
	public boolean canHandle(PropertiesEditingView view, ElementEditor editor) {
		return getModel() == editor.getRepresentation();
	}

	/**
	 * {@inheritDoc}
	 * @param view 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.ModelPropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.views.ElementEditor)
	 */
	public PropertyEditor getPropertyEditor(PropertiesEditingView view, ElementEditor editor) {
		return new TextPropertyEditor(view, editor);
	}

}
