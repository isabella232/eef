/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.osgi.service.event.EventAdmin;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class DelegatingPropertiesEditingContext implements PropertiesEditingContext {

	private PropertiesEditingContext delegatingContext;
	
	/**
	 * @param delegatingContext {@link PropertiesEditingContext} to use for delegation.
	 */
	public DelegatingPropertiesEditingContext(PropertiesEditingContext delegatingContext) {
		this.delegatingContext = delegatingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setEmfServiceProvider(org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider)
	 */
	public void setEmfServiceProvider(EMFServiceProvider emfServiceProvider) {
		delegatingContext.setEmfServiceProvider(emfServiceProvider);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setPropertiesEditingProviderRegistry(org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry)
	 */
	public void setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry) {
		delegatingContext.setPropertiesEditingProviderRegistry(propertiesEditingProviderRegistry);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setEventAdmin(org.osgi.service.event.EventAdmin)
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		delegatingContext.setEventAdmin(eventAdmin);
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
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		return delegatingContext.getOptions();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFService()
	 */
	public EMFService getEMFService() {
		return delegatingContext.getEMFService();
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
