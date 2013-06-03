/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.handlers.editingview;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingViewHandlerProvider extends AbstractEEFService<Object> implements ViewHandlerProvider {

	private BindingManagerProvider bindingManagerProvider;
	private ViewServiceProvider viewServiceProvider;
	private ToolkitPropertyEditorProvider toolkitPropertyEditorProvider;

	/**
	 * @param bindingManagerProvider the bindingManagerProvider to set
	 */
	public void setBindingManagerProvider(BindingManagerProvider bindingManagerProvider) {
		this.bindingManagerProvider = bindingManagerProvider;
	}

	/**
	 * @param viewServiceProvider the viewServiceProvider to set
	 */
	public void setViewServiceProvider(ViewServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

	/**
	 * @param toolkitPropertyEditorProvider the toolkitPropertyEditorProvider to set
	 */
	public void setToolkitPropertyEditorProvider(ToolkitPropertyEditorProvider toolkitPropertyEditorProvider) {
		this.toolkitPropertyEditorProvider = toolkitPropertyEditorProvider;
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
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider#getLockManager(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent, java.lang.Object)
	 */
	public EEFLockManager getLockManager(PropertiesEditingComponent component, Object view) {
		return bindingManagerProvider.getBindingManager(component).getLockManager(view);
	}

	/**
	 * @return the viewServiceProvider
	 */
	public ViewServiceProvider getViewServiceProvider() {
		return viewServiceProvider;
	}

	public ToolkitPropertyEditorProvider getToolkitPropertyEditorProvider() {
		return toolkitPropertyEditorProvider;
	}

}
