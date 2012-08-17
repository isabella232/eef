/**
 * 
 */
package org.eclipse.emf.samples.conferences.providers;

import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferencePropertiesEditingProvider extends PropertiesEditingProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingProvider#getEditingModelPath()
	 */
	@Override
	protected String getEditingModelPath() {
		return "platform:/plugin/org.eclipse.emf.examples.eef.edit/models/conference.editingmodel";
	}

	


}