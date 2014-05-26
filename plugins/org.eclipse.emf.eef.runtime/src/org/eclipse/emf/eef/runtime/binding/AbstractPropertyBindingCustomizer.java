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
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer;
import org.eclipse.emf.eef.runtime.util.EEFInvocationParameters;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class AbstractPropertyBindingCustomizer implements PropertyBindingCustomizer {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getGetter()
	 */
	public EEFModifierCustomizer<Object> getGetter() {
		return new NullModifierCustomizer<Object>();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getValuesProvider()
	 */
	public EEFModifierCustomizer<Object> getValuesProvider() {
		return new NullModifierCustomizer<Object>();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer#getEventFilter()
	 */
	public EEFModifierCustomizer<Boolean> getEventFilter() {
		return new NullModifierCustomizer<Boolean>() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.internal.binding.NullModifierCustomizer#execute(org.eclipse.emf.eef.runtime.util.EEFInvocationParameters)
			 */
			@Override
			public Boolean execute(EEFInvocationParameters parameters) {
				return false;
			}

		};
	}

}
