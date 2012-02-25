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
import org.eclipse.emf.eef.runtime.notify.EditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingComponent extends Adapter, EditingListener {

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
	 * @param listener the {@link EditingListener} to add.
	 */
	void addEditingListener(EditingListener listener);
	
	/**
	 * Remove a listener to this component.
	 * @param listener the {@link EditingListener} to remove.
	 */
	void removeEditingListener(EditingListener listener);
	
	/**
	 * Validate the element edited by the current component.
	 * @return a result {@link Diagnostic} for this validation. 
	 */
	Diagnostic validate();
}
