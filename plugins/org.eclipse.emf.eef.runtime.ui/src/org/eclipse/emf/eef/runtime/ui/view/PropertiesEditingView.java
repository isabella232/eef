/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingView extends PropertiesEditingListener {

	/**
	 * @return the {@link PropertiesEditingComponent} managing the view.
	 */
	PropertiesEditingComponent getEditingComponent();
	
	/**
	 * Returns the {@link PropertyEditor} managing the given editor element.
	 * @param editor element to process.
	 * @return the {@link PropertyEditor} managing the given editor element if exists, <code>null</code> otherwise.
	 */
	PropertyEditor getPropertyEditor(ViewElement editor);
	
	/**
	 * @return the {@link ViewService} for the view.
	 */
	ViewService getViewService();
	
	/**
	 * @return the {@link ViewSettings} for the view.
	 */
	ViewSettings getViewSettings();
	
	/**
	 * @return the {@link Composite} containing view contents.
	 */
	Composite getContents();
	
	/**
	 * Initializes the view.
	 */
	void init();
		
	/**
	 * Dispose the view.
	 */
	void dispose();

	/**
	 * Sets the {@link PropertyEditorProviderRegistry} to use for {@link PropertyEditor}s creation.
	 * @param propertyEditorProviderRegistry {@link PropertyEditorProviderRegistry} to use to build {@link PropertiesEditingView} contents.
	 */
	void setPropertyEditorProviderRegistry(PropertyEditorProviderRegistry propertyEditorProviderRegistry);

	/**
	 * Sets the value to the given field.
	 * @param field field to modify.
	 * @param value new value.
	 */
	void setValue(Object field, Object value);

	/**
	 * Unsets the value of the given field.
	 * @param field field to unset.
	 */
	void unsetValue(Object field);
	
	/**
	 * Add the value to given field.
	 * @param field field to modify.
	 * @param value new value.
	 */
	void addValue(Object field, Object value);
	
	/**
	 * Add all the values to given field.
	 * @param field field to modify.
	 * @param values new values.
	 */
	void addAllValues(Object field, Collection<?> values);
	
	/**
	 * Removes the value to given field.
	 * @param field field to modify.
	 * @param value value to remove.
	 */
	void removeValue(Object field, Object value);
	
	/**
	 * Removes all the values to given field.
	 * @param field field to modify.
	 * @param value values to remove.
	 */
	void removeAllValues(Object field, Collection<?> values);
	
	/**
	 * Moves the value at the given index to given field.
	 * @param field field to modify.
	 * @param value value to remove.
	 * @param newIndex new value index.
	 */
	void moveValue(Object field, Object value, int newIndex);

}
