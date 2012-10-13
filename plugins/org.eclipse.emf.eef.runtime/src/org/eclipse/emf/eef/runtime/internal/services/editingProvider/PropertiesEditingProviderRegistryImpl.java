///**
// * 
// */
//package org.eclipse.emf.eef.runtime.internal.services.editingProvider;
//
//import org.eclipse.emf.ecore.EPackage;
//import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
//import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
//import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProvider;
//import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry;
//import org.eclipse.emf.eef.runtime.services.impl.EEFServiceSimpleRegistry;
//
///**
// * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
// *
// */
//public class PropertiesEditingProviderRegistryImpl extends EEFServiceSimpleRegistry<EPackage, PropertiesEditingProvider> implements PropertiesEditingProviderRegistry {
//
//	private EEFComponentRegistry componentRegistry;
//	private ModelChangesNotificationManager notificationManager;
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
//	 */
//	public void setComponentRegistry(EEFComponentRegistry componentRegistry) {
//		this.componentRegistry = componentRegistry;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#unsetComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
//	 */
//	public void unsetComponentRegistry(EEFComponentRegistry componentRegistry) {
//		if (componentRegistry == this.componentRegistry) {
//			componentRegistry = null;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#setModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
//	 */
//	public void setModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
//		this.notificationManager = notificationManager;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#unsetModelChangesNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
//	 */
//	public void unsetModelChangesNotificationManager(ModelChangesNotificationManager notificationManager) {
//		if (notificationManager == this.notificationManager) {
//			this.notificationManager = null;
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.impl.EEFServiceSimpleRegistry#addService(org.eclipse.emf.eef.runtime.services.EEFService)
//	 */
//	public synchronized void addService(PropertiesEditingProvider service) {
//		service.setComponentRegistry(componentRegistry);
//		service.setNotificationManager(notificationManager);
//		super.addService(service);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#getPropertiesEditingProvider(org.eclipse.emf.ecore.EPackage)
//	 */
//	public PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage) {
//		return getServiceForElement(ePackage);
//	}
//	
//	
//
//}
