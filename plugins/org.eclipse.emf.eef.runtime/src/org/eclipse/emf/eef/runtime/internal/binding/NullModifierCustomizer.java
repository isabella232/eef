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
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

/**
 * Null pattern for EEFModifierCustomizer.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class NullModifierCustomizer<T> implements EEFModifierCustomizer<T> {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
	 */
	public T execute(EEFInvocationParameters parameters) {
		return null;
	}

}
