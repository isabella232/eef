/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DelegatingPropertiesEditingContext implements PropertiesEditingContext {

	private PropertiesEditingContext delegatingContext;
	
	/**
	 * @param delegatingContext {@link PropertiesEditingContext} to use for delegation.
	 */
	public DelegatingPropertiesEditingContext(PropertiesEditingContext delegatingContext) {
		this.delegatingContext = delegatingContext;
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		return delegatingContext.getEditingModel();
	}

	/**
	 * @param editingModel
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setEditingModel(org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel)
	 */
	public void setEditingModel(PropertiesEditingModel editingModel) {
		delegatingContext.setEditingModel(editingModel);
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getViewHandlerProvider()
	 */
	public ViewHandlerProvider getViewHandlerProvider() {
		return delegatingContext.getViewHandlerProvider();
	}

	/**
	 * @param viewHandlerProvider
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setViewHandlerProvider(org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider)
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		delegatingContext.setViewHandlerProvider(viewHandlerProvider);
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return delegatingContext.getEditingComponent();
	}

	/**
	 * @param context
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
		return delegatingContext.getEditingPolicy(context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		return delegatingContext.getAdapterFactory();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		delegatingContext.dispose();
	}
}
