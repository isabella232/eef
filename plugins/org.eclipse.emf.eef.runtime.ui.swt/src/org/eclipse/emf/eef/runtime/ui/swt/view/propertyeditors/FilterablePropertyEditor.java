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
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors;

import java.beans.PropertyEditor;
import java.util.Collection;

import org.eclipse.jface.viewers.ViewerFilter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FilterablePropertyEditor {

	/**
	 * Adds a {@link ViewerFilter} to the current {@link PropertyEditor}.
	 * @param filter the filter to add.
	 */
	void addFilter(ViewerFilter filter);
	
	/**
	 * Removes a {@link ViewerFilter} to the current {@link PropertyEditor}.
	 * @param filter the filter to remove.
	 */
	void removeFilter(ViewerFilter filter);
	
	/**
	 * @return the registered filters;
	 */
	Collection<ViewerFilter> getFilters();

}
