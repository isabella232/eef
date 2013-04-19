/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.NullEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.intent.NullEditingProlicyIntentFactory;
import org.eclipse.emf.eef.runtime.internal.policies.processors.NullEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class StandardPropertiesEditingPolicyProvider extends AbstractEEFService<PropertiesEditingContext> implements PropertiesEditingPolicyProvider, DefaultService {

	private EditingPolicyIntentFactoryProvider editingPolicyIntentFactoryProvider;
	private EditingPolicyProcessorProvider editingPolicyProcessorProvider;
	
	private PropertiesEditingPolicy nullEditingPolicy;

	/**
	 * @param editingPolicyIntentFactoryProvider the editingPolicyIntentFactoryProvider to set
	 */
	public void setEditingPolicyIntentFactoryProvider(EditingPolicyIntentFactoryProvider editingPolicyIntentFactoryProvider) {
		this.editingPolicyIntentFactoryProvider = editingPolicyIntentFactoryProvider;
	}

	/**
	 * @param editingPolicyProcessorProvider the editingPolicyProcessorProvider to set
	 */
	public void setEditingPolicyProcessorProvider(EditingPolicyProcessorProvider editingPolicyProcessorProvider) {
		this.editingPolicyProcessorProvider = editingPolicyProcessorProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
		EditingPolicyIntentFactory intentFactory = editingPolicyIntentFactoryProvider.getProcessingFactory(context);
		if (!(intentFactory instanceof NullEditingProlicyIntentFactory)) {
			EditingPolicyProcessor processor = editingPolicyProcessorProvider.getProcessor(context);
			if (!(processor instanceof NullEditingPolicyProcessor)) {
				return new EditingPolicyWithProcessor(intentFactory.createProcessing(context), processor);
			}
		}
		return getNullEditingPolicy();
	}

	private PropertiesEditingPolicy getNullEditingPolicy() {
		if (nullEditingPolicy == null) {
			nullEditingPolicy = new NullEditingPolicy();
		}
		return nullEditingPolicy;
	}

}
