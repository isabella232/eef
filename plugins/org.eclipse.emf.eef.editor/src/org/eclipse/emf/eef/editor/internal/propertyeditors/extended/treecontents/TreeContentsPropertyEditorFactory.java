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

import org.eclipse.emf.eef.editor.internal.propertyeditors.extended.EEFExtendedToolkit;
import org.eclipse.emf.eef.editor.internal.widgets.TreeEEFViewer;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TreeContentsPropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	public static final String TREE_CONTENTS_WIDGET_NAME = "TreeContents";
	
	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();

	static {
		widget.setName(TREE_CONTENTS_WIDGET_NAME);		
	}
	
	private EEFExtendedToolkit eefExtendedToolkit;
	
	/**
	 * @param eefExtendedToolkit
	 */
	public TreeContentsPropertyEditorFactory(EEFExtendedToolkit eefExtendedToolkit) {
		this.eefExtendedToolkit = eefExtendedToolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#getModel()
	 */
	public Widget getModel() {
		return widget;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation() && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@Override
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		PropertyEditorViewer<TreeEEFViewer> propertyEditorViewer;
		FormToolkit toolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		PropertiesEditingView<Composite> view = (PropertiesEditingView<Composite>) editorContext.view;
		ElementEditor viewElement = (ElementEditor) editorContext.viewElement;
		if (toolkit != null) {
			propertyEditorViewer = new TreeContentsFormPropertyEditor(
					eefExtendedToolkit.getEMFServiceProvider(), eefExtendedToolkit.getImageManager(), 
					view, viewElement);
		} else {
			propertyEditorViewer = new TreeContentsSWTPropertyEditor(
					eefExtendedToolkit.getEMFServiceProvider(), eefExtendedToolkit.getImageManager(), 
					view, viewElement);
		}
		return new TreeContentsPropertyEditor(
						eefExtendedToolkit.getEditingContextFactoryProvider(), eefExtendedToolkit.getEMFServiceProvider(), 
						view, viewElement, 
						propertyEditorViewer);
	}

}
