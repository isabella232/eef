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
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequest;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory;
import org.eclipse.emf.eef.runtime.services.DefaultService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class NullEditingPolicyRequestFactory implements EditingPolicyRequestFactory, DefaultService {

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
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyRequestFactory#createProcessing(org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	public EditingPolicyRequest createProcessing(SemanticPropertiesEditingContext editingContext) {
		return null;
	}

}
