/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.internal.notify.ModelChangesNotifier;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl;
import org.osgi.framework.BundleContext;
import org.osgi.service.event.EventHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelChangesNotificationManager {

	/**
	 * Defines the {@link EEFServiceRegistryImpl} to use in the current NotificationManager.
	 * @param serviceRegistry the {@link EEFServiceRegistryImpl} to use.
	 */
	void setServiceRegistry(EEFServiceRegistry serviceRegistry);
	
	/**
	 * Unsets the {@link EEFServiceRegistryImpl} used in the current NotificationManager.
	 * @param serviceRegistry the {@link EEFServiceRegistryImpl} to unset.
	 */
	void unsetServiceRegistry(EEFServiceRegistry serviceRegistry);
	
	/**
	 * Registers the given {@link PropertiesEditingComponent} in the {@link BundleContext} as an {@link EventHandler}.
	 * @param editingComponent the {@link PropertiesEditingComponent} to register.
	 */
	void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent);
	
	/**
	 * Unregisters the given {@link PropertiesEditingComponent} in the {@link BundleContext}.
	 * @param editingComponent the {@link PropertiesEditingComponent} to unregister.
	 */
	void unregisterEditingComponent(PropertiesEditingComponent editingComponent);

	/**
	 * Registers an {@link ModelChangesNotifier} on the highest reachable notifier from the given {@link EObject}. 
	 * @param source {@link EObject} to process.
	 */
	void initModelChangesNotifierIfNeeded(EObject source);

}
