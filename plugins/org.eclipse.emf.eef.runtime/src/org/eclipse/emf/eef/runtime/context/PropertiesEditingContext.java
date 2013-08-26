/**
 * 
 */
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingContext {
			
	/**
	 * @return the {@link EMFServiceProvider} of the current {@link PropertiesEditingContext}.
	 */
	EMFServiceProvider getEMFServiceProvider();
	
	/**
	 * @return the {@link BindingHandlerProvider} to use in the current context.
	 */
	BindingHandlerProvider getBindingManagerProvider();
	
	/**
	 * @return the {@link EditingContextFactoryProvider} to use in the current context.
	 */
	EditingContextFactoryProvider getContextFactoryProvider();
	
	/**
	 * @return the {@link ViewHandlerProvider} to use in the current context.
	 */
	ViewHandlerProvider getViewHandlerProvider();
	
	/**
	 * @return the parent context of the current {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext getParentContext();

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
	 * @param editingComponent the {@link PropertiesEditingComponent} to dispose.
	 */
	void disposeComponent(PropertiesEditingComponent editingComponent);
	
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
