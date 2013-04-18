/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.processors;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessorProvider;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EditingPolicyProcessorProviderImpl extends EEFServiceProviderImpl<PropertiesEditingContext, EditingPolicyProcessor> implements EditingPolicyProcessorProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessorProvider#getProcessor(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyProcessor getProcessor(PropertiesEditingContext editingContext) {
		return getService(editingContext);
	}

}
