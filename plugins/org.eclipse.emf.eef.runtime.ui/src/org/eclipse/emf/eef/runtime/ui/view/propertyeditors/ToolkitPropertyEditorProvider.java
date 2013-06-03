/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ToolkitPropertyEditorProvider {
	
	/**
	 * @param editorContext The {@link PropertyEditorContext} to process.
	 * @return a {@link ToolkitPropertyEditorImpl} able to process the given editor context.
	 */
	<T> ToolkitPropertyEditor<T> getToolkit(PropertyEditorContext editorContext);
	
}
