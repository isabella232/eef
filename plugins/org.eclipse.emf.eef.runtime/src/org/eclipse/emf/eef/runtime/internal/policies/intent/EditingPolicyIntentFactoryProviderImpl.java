/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.intent;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactoryProvider;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingPolicyIntentFactoryProviderImpl extends EEFServiceProviderImpl<PropertiesEditingContext, EditingPolicyIntentFactory> implements EditingPolicyIntentFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactoryProvider#getProcessingFactory(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyIntentFactory getProcessingFactory(PropertiesEditingContext editingContext) {
		return getService(editingContext);
	}

}
