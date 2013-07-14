/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelPropertyEditorFactory<T extends EObject,U> extends PropertyEditorFactory<U> {

	/**
	 * @return the model for this provider.
	 */
	T getModel();
	
	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);

}
