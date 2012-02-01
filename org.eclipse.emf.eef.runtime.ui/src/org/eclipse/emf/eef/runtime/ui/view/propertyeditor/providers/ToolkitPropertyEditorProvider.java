/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.Toolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class ToolkitPropertyEditorProvider implements ModelPropertyEditorProvider<Toolkit> {

	private List<WidgetPropertyEditorProvider> widgetProviders;
	
	/**
	 * 
	 */
	public ToolkitPropertyEditorProvider() {
		widgetProviders = new ArrayList<WidgetPropertyEditorProvider>();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.PropertyEditorProvider#canHandle(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
	 */
	public boolean canHandle(PropertiesEditingView view, ElementEditor editor) {
		for (WidgetPropertyEditorProvider provider : widgetProviders) {
			if (provider.canHandle(view, editor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
	 * TODO: need cache
	 */
	public PropertyEditor getPropertyEditor(PropertiesEditingView view, ElementEditor editor) {
		for (WidgetPropertyEditorProvider provider : widgetProviders) {
			if (provider.canHandle(view, editor)) {
				return provider.getPropertyEditor(view, editor);
			}
		}
		return null;
	}
	
	/**
	 * @param provider
	 */
	public ToolkitPropertyEditorProvider addPropertyEditorProvider(WidgetPropertyEditorProvider provider) {
		widgetProviders.add(provider);
		getModel().getWidgets().add(provider.getModel());
		return this;
	}
}
