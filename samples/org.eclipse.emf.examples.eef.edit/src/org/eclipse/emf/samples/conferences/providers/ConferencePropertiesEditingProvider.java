/**
 * 
 */
package org.eclipse.emf.samples.conferences.providers;

import org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferencePropertiesEditingProvider extends PropertiesEditingProviderImpl {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editingProviding.PropertiesEditingProviderImpl#getEditingModelPath()
	 */
	@Override
	protected String getEditingModelPath() {
		return "platform:/plugin/org.eclipse.emf.examples.eef.edit/models/conference.fx.editingmodel";
	}

	


}