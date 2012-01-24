/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

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
	 * Sets the editing context for this component.
	 * @param editingContext {@link PropertiesEditingContext} to set.
	 */
	void setEditingContext(PropertiesEditingContext editingContext);

	/**
	 * Returns a {@link ViewHandler} able to manage a view for the edited model element.
	 * @return a {@link ViewHandler} editing the model element.
	 */
	public abstract ViewHandler<?> getViewHandler();

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
