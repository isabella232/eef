/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditor {

	/**
	 * Build the PropertyEditor in the given composite.
	 * @param parent {@link Composite} where to build the PropertyEditor.
	 */
	public void build(Composite parent);
	
	/**
	 * Initialize the PropertyEditor for the given feature.
	 * @param feature {@link EStructuralFeature} managed by this {@link PropertyEditor}.
	 */
	public void init(EStructuralFeature feature);
}
