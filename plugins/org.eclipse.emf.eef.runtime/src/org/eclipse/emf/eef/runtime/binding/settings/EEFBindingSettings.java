/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding.settings;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelEnvironment;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface EEFBindingSettings extends EEFService<EPackage> {
	
	/**
	 * @return the EMFServiceProvider in use in the current editing provider.
	 */
	EMFServiceProvider getEMFServiceProvider();

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
	 * @return a {@link ViewHandlerFactory} able to provide {@link ViewHandler} for the given view.
	 * @param view view to process.
	 * @return a applicable {@link ViewHandlerFactory} if exists, <code>null</code> otherwise.
	 */
	ViewHandlerFactory getViewHandlerFactory(Object view);

	/**
	 * Method allowing users to filter the {@link EEFLockPolicy}s applied on a {@link PropertiesEditingComponent}. 
	 * @param lockPolicy {@link EEFLockPolicy} to process.
	 * @return <code>true</code> if the given policy must be applied on the edited elements.
	 */
	boolean enableLockPolicy(EEFLockPolicy lockPolicy);

}
