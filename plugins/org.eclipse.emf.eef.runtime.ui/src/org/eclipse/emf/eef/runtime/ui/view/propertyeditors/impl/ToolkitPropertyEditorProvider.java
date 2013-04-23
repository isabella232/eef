/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;


import java.util.List;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class ToolkitPropertyEditorProvider<T> extends AbstractEEFService<PropertyEditorContext<T>> implements ModelPropertyEditorProvider<Toolkit,T>, EEFService<PropertyEditorContext<T>> {
	
	private BindingManagerProvider bindingManagerProvider;

	/**
	 * @param bindingManagerProvider the bindingManagerProvider to set
	 */
	public final void setBindingManagerProvider(BindingManagerProvider bindingManagerProvider) {
		this.bindingManagerProvider = bindingManagerProvider;
	}

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
	public final boolean serviceFor(PropertyEditorContext<T> editorContext) {
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
	public final PropertyEditor getPropertyEditor(PropertyEditorContext<T> editorContext) {
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
	public final List<WidgetPropertyEditorProvider<T>> getWidgetProviders() {
		return widgetProviders;
	}

	/**
	 * @param provider
	 */
	public final ToolkitPropertyEditorProvider<T> addPropertyEditorProvider(WidgetPropertyEditorProvider<T> provider) {
		widgetProviders.add(provider);
		provider.setToolkit(this);
		getModel().getWidgets().add(provider.getModel());
		return this;
	}
	
	
	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		bindingManagerProvider.getBindingManager(editingComponent).firePropertiesChanged(editingComponent, editingEvent);
	}
	
	/**
	 * Clears the widgetProviders list.
	 */
	protected final void clearEditorProviders() {
		widgetProviders.clear();
	}
}
