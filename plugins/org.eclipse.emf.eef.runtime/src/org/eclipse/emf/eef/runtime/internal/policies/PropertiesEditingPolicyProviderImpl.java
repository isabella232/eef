/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.processors.NullEditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.internal.policies.request.NullEditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessorProvider;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactoryProvider;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class PropertiesEditingPolicyProviderImpl implements PropertiesEditingPolicyProvider, DefaultService {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditingPolicyRequestFactoryProvider editingPolicyRequestFactoryProvider;
	private EditingPolicyProcessorProvider editingPolicyProcessorProvider;

	private PropertiesEditingPolicy nullEditingPolicy;

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public final void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param editingPolicyRequestFactoryProvider
	 *            the editingPolicyRequestFactoryProvider to set
	 */
	public void setEditingPolicyRequestFactoryProvider(EditingPolicyRequestFactoryProvider editingPolicyRequestFactoryProvider) {
		this.editingPolicyRequestFactoryProvider = editingPolicyRequestFactoryProvider;
	}

	/**
	 * @param editingPolicyProcessorProvider
	 *            the editingPolicyProcessorProvider to set
	 */
	public void setEditingPolicyProcessorProvider(EditingPolicyProcessorProvider editingPolicyProcessorProvider) {
		this.editingPolicyProcessorProvider = editingPolicyProcessorProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(SemanticPropertiesEditingContext context) {
		EditingPolicyRequestFactory requestFactory = editingPolicyRequestFactoryProvider.getProcessingFactory(context);
		if (!(requestFactory instanceof NullEditingPolicyRequestFactory)) {
			EditingPolicyProcessor processor = editingPolicyProcessorProvider.getProcessor(context);
			if (!(processor instanceof NullEditingPolicyProcessor)) {
				EditingPolicyRequest createProcessing = requestFactory.createProcessing(context);
				return new EditingPolicyWithProcessor(eefEditingServiceProvider, createProcessing, processor);
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
