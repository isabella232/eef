/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;


import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorImpl;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface WidgetPropertyEditorProvider<T> extends ModelPropertyEditorProvider<Widget, T> {

	/**
	 * Defines if the current provider is able to handle the given {@link PropertyEditorContext}.
	 * @param editorContext context to process.
	 * @return <code>true</code> if the current provider can handle this context.
	 */
	boolean serviceFor(PropertyEditorContext editorContext);
	
	/**
	 * @param toolkit the {@link ToolkitPropertyEditorImpl} owning the current property editor
	 */
	void setToolkit(ToolkitPropertyEditorImpl<T> toolkit);

}
