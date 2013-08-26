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

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.query.Filter;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewerFilterBuilder extends EEFService<Filter> {

	/**
	 * Build a {@link ViewerFilter} according to the given {@link Filter}.
	 * @param editingContext the current {@link PropertiesEditingContext}.
	 * @param editingView the current {@link PropertiesEditingView}.
	 * @param filter the {@link Filter} to wrap.
	 * @return a {@link ViewFilter}.
	 */
	ViewerFilter buildFilter(PropertiesEditingContext editingContext, PropertiesEditingView<Composite> editingView, Filter filter);
}
