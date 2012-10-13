/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.editingProviding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface PropertiesEditingProvider extends EEFService<EPackage> {

	/**
	 * Creates a new {@link PropertiesEditingComponent}.
	 * 
	 * @param target the {@link EObject} to handle.
	 * @return the created {@link PropertiesEditingComponent}.
	 */
	PropertiesEditingComponent createComponent(EObject target);
	
	/**
	 * Executes operations to be done to dispose the given {@link PropertiesEditingComponent}.
	 * @param component {@link PropertiesEditingComponent} to dispose.
	 */
	void disposeComponent(PropertiesEditingComponent component);

	/**
	 * @return the editingModelEnvironment of this provider.
	 */
	EditingModelEnvironment getEditingModelEnvironment();

	/**
	 * Returns the EditingModel describing the editing forms to edit the given object.
	 * @param eObject the {@link EObject} to edit.
	 * @return the {@link PropertiesEditingModel} to use for edit the given EObject.
	 */
	PropertiesEditingModel getEditingModel(EObject eObject);

	/**
	 * @return a {@link ViewHandlerProvider} able to provide {@link ViewHandler} for the given view.
	 * @param view view to process.
	 * @return a applicable {@link ViewHandlerProvider} if exists, <code>null</code> otherwise.
	 */
	ViewHandlerProvider getViewHandlerProvider(Object view);

	/**
	 * Defines the {@link EEFComponentRegistryImpl} to use in the current {@link PropertiesEditingProvider}.
	 * @param componentRegistry the {@link EEFComponentRegistryImpl} to use.
	 */
	void setComponentRegistry(EEFComponentRegistry componentRegistry);
	
	/**
	 * Defines the {@link ModelChangesNotificationManager} to use in the current {@link PropertiesEditingProviderRegistry}
	 * @param notificationManager {@link ModelChangesNotificationManager} to set.
	 */
	void setNotificationManager(ModelChangesNotificationManager notificationManager);

}
