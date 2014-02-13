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
import org.eclipse.emf.eef.runtime.ui.swt.internal.util.EEFViewerFilterInvocationParametersImpl;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.util.EEFInvokerProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class JavaViewerFilter extends ViewerFilter {
	
	private EEFInvokerProvider eefInvokerProvider;
	
	private ClassLoader loader;
	private Filter filter;
	private PropertiesEditingContext editingContext;
	private PropertiesEditingView<Composite> editingView;

	public JavaViewerFilter(EEFInvokerProvider eefInvokerProvider, PropertiesEditingContext editingContext, PropertiesEditingView<Composite> editingView, ClassLoader loader, Filter filter) {
		this.eefInvokerProvider = eefInvokerProvider;
		this.editingContext = editingContext;
		this.editingView = editingView;
		this.loader = loader;
		this.filter = filter;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Object invoke = eefInvokerProvider.getInvoker(filter.getBody()).invoke(loader, filter.getBody(), new EEFViewerFilterInvocationParametersImpl(editingContext, editingView, element));
		if (invoke instanceof Boolean) {
			return (Boolean) invoke;
		}
		return true;
	}

}
