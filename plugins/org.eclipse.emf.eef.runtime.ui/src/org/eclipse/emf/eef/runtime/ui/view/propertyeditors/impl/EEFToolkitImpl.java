/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;


import java.util.List;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorFactory.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EEFToolkitImpl<T> extends AbstractEEFService<PropertyEditorContext> implements EEFToolkit<T> {
	
	private BindingManagerProvider bindingManagerProvider;

	/**
	 * @param bindingManagerProvider the bindingManagerProvider to set
	 */
	public final void setBindingManagerProvider(BindingManagerProvider bindingManagerProvider) {
		this.bindingManagerProvider = bindingManagerProvider;
	}

	private List<WidgetPropertyEditorFactory<T>> widgetProviders;
	
	/**
	 * 
	 */
	public EEFToolkitImpl() {
		widgetProviders = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public final boolean serviceFor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorFactory<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 * TODO: need cache
	 */
	public final PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorFactory<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return provider.getPropertyEditor(editorContext);
			}
		}
		return null;
	}
	
	/**
	 * @return the widgetProviders
	 */
	public final List<WidgetPropertyEditorFactory<T>> getWidgetProviders() {
		return widgetProviders;
	}

	/**
	 * @param provider
	 */
	public final EEFToolkitImpl<T> addPropertyEditorFactory(WidgetPropertyEditorFactory<T> provider) {
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
		bindingManagerProvider.getBindingManager(editingComponent.getEObject()).firePropertiesChanged(editingComponent, editingEvent);
	}
	
	/**
	 * Clears the widgetProviders list.
	 */
	protected final void clearEditorProviders() {
		widgetProviders.clear();
	}
}
