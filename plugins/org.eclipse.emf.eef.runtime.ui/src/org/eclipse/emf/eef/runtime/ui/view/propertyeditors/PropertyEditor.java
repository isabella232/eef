/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditor {

	/**
	 * Initialize the PropertyEditor for the given feature.
	 * @param feature {@link EStructuralFeature} managed by this {@link PropertyEditor}.
	 */
	public void init(EStructuralFeature feature);
	
}
