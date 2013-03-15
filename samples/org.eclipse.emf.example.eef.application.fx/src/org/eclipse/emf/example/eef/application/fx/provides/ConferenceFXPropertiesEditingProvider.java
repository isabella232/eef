/**
 * 
 */
package org.eclipse.emf.example.eef.application.fx.provides;

import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferenceFXPropertiesEditingProvider extends PropertiesEditingProviderImpl {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl#getEditingModelPath()
	 */
	@Override
	protected String getEditingModelPath() {
		return "platform:/plugin/org.eclipse.emf.example.eef.application.fx/models/conference.fx.editingmodel";
	}

	


}