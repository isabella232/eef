/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComposedPropertyEditorProvider implements PropertyEditorProvider {
	
	private List<PropertyEditorProvider> editorProviders;
	
	/**
	 * 
	 */
	public ComposedPropertyEditorProvider() {
		editorProviders = new ArrayList<PropertyEditorProvider>();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.PropertyEditorProvider#canHandle(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
	 */
	public boolean canHandle(PropertiesEditingView view, ElementEditor editor) {
		for (PropertyEditorProvider provider : editorProviders) {
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
		for (PropertyEditorProvider provider : editorProviders) {
			if (provider.canHandle(view, editor)) {
				return provider.getPropertyEditor(view, editor);
			}
		}
		return null;
	}
	
	/**
	 * @param provider
	 */
	public ComposedPropertyEditorProvider addPropertyEditorProvider(WidgetPropertyEditorProvider provider) {
		editorProviders.add(provider);
		return this;
	}

}
