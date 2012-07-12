/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingComponent extends Adapter, PropertiesEditingListener {

    /**
	 * Returns the editing context of the component.
	 * @return the component's {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext getEditingContext();
	
	/**
	 * @return the {@link EClassBinding} describing the target {@link EObject} mapping.
	 */
	EClassBinding getBinding();
	
	/**
	 * Sets the editing context for this component.
	 * @param editingContext {@link PropertiesEditingContext} to set.
	 */
	void setEditingContext(PropertiesEditingContext editingContext);
	
	/**
	 * Returns the {@link ViewHandler} able to manage the given view.
	 * @param view the view to manage.
	 * @return a list of {@link ViewHandler} editing the model element.
	 */
	ViewHandler<?> getViewHandler(Object view);

	/**
	 * Returns all {@link ViewHandler}s able to manage the views for the edited model element.
	 * @return a list of {@link ViewHandler} editing the model element.
	 */
	List<ViewHandler<?>> getViewHandlers();

	/**
	 * @return the {@link ViewChangeNotifier} component.
	 */
	ViewChangeNotifier getViewChangeNotifier();
	
	/**
	 * Specifies that a feature value has changed. The event process must be delayed.
	 * 
	 * @param event information about this change.
	 */
	void delayedFirePropertiesChanged(PropertiesEditingEvent event);
	
	/**
	 * Add a listener to this component.
	 * @param listener the {@link PropertiesEditingListener} to add.
	 */
	void addEditingListener(PropertiesEditingListener listener);
	
	/**
	 * Remove a listener to this component.
	 * @param listener the {@link PropertiesEditingListener} to remove.
	 */
	void removeEditingListener(PropertiesEditingListener listener);
	
	/**
	 * Validate the element edited by the current component.
	 * @return a result {@link Diagnostic} for this validation. 
	 */
	Diagnostic validate();
	
	/**
	 * Dispose this component. Main topic is to remove this adapter from its host.
	 */
	void dispose();
}
