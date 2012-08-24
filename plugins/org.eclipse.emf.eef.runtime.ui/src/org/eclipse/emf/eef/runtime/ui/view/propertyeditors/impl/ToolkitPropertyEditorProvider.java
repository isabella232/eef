/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorProvider provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 * TODO: need cache
	 */
	public PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorProvider provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return provider.getPropertyEditor(editorContext);
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
