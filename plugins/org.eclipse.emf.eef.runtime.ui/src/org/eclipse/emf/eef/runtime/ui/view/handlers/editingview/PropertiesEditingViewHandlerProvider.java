/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewServiceRegistry;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandlerProvider implements ViewHandlerProvider {

	private ViewServiceRegistry viewServiceRegistry;
	private PropertyEditorProviderRegistry editorProviderRegistry;
	
	/**
	 * Returns the {@link PropertyEditorProvider} able to process the given {@link PropertyEditorContext}.
	 * @param editorContext the {@link PropertyEditorContext} to process
	 * @return {@link PropertyEditorProvider} able to process the given {@link PropertyEditorContext}
	 */
	public PropertyEditorProvider getPropertyEditorProvider(PropertyEditorContext editorContext) {
		return editorProviderRegistry.getPropertyEditorProvider(editorContext);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object view) {
		return view instanceof View;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public ViewHandler<?> getHandler(PropertiesEditingComponent editingComponent, Object view) {
		return new PropertiesEditingViewHandler(this, editingComponent, (View) view);
	}

	/**
	 * Returns the {@link ViewServiceRegistry} to use in the views created via this provider.
	 * @return the {@link ViewServiceRegistry} to use.
	 */
	public ViewServiceRegistry getViewServiceRegistry() {
		return viewServiceRegistry;
	}

	/**
	 * Defines the {@link ViewServiceRegistry} to use in the views created via this provider. 
	 * @param viewServiceRegistry the viewServiceRegistry to set
	 */
	public void setViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
		this.viewServiceRegistry = viewServiceRegistry;
	}

	/**
	 * Unsets the {@link ViewServiceRegistry} used in the views created via this provider. 
	 * @param viewServiceRegistry the viewServiceRegistry to unset
	 */
	public void unsetViewServiceRegistry(ViewServiceRegistry viewServiceRegistry) {
		if (viewServiceRegistry == this.viewServiceRegistry) {
			this.viewServiceRegistry = null;
		}
	}

	/**
	 * @return the {@link PropertyEditorProviderRegistry} to use for {@link PropertyEditor} creation.
	 */
	public PropertyEditorProviderRegistry getPropertyEditorProviderRegistry() {
		return editorProviderRegistry;
	}
	
	/**
 	 * Defines the {@link PropertyEditorProviderRegistry} to use in this provider.
	 * @param editorProviderRegistry the editorProviderRegistry to set
	 */
	public void setEditorProviderRegistry(PropertyEditorProviderRegistry editorProviderRegistry) {
		this.editorProviderRegistry = editorProviderRegistry;
	}

 	/**
 	 * Unsets the {@link PropertyEditorProviderRegistry} used in this provider.
	 * @param editorProviderRegistry the editorProviderRegistry to unset
	 */
	public void unsetEditorProviderRegistry(PropertyEditorProviderRegistry editorProviderRegistry) {
		if (editorProviderRegistry == this.editorProviderRegistry) {
			this.editorProviderRegistry = null;
		}
	}

	// Build the EEF default PropertyEditorProviders (i.e. SWTToolkit and EMFPropertiesToolkit).
//	private PropertyEditorProvider buildPropertyEditorProvider() {
//		return new ComposedPropertyEditorProvider.Builder()
//						.addPropertyEditorProvider(new SWTToolkit())
//						.addPropertyEditorProvider(new EMFPropertiesToolkit())
//						.build();
//	}

}
