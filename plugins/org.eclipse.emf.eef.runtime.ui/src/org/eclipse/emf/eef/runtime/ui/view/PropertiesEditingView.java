/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.EditingListener;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingView extends EditingListener {

	/**
	 * @return the {@link PropertiesEditingComponent} managing the view.
	 */
	PropertiesEditingComponent getEditingComponent();
	
	/**
	 * @return the {@link ViewHelper} for the view.
	 */
	ViewHelper getViewHelper();
	
	/**
	 * @return the {@link ViewSettings} for the view.
	 */
	ViewSettings getViewSettings();

	/**
	 * Create the contents of the view in the given composite.
	 * @param composite owning {@link Composite}.
	 */
	void createContents(Composite composite);
	
	/**
	 * @return the {@link Composite} containing view contents.
	 */
	Composite getContents();
	
	/**
	 * Initialize the view.
	 */
	void init();

	/**
	 * Sets the PropertyEditor Provider.
	 * @param propertyEditorProvider {@link PropertyEditorProvider} to use to build {@link PropertiesEditingView} contents.
	 */
	void setPropertyEditorProvider(PropertyEditorProvider propertyEditorProvider);

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
