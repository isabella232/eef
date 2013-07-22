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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ereferenceeditor;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.UIConstants;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.util.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceSWTPropertyEditor implements SWTPropertyEditor<MultiLinePropertyViewer> {

	private ImageManager imageManager;

	protected PropertiesEditingView<Composite> view;
	protected ElementEditor elementEditor;
	private MultiLinePropertyViewer multiLinePropertyViewer;

	/**
	 * @param imageManager
	 * @param view
	 * @param elementEditor
	 */
	public EReferenceSWTPropertyEditor(ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
		this.imageManager = imageManager;
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
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(final Composite parent) {
		if (view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).createLabel(view.getEditingComponent(), parent, elementEditor, elementEditor.getName());
		}
		multiLinePropertyViewer = new MultiLinePropertyViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL) {

			/**
			 * {@inheritDoc}
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
		multiLinePropertyViewer.setLocked(false);		
	}


}
