/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface SWTPropertiesEditingView {

	/**
	 * Create the contents of the view in the given composite.
	 * @param composite owning {@link Composite}.
	 */
	void createContents(Composite composite);

}
