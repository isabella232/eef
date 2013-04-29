/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingContext {
	
	final String EOBJECT_PARAM = "EObject parameter"; 
	final String ADAPTERFACTORY_PARAM = "AdapterFactory parameter"; 
	final String PARENTCONTEXT_PARAM = "ParentContext parameter"; 
	
	/**
	 * @return the {@link EEFServiceRegistry} of the current {@link PropertiesEditingContext}.
	 */
	EEFServiceRegistry getServiceRegistry();
	
	/**
	 * Defines the {@link EEFServiceRegistry} of the current {@link PropertiesEditingContext}.
	 * @param serviceRegistry the {@link EEFServiceRegistryImpl}.
	 */
	void setServiceRegistry(EEFServiceRegistry serviceRegistry);
	
	/**
	 * @return the {@link EMFServiceProvider} of the current {@link PropertiesEditingContext}.
	 */
	EMFServiceProvider getEMFServiceProvider();
	
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
	 * @return the {@link AdapterFactory} of the context.
	 */
	AdapterFactory getAdapterFactory();
	
	/**
	 * @return the {@link ContextOptions} object of this context.
	 */
	ContextOptions getOptions();
	
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
