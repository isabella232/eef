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

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class MonoPropertyBindingCustomizer extends AbstractPropertyBindingCustomizer {

	public static final int SETTER = 10;
	public static final int UNSETTER = 11;

	/**
	 * @return EEFModifierCustomizer for setter
	 */
	public EEFModifierCustomizer<Void> getSetter() {
		return new NullModifierCustomizer<Void>();
	}

	/**
	 * @return EEFModifierCustomizer for unsetter
	 */
	public EEFModifierCustomizer<Void> getUnsetter() {
		return new NullModifierCustomizer<Void>();
	}
}
