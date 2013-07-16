/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactoryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class DelegatingPropertiesEditingContext implements PropertiesEditingContext {

	protected PropertiesEditingContext delegatingContext;
	
	/**
	 * @param delegatingContext {@link PropertiesEditingContext} to use for delegation.
	 */
	public DelegatingPropertiesEditingContext(PropertiesEditingContext delegatingContext) {
		this.delegatingContext = delegatingContext;
	}

	/**
	 * @return the delegatingContext
	 */
	public final PropertiesEditingContext getDelegatingContext() {
		return delegatingContext;
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return delegatingContext.getEMFServiceProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getBindingManagerProvider()
	 */
	public BindingHandlerProvider getBindingManagerProvider() {
		return delegatingContext.getBindingManagerProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getContextFactoryProvider()
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return delegatingContext.getContextFactoryProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getViewHandlerFactoryProvider()
	 */
	public ViewHandlerFactoryProvider getViewHandlerFactoryProvider() {
		return delegatingContext.getViewHandlerFactoryProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return delegatingContext.getEditingComponent();
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
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		return delegatingContext.getOptions();
	}

	/**
	 * @param editingComponent
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void disposeComponent(PropertiesEditingComponent editingComponent) {
		delegatingContext.disposeComponent(editingComponent);
	}

	/**
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#stopEditing()
	 */
	public void stopEditing() {
		delegatingContext.stopEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#cancelEditing()
	 */
	public void cancelEditing() {
		delegatingContext.cancelEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		delegatingContext.undoEditing();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		delegatingContext.dispose();
	}

}
