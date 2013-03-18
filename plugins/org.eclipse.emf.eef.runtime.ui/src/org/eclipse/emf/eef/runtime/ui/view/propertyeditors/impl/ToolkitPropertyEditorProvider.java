/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;


import java.util.List;

import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.toolkits.Toolkit;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class ToolkitPropertyEditorProvider<T> extends AbstractEEFService<PropertyEditorContext<T>> implements ModelPropertyEditorProvider<Toolkit,T>, EEFService<PropertyEditorContext<T>> {

	private List<WidgetPropertyEditorProvider<T>> widgetProviders;
	
	/**
	 * 
	 */
	public ToolkitPropertyEditorProvider() {
		widgetProviders = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#serviceFor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext<T> editorContext) {
		for (WidgetPropertyEditorProvider<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 * TODO: need cache
	 */
	public PropertyEditor getPropertyEditor(PropertyEditorContext<T> editorContext) {
		for (WidgetPropertyEditorProvider<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return provider.getPropertyEditor(editorContext);
			}
		}
		return null;
	}
	
	/**
	 * @return the widgetProviders
	 */
	public List<WidgetPropertyEditorProvider<T>> getWidgetProviders() {
		return widgetProviders;
	}

	/**
	 * @param provider
	 */
	public ToolkitPropertyEditorProvider<T> addPropertyEditorProvider(WidgetPropertyEditorProvider<T> provider) {
		widgetProviders.add(provider);
		getModel().getWidgets().add(provider.getModel());
		return this;
	}
	
	/**
	 * Clears the widgetProviders list.
	 */
	protected final void clearEditorProviders() {
		widgetProviders.clear();
	}
}
