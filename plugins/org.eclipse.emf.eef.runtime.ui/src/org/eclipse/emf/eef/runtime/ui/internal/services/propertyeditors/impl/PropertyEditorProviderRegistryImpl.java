/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.internal.services.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry;
import org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyEditorProviderRegistryImpl extends EEFServiceRegistry<PropertyEditorContext, PropertyEditorProvider> implements PropertyEditorProviderRegistry {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProviderRegistry#getPropertyEditorProvider(org.eclipse.emf.eef.runtime.ui.services.propertyeditors.PropertyEditorProvider.PropertyEditorContext)
	 */
	public PropertyEditorProvider getPropertyEditorProvider(PropertyEditorContext editorContext) {
		return (PropertyEditorProvider) getServiceForElement(editorContext);
	}
	
	

}
