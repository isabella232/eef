/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelPropertyEditorProvider<T extends EObject,U> extends PropertyEditorProvider<U> {
	
	

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
