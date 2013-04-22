/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.request;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactoryProvider;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingPolicyRequestFactoryProviderImpl extends EEFServiceProviderImpl<PropertiesEditingContext, EditingPolicyRequestFactory> implements EditingPolicyRequestFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactoryProvider#getProcessingFactory(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyRequestFactory getProcessingFactory(PropertiesEditingContext editingContext) {
		return getService(editingContext);
	}

}
