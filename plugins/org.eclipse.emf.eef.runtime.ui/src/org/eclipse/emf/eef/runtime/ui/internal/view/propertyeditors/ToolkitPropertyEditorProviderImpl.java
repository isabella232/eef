/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ToolkitPropertyEditorProviderImpl extends EEFServiceProviderImpl<PropertyEditorContext, ToolkitPropertyEditor<?>> implements ToolkitPropertyEditorProvider {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider#getToolkit(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 * WARNING: a unchecked cast is done in this method, client must ensure that the result of this method is affected to the good type of element!
	 */
	@SuppressWarnings("unchecked")
	public <T> ToolkitPropertyEditor<T> getToolkit(PropertyEditorContext editorContext) {
		return (ToolkitPropertyEditor<T>) getService(editorContext);
	}

}
