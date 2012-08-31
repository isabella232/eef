/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.services.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorProviderRegistry {

	
	/**
	 * Returns the {@link PropertyEditorProvider} able to handle the given {@link PropertyEditorContext}.
	 * @param editorContext {@link PropertyEditorContext} to process.
	 * @return a {@link PropertyEditorProvider} able to process the given {@link PropertyEditorContext}.
	 */
	PropertyEditorProvider getPropertyEditorProvider(PropertyEditorContext editorContext);
	
}
