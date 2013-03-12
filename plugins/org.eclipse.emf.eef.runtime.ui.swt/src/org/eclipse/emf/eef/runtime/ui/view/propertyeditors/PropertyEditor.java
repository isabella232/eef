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
	void init(EStructuralFeature feature);
	
	
	/**
	 * @return the {@link PropertyEditorViewer} responsible for building the UI part 
	 * of this editor.
	 */
	PropertyEditorViewer<?> getPropertyEditorViewer();
}
