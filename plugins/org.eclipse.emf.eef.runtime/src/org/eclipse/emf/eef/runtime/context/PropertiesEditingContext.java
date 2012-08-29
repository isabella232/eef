/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingContext {
	
	/**
	 * Defines the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingContext}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to use.
	 */
	void setEmfServiceProvider(EMFServiceProvider emfServiceProvider);
	
	/**
	 * Defines the {@link PropertiesEditingProviderRegistry} to use in the current {@link PropertiesEditingContext}.
	 * @param propertiesEditingProviderRegistry the {@link PropertiesEditingProviderRegistry} to use.
	 */
	void setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry);

	/**
	 * Defines the {@link ModelChangesNotificationManager} to uses in the current {@link PropertiesEditingContext}
	 * @param notificationManager the {@link ModelChangesNotificationManager} to set.
	 */
	void setNotificationManager(ModelChangesNotificationManager notificationManager);

	/**
	 * Returns a {@link PropertiesEditingComponent} binding the edited model element.
	 * @return a {@link PropertiesEditingComponent} binded on the edited model element.
	 */
	PropertiesEditingComponent getEditingComponent();

	/**
	 * Return the {@link PropertiesEditingPolicy} to perform for the given {@link PropertiesEditingContext}.
	 * @param context editing context.
	 * @return {@link PropertiesEditingPolicy} to perform.
	 */
	PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context);

	/**
	 * @return the {@link AdapterFactory} of the context.
	 */
	AdapterFactory getAdapterFactory();
	
	/**
	 * @return the {@link ContextOptions} object of this context.
	 */
	ContextOptions getOptions();
	
	/**
	 * @return the {@link EMFService} used by the context for EMF purpose.
	 */
	EMFService getEMFService();
	
	/**
	 * stop the recording of editing operations in this context.
	 */
	void stopEditing();

	/**
	 * Cancel all the editing operations in this context.
	 */
	void cancelEditing();

	/**
	 * Undo all the editing operations in this context.
	 */
	void undoEditing();

	/**
	 * Dispose the current context. 
	 */
	void dispose();

}
