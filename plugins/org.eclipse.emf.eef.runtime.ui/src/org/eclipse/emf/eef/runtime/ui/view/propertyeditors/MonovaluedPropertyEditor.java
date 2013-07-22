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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface MonovaluedPropertyEditor {

	/**
	 * Sets a value to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void setValue(Object value);
	
	/**
	 * Unsets the value of this {@link PropertyEditor}.
	 */
	void unsetValue();
	
}
