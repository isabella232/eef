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
