/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;

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
	 * Defines the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to use.
	 */
	void setEMFServiceProvider(EMFServiceProvider emfServiceProvider);

	/**
	 * Unsets the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to use.
	 */
	void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider);
	
	/**
	 * Defines the {@link ViewHandlerProviderRegistry} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param viewHandlerProviderRegistry the {@link ViewHandlerProviderRegistry} to use.
	 */
	void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry);
	
	/**
	 * Unsets the {@link ViewHandlerProviderRegistry} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param viewHandlerProviderRegistry the {@link ViewHandlerProviderRegistry} to unset.
	 */
	void unsetViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry);

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
