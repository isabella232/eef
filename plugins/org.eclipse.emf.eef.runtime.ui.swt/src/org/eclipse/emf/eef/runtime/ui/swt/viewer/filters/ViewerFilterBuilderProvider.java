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
package org.eclipse.emf.eef.runtime.ui.swt.viewer.filters;

import org.eclipse.emf.eef.runtime.query.Filter;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewerFilterBuilderProvider {

	/**
	 * Returns a {@link ViewerFilterBuilder} able to wrap the given filter.
	 * @param filter the filter to wrap.
	 * @return a adapted filter builder.
	 */
	ViewerFilterBuilder getFilterBuilder(Filter filter);
	
}
