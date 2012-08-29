/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingContextFactory {

	/**
	 * Creates a standard {@link PropertiesEditingContext} with an {@link AdapterFactory} and an {@link EObject}.
	 * @param adapterFactory {@link AdapterFactory} for the context.
	 * @param eObject {@link EObject} for the context.
	 * @return the created {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext createPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject);

	/**
	 * Creates a standard {@link PropertiesEditingContext} with an {@link AdapterFactoryEditingDomain} and an {@link EObject}.
	 * @param domain {@link AdapterFactoryEditingDomain} for the context.
	 * @param eObject {@link EObject} for the context.
	 * @return the created {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext createPropertiesEditingContext(AdapterFactoryEditingDomain domain, EObject eObject);
	
	/**
	 * Creates a standard {@link PropertiesEditingContext} with an {@link EditingDomain}, an an {@link AdapterFactory} and an {@link EObject}.
	 * @param domain {@link AdapterFactoryEditingDomain} for the context.
	 * @param adapterFactory {@link AdapterFactory} for the context.
	 * @param eObject {@link EObject} for the context.
	 * @return the created {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext createPropertiesEditingContext(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject);

	/**
	 * Defines the {@link EMFServiceProvider} to use in the {@link PropertiesEditingContext} created by this factory
	 * @param emfServiceProvider {@link EMFServiceProvider} to use.
	 */
	void setEMFServiceProvider(EMFServiceProvider emfServiceProvider);

	/**
	 * Unsets the {@link EMFServiceProvider} to use in the {@link PropertiesEditingContext} created by this factory
	 * @param emfServiceProvider {@link EMFServiceProvider} to use.
	 */
	void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider);

	/**
	 * Defines the {@link PropertiesEditingProviderRegistry} to use in the {@link PropertiesEditingContext} created by this factory
	 * @param propertiesEditingProviderRegistry {@link PropertiesEditingProviderRegistry} to use.
	 * TODO: Useless injection, the PropertiesEditingContextFactory can inject this for me!
	 */
	void setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry);
	
	/**
	 * Unsets the {@link PropertiesEditingProviderRegistry} to use in the {@link PropertiesEditingContext} created by this factory
	 * @param propertiesEditingProviderRegistry {@link PropertiesEditingProviderRegistry} to use.
	 */
	void unsetPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry);

	/**
	 * Defines the {@link ModelChangesNotificationManager} to uses in the current {@link PropertiesEditingContextFactory}.
	 * @param notificationManager the {@link ModelChangesNotificationManager} to set.
	 */
	void setNotificationManager(ModelChangesNotificationManager notificationManager);

	/**
	 * Unsets the {@link ModelChangesNotificationManager} to uses in the current {@link PropertiesEditingContextFactory}.
	 * @param notificationManager the {@link ModelChangesNotificationManager} to unset.
	 */
	void unsetNotificationManager(ModelChangesNotificationManager notificationManager);

}
