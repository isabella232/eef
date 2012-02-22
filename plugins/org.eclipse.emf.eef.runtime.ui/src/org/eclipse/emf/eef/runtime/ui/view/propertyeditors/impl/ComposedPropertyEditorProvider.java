/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComposedPropertyEditorProvider implements PropertyEditorProvider {
	
	private List<PropertyEditorProvider> editorProviders;
	
	/**
	 * @param editorProviders
	 */
	private ComposedPropertyEditorProvider(List<PropertyEditorProvider> editorProviders) {
		this.editorProviders = editorProviders;
	}

	/**
	 * @return the editorProviders
	 */
	public List<PropertyEditorProvider> getPropertyEditorProviders() {
		return editorProviders;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider#canHandle(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView, org.eclipse.emf.eef.views.ElementEditor)
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
	
	public static class Builder {
		
		private List<PropertyEditorProvider> propertyEditorProviders;
		
		/**
		 * 
		 */
		public Builder() {
			propertyEditorProviders = new ArrayList<PropertyEditorProvider>();
		}

		/**
		 * @param provider {@link PropertyEditorProvider} to add to the {@link ComposedPropertyEditorProvider}.
		 */
		public Builder addPropertyEditorProvider(PropertyEditorProvider provider) {
			propertyEditorProviders.add(provider);
			return this;
		}
		
		/**
		 * @return the {@link ComposedPropertyEditorProvider}.
		 */
		public ComposedPropertyEditorProvider build() {
			return new ComposedPropertyEditorProvider(propertyEditorProviders);
		}
	
	}
}
