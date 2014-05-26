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

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface PropertyBindingCustomizer {

	/**
	 * Constants.
	 */
	final int GETTER = 0;
	final int VALUES_PROVIDER = 1;
	final int EVENT_FILTER = 2;

	/**
	 * @return EEFModifierCustomizer for getter
	 */
	EEFModifierCustomizer<Object> getGetter();

	/**
	 * @return EEFModifierCustomizer for choices of values
	 */
	EEFModifierCustomizer<Object> getValuesProvider();

	/**
	 * @return EEFModifierCustomizer for semantic event filter on notification
	 */
	EEFModifierCustomizer<Boolean> getEventFilter();

}
