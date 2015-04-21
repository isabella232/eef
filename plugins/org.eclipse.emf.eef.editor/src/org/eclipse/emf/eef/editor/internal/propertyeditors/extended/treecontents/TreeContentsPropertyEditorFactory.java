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
package org.eclipse.emf.eef.editor.internal.propertyeditors.extended.treecontents;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.editor.internal.propertyeditors.extended.EEFExtendedToolkit;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class TreeContentsPropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	private EEFExtendedToolkit eefExtendedToolkit;

	/**
	 * @param eefExtendedToolkit
	 */
	public TreeContentsPropertyEditorFactory(EEFExtendedToolkit eefExtendedToolkit) {
		this.eefExtendedToolkit = eefExtendedToolkit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return super.serviceFor(editorContext) && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@Override
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		PropertyEditorViewer<TreeEEFViewer> propertyEditorViewer;
		FormToolkit toolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) editorContext.view;
		ElementEditor viewElement = (ElementEditor) editorContext.viewElement;
		EEFEditingService editingService = eefExtendedToolkit.getEEFEditingServiceProvider().getEditingService(view.getEditingComponent().getEObject());
		EditingDomain editingDomain = editingService.searchEditingDomain(view.getEditingComponent().getEditingContext());
		if (toolkit != null) {
			propertyEditorViewer = new TreeContentsFormPropertyEditor(eefExtendedToolkit.getEMFServiceProvider(), eefExtendedToolkit.getEEFEditingServiceProvider(), eefExtendedToolkit.getImageManager(), editingDomain, view, viewElement);
		} else {
			propertyEditorViewer = new TreeContentsSWTPropertyEditor(eefExtendedToolkit.getEMFServiceProvider(), eefExtendedToolkit.getEEFEditingServiceProvider(), eefExtendedToolkit.getImageManager(), editingDomain, view, viewElement);
		}
		return new TreeContentsPropertyEditor(editorContext.view.getEditingComponent().getEditingContext().getContextFactoryProvider(), eefExtendedToolkit.getEMFServiceProvider(), eefExtendedToolkit.getEEFEditingServiceProvider(), view, viewElement, propertyEditorViewer);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#canHandle(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean canHandle(EStructuralFeature feature) {
		return false;
	}

}
