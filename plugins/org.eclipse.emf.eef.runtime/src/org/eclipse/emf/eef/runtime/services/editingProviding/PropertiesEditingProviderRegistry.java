/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.editingProviding;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingProviderRegistry {
	
	/**
	 * @param ePackage
	 * @return
	 */
	PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage);
	
	/**
	 * Defines the {@link EEFComponentRegistryImpl} to use in the current {@link EEFComponentRegistryImpl}.
	 * @param componentRegistry {@link EEFComponentRegistryImpl} to use.
	 */
	void setComponentRegistry(EEFComponentRegistry componentRegistry);
	
	/**
	 * Unsets the {@link EEFComponentRegistryImpl} used in the current {@link EEFComponentRegistryImpl}.
	 * @param componentRegistry {@link EEFComponentRegistryImpl} to unset.
	 */
	void unsetComponentRegistry(EEFComponentRegistry componentRegistry);
	
	/**
	 * Defines the {@link ModelChangesNotificationManager} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param notificationManager the {@link ModelChangesNotificationManager} to set.
	 */
	void setModelChangesNotificationManager(ModelChangesNotificationManager notificationManager);

	/**
	 * Unsets the {@link ModelChangesNotificationManager} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param notificationManager the {@link ModelChangesNotificationManager} to unset.
	 */
	void unsetModelChangesNotificationManager(ModelChangesNotificationManager notificationManager);

}
