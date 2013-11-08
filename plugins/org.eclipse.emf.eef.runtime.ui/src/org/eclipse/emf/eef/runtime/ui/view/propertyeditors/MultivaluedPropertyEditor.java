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

import java.util.Collection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface MultivaluedPropertyEditor {

	/**
	 * Add a value to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void addValue(Object value);

	/**
	 * Add all values to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void addAllValues(Collection<?> values);

	/**
	 * Removes a value to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void removeValue(Object value);

	/**
	 * Removes all values to this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void removeAllValues(Collection<?> values);

	/**
	 * Moves a value at the given index in this {@link PropertyEditor}.
	 * @param value the new value.
	 */
	void moveValue(Object value, int newIndex);

}
