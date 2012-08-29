/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingProviderRegistryImpl extends EEFServiceRegistry<EPackage, PropertiesEditingProvider> implements PropertiesEditingProviderRegistry {

	private EMFServiceProvider emfServiceProvider;
	private ViewHandlerProviderRegistry viewHandlerProviderRegistry;
	private ModelChangesNotificationManager notificationManager;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#setEMFServiceProvider(EMFServiceRegistry)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#unsetEMFServiceRegistry(org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry)
	 */
	public void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		if (emfServiceProvider == this.emfServiceProvider) {
			this.emfServiceProvider = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#setViewHandlerProviderRegistry(ViewHandlerProviderRegistry)
	 */
	public void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		this.viewHandlerProviderRegistry = viewHandlerProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#unsetViewHandlerProviderRegistry(org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry)
	 */
	public void unsetViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		if (viewHandlerProviderRegistry == this.viewHandlerProviderRegistry) {
			this.viewHandlerProviderRegistry = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#setModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void setModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#unsetModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void unsetModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
		if (notificationManager == this.notificationManager) {
			this.notificationManager = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceRegistry#addService(org.eclipse.emf.eef.runtime.services.EEFService)
	 */
	public synchronized void addService(PropertiesEditingProvider service) {
		service.setEMFServiceProvider(emfServiceProvider);
		service.setViewHandlerProviderRegistry(viewHandlerProviderRegistry);
		service.setNotificationManager(notificationManager);
		super.addService(service);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#getPropertiesEditingProvider(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage) {
		return getServiceForElement(ePackage);
	}
	
	

}
