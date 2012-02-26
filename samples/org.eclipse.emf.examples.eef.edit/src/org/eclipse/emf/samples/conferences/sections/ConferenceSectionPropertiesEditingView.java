/**
 * 
 */
package org.eclipse.emf.samples.conferences.sections;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.view.section.SectionPropertiesEditingView;
import org.eclipse.emf.samples.conferences.providers.ConferencePropertiesEditingProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ConferenceSectionPropertiesEditingView extends SectionPropertiesEditingView {

	/**
	 * 
	 */
	public ConferenceSectionPropertiesEditingView() {
		super();
		((ComposedAdapterFactory)adapterFactory).addAdapterFactory(new ConferencePropertiesEditingProvider());
	}

	
	
}
