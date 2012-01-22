/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.model.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;
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
	 * @return a {@link ViewHandler} able to manage a view for the edited {@link EObject}.
	 */
	ViewHandler getViewHandler();
	
}
