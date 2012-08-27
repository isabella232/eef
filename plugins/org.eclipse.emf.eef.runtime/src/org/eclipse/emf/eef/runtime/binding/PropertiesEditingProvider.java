/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProviderRegistry;
import org.osgi.framework.BundleContext;

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
	 * Defines the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingProvider}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to set
	 */
	void setEMFServiceProvider(EMFServiceProvider emfServiceProvider);
 
	/**
	 * Defines the {@link ViewHandlerProviderRegistry} to use in the current {@link PropertiesEditingProvider}
	 * @param viewHandlerProviderRegistry the {@link ViewHandlerProviderRegistry} to set.
	 */
	void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry);

	/**
	 * Defines the {@link BundleContext} of this component.
	 * @param bundleContext the {@link BundleContext} of this component.
	 */
	void setBundleContext(BundleContext bundleContext);
}
