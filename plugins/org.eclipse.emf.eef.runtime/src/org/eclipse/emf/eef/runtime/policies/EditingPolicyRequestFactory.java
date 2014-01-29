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
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyRequestFactory extends EEFService<PropertiesEditingContext> {
	
	/**
	 * Creates a {@link EditingPolicyRequest} from a given {@link PropertiesEditingContext}
	 * @param editingContext the source {@link PropertiesEditingContext}. 
	 * @return the created {@link EditingPolicyRequest}.
	 */
	EditingPolicyRequest createProcessing(SemanticPropertiesEditingContext editingContext);

}
