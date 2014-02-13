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
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.util.EEFModifierInvocationParameters;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFModifierInvocationParametersImpl extends EEFInvocationParametersImpl implements EEFModifierInvocationParameters {

	private Object value;
	
	/**
	 * @param editingContext
	 * @param value
	 */
	public EEFModifierInvocationParametersImpl(PropertiesEditingContext editingContext, Object value) {
		super(editingContext);
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EEFModifierInvocationParameters#getValue()
	 */
	public Object getValue() {
		return value;
	}

}
