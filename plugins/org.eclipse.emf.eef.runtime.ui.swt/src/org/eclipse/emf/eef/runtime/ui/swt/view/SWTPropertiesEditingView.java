/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view;

import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface SWTPropertiesEditingView {

	/**
	 * Creates the contents of the view in the given composite.
	 * @param composite owning {@link Composite}.
	 */
	void createContents(Composite composite);

}
