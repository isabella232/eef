/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FormPropertiesEditingView extends PropertiesEditingView {

	/**
	 * Create the contents of the view in the given composite using the given toolkit.
	 * @param toolkit {@link FormToolkit} to use.
	 * @param composite owning {@link Composite}.
	 */
	void createContents(FormToolkit toolkit, Composite composite);
}
