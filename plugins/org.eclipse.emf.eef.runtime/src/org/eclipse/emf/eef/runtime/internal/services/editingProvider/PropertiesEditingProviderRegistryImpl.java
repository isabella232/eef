/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.editingProvider;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceSimpleRegistry;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingProviderRegistryImpl extends EEFServiceSimpleRegistry<EPackage, PropertiesEditingProvider> implements PropertiesEditingProviderRegistry {

	private EMFServiceProvider emfServiceProvider;
	private ViewHandlerProviderRegistry viewHandlerProviderRegistry;
	private ModelChangesNotificationManager notificationManager;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#setEMFServiceProvider(EMFServiceRegistry)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#unsetEMFServiceRegistry(org.eclipse.emf.eef.runtime.internal.services.emf.EMFServiceRegistry)
	 */
	public void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		if (emfServiceProvider == this.emfServiceProvider) {
			this.emfServiceProvider = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#setViewHandlerProviderRegistry(ViewHandlerProviderRegistry)
	 */
	public void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		this.viewHandlerProviderRegistry = viewHandlerProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider#unsetViewHandlerProviderRegistry(org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry)
	 */
	public void unsetViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		if (viewHandlerProviderRegistry == this.viewHandlerProviderRegistry) {
			this.viewHandlerProviderRegistry = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#setModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void setModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#unsetModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void unsetModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
		if (notificationManager == this.notificationManager) {
			this.notificationManager = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceSimpleRegistry#addService(org.eclipse.emf.eef.runtime.services.EEFService)
	 */
	public synchronized void addService(PropertiesEditingProvider service) {
		service.setEMFServiceProvider(emfServiceProvider);
		service.setViewHandlerProviderRegistry(viewHandlerProviderRegistry);
		service.setNotificationManager(notificationManager);
		super.addService(service);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#getPropertiesEditingProvider(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage) {
		return getServiceForElement(ePackage);
	}
	
	

}
