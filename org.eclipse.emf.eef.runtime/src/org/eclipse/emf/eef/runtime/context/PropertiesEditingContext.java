/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingContext {
	
	/**
	 * @return the {@link PropertiesEditingModel} of the editing context.
	 */
	PropertiesEditingModel getEditingModel();

	/**
	 * Sets the {@link PropertiesEditingModel} to use in the editing context.
	 * @param editingModel {@link PropertiesEditingModel} to set.
	 */
	void setEditingModel(PropertiesEditingModel editingModel);

	/**
	 * @return the {@link ViewHandlerProvider} of the editing context.
	 */
	ViewHandlerProvider getViewHandlerProvider();
	
	/**
	 * Sets the {@link ViewHandlerProvider} to use in the editing context.
	 * @param viewHandlerProvider {@link ViewHandlerProvider} to set.
	 */
	void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider);
	
	/**
	 * Returns a {@link PropertiesEditingComponent} binding the edited model element.
	 * @return a {@link PropertiesEditingComponent} binded on the edited model element.
	 */
	PropertiesEditingComponent getComponent();

	/**
	 * Set a value to the given feature of the given {@link EObject}.
	 * @param eObject {@link EObject} to edit.
	 * @param feature {@link EStructuralFeature} to modify.
	 * @param value the value of the feature.
	 */
	void performSet(EObject eObject, EStructuralFeature feature, Object value);

}
