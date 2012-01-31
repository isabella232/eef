/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;

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
	
}
