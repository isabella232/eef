/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface SWTPropertyEditor<VIEWER extends Viewer> extends PropertyEditorViewer<VIEWER> {

	/**
	 * Build the PropertyEditor in the given composite.
	 * @param parent {@link Composite} where to build the PropertyEditor.
	 */
	void build(Composite parent);
		
}
