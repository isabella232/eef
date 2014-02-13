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
package org.eclipse.emf.eef.runtime.ui.swt.internal.viewer.filters;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.query.Filter;
import org.eclipse.emf.eef.runtime.query.JavaBody;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilder;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.util.EEFInvokerProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewerFilterBuilderImpl implements ViewerFilterBuilder, DefaultService {

	private EEFInvokerProvider eefInvokerProvider;
	
	private ViewerFilter nullViewerFilter;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Filter element) {
		return true;
	}
	
	/**
	 * @param eefInvokerProvider the eefInvokerProvider to set
	 */
	public final void setEEFInvokerProvider(EEFInvokerProvider eefInvokerProvider) {
		this.eefInvokerProvider = eefInvokerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.viewer.filters.ViewerFilterBuilder#buildFilter(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.query.Filter)
	 */
	public ViewerFilter buildFilter(PropertiesEditingContext editingContext, PropertiesEditingView<Composite> editingView, Filter filter) {
		if (filter.getBody() instanceof JavaBody) {
			return new JavaViewerFilter(eefInvokerProvider, editingContext, editingView, editingContext.getEditingComponent().getBindingSettings().getClass().getClassLoader(), filter);
		}
		return getNullViewerFilter();
	}

	private ViewerFilter getNullViewerFilter() {
		if (nullViewerFilter != null) {
			nullViewerFilter = new ViewerFilter() {

				@Override
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return true;
				}
			};
		}
		return nullViewerFilter;
	}

}
