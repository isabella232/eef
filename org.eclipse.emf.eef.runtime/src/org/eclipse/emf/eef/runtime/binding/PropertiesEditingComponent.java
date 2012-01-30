/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandler;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingComponent extends Adapter {

	/**
	 * Returns the editing context of the component.
	 * @return the current {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext getEditingContext();
	
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
	 * @return the component {@link ViewChangeNotifier}.
	 */
	public ViewChangeNotifier getViewChangeNotifier();
	
	
	/**
	 * Notify the component that a widget of a managed view has changed.
	 * @param editingEvent {@link PropertiesEditingEvent} described the view change.
	 */
	void fireViewChange(PropertiesEditingEvent editingEvent);

}
