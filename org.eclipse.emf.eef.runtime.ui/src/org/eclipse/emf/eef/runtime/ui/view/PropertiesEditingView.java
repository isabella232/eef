/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingView {

	/**
	 * @return the {@link PropertiesEditingComponent} managing the view.
	 */
	PropertiesEditingComponent getEditingComponent();
	
	/**
	 * @return the {@link ViewHelper} for the view.
	 */
	ViewHelper getViewHelper();

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
	 * Set the PropertyEditor Provider.
	 * @param propertyEditorProvider {@link PropertyEditorProvider} to use to build {@link PropertiesEditingView} contents.
	 */
	void setPropertyEditorProvider(PropertyEditorProvider propertyEditorProvider);

	/**
	 * Sets the value to the given field.
	 * @param field field to modify.
	 * @param value new value
	 */
	void setValue(Object field, Object value);

	/**
	 * Unsets the value of the given field.
	 * @param field field to unset.
	 */
	void unsetValue(Object field);
	
}
