/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.mutivaluededitor;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 * 
 */
public class MultiValuedSWTPropertyEditor implements SWTPropertyEditor<MultiLinePropertyViewer>, FilterablePropertyEditor {

	private ImageManager imageManager;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	private MultiLinePropertyViewer multiLinePropertyViewer;

	private Collection<ViewerFilter> filters;

	public MultiValuedSWTPropertyEditor(ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.imageManager = imageManager;
		this.filters = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public MultiLinePropertyViewer getViewer() {
		return multiLinePropertyViewer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(final Composite parent) {
		if (view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).createLabel(view.getEditingComponent(), parent, elementEditor, elementEditor.getName());
		}
		multiLinePropertyViewer = new MultiLinePropertyViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL) {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer#buildAdditionnalActionControls(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void buildAdditionnalActionControls(Composite parent) {
				if (view.getViewService() instanceof SWTViewService) {
					((SWTViewService) view.getViewService()).createHelpButton(view.getEditingComponent(), parent, elementEditor);
				}
			}

		};
		for (EObject subEditor : elementEditor.eContents()) {
			if (subEditor instanceof ElementEditor) {
				multiLinePropertyViewer.addColumn(((ElementEditor) subEditor).getName(), UIConstants.DEFAULT_COLUMN_WIDTH);
			}
		}
		multiLinePropertyViewer.setImageManager(imageManager);
		multiLinePropertyViewer.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		multiLinePropertyViewer.setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#addFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void addFilter(ViewerFilter filter) {
		filters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#removeFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void removeFilter(ViewerFilter filter) {
		filters.remove(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#getFilters()
	 */
	public Collection<ViewerFilter> getFilters() {
		return filters;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		multiLinePropertyViewer.setLocked(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		multiLinePropertyViewer.setLocked(false);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#isLocked()
	 */
	public boolean isLocked() {
		return multiLinePropertyViewer.isLocked();
	}

}
