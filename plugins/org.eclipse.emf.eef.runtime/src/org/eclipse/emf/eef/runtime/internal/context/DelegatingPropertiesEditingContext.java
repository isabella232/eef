/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class DelegatingPropertiesEditingContext implements PropertiesEditingContext {

	protected PropertiesEditingContext delegatingContext;
	
	/**
	 * @return the delegatingContext
	 */
	public final PropertiesEditingContext getDelegatingContext() {
		return delegatingContext;
	}

	/**
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getServiceRegistry()
	 */
	public EEFServiceRegistry getServiceRegistry() {
		return delegatingContext.getServiceRegistry();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setServiceRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl)
	 */
	public void setServiceRegistry(EEFServiceRegistry serviceRegistry) {
		delegatingContext.setServiceRegistry(serviceRegistry);		
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return delegatingContext.getEMFServiceProvider();
	}

	/**
	 * @param notificationManager
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#setNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void setNotificationManager(ModelChangesNotificationManager notificationManager) {
		delegatingContext.setNotificationManager(notificationManager);
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
