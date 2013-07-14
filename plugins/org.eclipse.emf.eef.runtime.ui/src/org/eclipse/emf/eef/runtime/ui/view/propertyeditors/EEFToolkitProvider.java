/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFToolkitProvider {
	
	/**
	 * @param editorContext The {@link PropertyEditorContext} to process.
	 * @return a {@link EEFToolkitImpl} able to process the given editor context.
	 */
	<T> EEFToolkit<T> getToolkit(PropertyEditorContext editorContext);
	
}
