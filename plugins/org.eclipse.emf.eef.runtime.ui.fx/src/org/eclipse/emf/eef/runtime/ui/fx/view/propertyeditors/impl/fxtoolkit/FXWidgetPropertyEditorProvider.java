/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FXWidgetPropertyEditorProvider<T> extends WidgetPropertyEditorProvider<T> {

	/**
	 * @return the count of columns used by the widgets.
	 */
	int columnsConsumption();
	
}
