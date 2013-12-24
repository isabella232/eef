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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets.FormEReferenceEditor;
import org.eclipse.emf.eef.runtime.ui.swt.e3.util.PlatformAwareViewService;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.FormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceFormPropertyEditor implements FormPropertyEditor<MultiLinePropertyViewer>, FilterablePropertyEditor {

	private ImageManager imageManager;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	private MultiLinePropertyViewer multiLinePropertyViewer;

	private Collection<ViewerFilter> filters;

	/**
	 * @param imageManager
	 * @param view
	 * @param elementEditor
	 */
	public EReferenceFormPropertyEditor(ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.imageManager = imageManager;
		this.view = view;
		this.elementEditor = elementEditor;
		this.filters = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public MultiLinePropertyViewer getViewer() {
		return multiLinePropertyViewer;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.FormPropertyEditor#build(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	public void build(final FormToolkit toolkit, final Composite parent) {
		final ViewService viewService = view.getViewService();
		if (viewService instanceof PlatformAwareViewService) {
			((PlatformAwareViewService)viewService).createLabel(view.getEditingComponent(), toolkit, parent, elementEditor, elementEditor.getName());
		} else if (viewService instanceof SWTViewService){
			((SWTViewService) viewService).createLabel(view.getEditingComponent(), parent, elementEditor, elementEditor.getName());			
		}
		multiLinePropertyViewer = new FormEReferenceEditor(toolkit, parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL) {

			/**
			 * {@inheritDoc}
			 * @see org.eclipse.emf.eef.runtime.ui.widgets.MultiLinePropertyViewer#buildAdditionnalActionControls(org.eclipse.swt.widgets.Composite)
			 */
			@Override
			protected void buildAdditionnalActionControls(Composite parent) {
				if (viewService instanceof PlatformAwareViewService) {
					((PlatformAwareViewService)viewService).createHelpButton(view.getEditingComponent(), toolkit, parent, elementEditor);
				}else if (viewService instanceof SWTViewService){
					((SWTViewService) viewService).createHelpButton(view.getEditingComponent(), parent, elementEditor);
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
		toolkit.adapt((Composite) multiLinePropertyViewer.getControl());
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		layoutData.heightHint = view.getViewSettings().getMultiEditorHeight();
		layoutData.horizontalSpan = 2;
		multiLinePropertyViewer.setLayoutData(layoutData);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#addFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void addFilter(ViewerFilter filter) {
		filters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#removeFilter(org.eclipse.jface.viewers.ViewerFilter)
	 */
	public void removeFilter(ViewerFilter filter) {
		filters.remove(filter);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.FilterablePropertyEditor#getFilters()
	 */
	public Collection<ViewerFilter> getFilters() {
		return filters;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		multiLinePropertyViewer.setLocked(true);		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		multiLinePropertyViewer.setLocked(true);		
	}


}
