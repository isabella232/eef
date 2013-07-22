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
import org.eclipse.emf.eef.runtime.services.EEFService;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditingPolicyProcessor extends EEFService<PropertiesEditingContext> {

	/**
	 * Processes a {@link EditingPolicyRequest}.
	 * @param editingContext the {@link PropertiesEditingContext} where to perform the editing.
	 * @param behavior behavior to execute.
	 */
	void process(PropertiesEditingContext editingContext, EditingPolicyRequest behavior);
	
}
