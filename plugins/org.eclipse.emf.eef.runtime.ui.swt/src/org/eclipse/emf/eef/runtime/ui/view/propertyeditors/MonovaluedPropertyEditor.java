/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface MonovaluedPropertyEditor {

	/**
	 * Sets a value to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void setValue(Object value);
	
	/**
	 * Unsets the value of this {@link PropertyEditor}.
	 */
	void unsetValue();
	
}
