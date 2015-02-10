/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.samples.conference.customizers;

import org.eclipse.emf.eef.runtime.binding.AbstractPropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.binding.EEFModifierCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;
import org.eclipse.emf.samples.conference.Conference;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class DemoCustomizer extends AbstractPropertyBindingCustomizer {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.AbstractPropertyBindingCustomizer#getGetter()
	 */
	@Override
	public EEFModifierCustomizer<Object> getGetter() {
		return new EEFModifierCustomizer<Object>() {

			@Override
			public Object execute(EEFInvocationParameters parameters) {
				return ((Conference)parameters.getEditedObject()).getName().toUpperCase();
			}
		};
	}

}
