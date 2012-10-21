///**
// * 
// */
//package org.eclipse.emf.eef.runtime.internal.services.editingProvider;
//
//import org.eclipse.emf.ecore.EPackage;
//import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
//import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
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
//	private EEFServiceRegistry serviceRegistry;
//	private ModelChangesNotificationManager notificationManager;
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl)
//	 */
//	public void setComponentRegistry(EEFServiceRegistry serviceRegistry) {
//		this.componentRegistry = serviceRegistry;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderRegistry#unsetComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl)
//	 */
//	public void unsetComponentRegistry(EEFServiceRegistry serviceRegistry) {
//		if (serviceRegistry == this.componentRegistry) {
//			serviceRegistry = null;
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
//		service.setComponentRegistry(serviceRegistry);
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
