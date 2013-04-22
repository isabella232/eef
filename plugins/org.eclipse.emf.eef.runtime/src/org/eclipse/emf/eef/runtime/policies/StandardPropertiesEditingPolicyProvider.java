/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.NullEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.processors.NullEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.internal.policies.request.NullEditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class StandardPropertiesEditingPolicyProvider extends AbstractEEFService<PropertiesEditingContext> implements PropertiesEditingPolicyProvider, DefaultService {

	private EditingPolicyRequestFactoryProvider editingPolicyRequestFactoryProvider;
	private EditingPolicyProcessorProvider editingPolicyProcessorProvider;
	
	private PropertiesEditingPolicy nullEditingPolicy;

	/**
	 * @param editingPolicyRequestFactoryProvider the editingPolicyRequestFactoryProvider to set
	 */
	public void setEditingPolicyRequestFactoryProvider(EditingPolicyRequestFactoryProvider editingPolicyRequestFactoryProvider) {
		this.editingPolicyRequestFactoryProvider = editingPolicyRequestFactoryProvider;
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
		EditingPolicyRequestFactory requestFactory = editingPolicyRequestFactoryProvider.getProcessingFactory(context);
		if (!(requestFactory instanceof NullEditingPolicyRequestFactory)) {
			EditingPolicyProcessor processor = editingPolicyProcessorProvider.getProcessor(context);
			if (!(processor instanceof NullEditingPolicyProcessor)) {
				return new EditingPolicyWithProcessor(requestFactory.createProcessing(context), processor);
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
