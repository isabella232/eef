/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface WidgetPropertyEditorProvider extends ModelPropertyEditorProvider<Widget> {

	/**
	 * Defines if the current provider is able to handle the given {@link PropertyEditorContext}.
	 * @param editorContext context to process.
	 * @return <code>true</code> if the current provider can handle this context.
	 */
	boolean serviceFor(PropertyEditorContext editorContext);

}
