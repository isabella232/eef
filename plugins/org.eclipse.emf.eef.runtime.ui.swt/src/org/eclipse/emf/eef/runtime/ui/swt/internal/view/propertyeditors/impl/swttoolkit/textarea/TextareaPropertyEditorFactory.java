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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.textarea;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class TextareaPropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	protected final SWTToolkit toolkit;

	/**
	 * @param toolkit
	 */
	public TextareaPropertyEditorFactory(SWTToolkit toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return super.serviceFor(editorContext) && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new TextareaPropertyEditor(toolkit.getEEFEditingServiceProvider(), (PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement, new TextareaSWTPropertyEditor((PropertiesEditingView<Composite>) editorContext.view,
				(ElementEditor) editorContext.viewElement));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#canHandle(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean canHandle(EStructuralFeature feature) {
		return !feature.isMany() && feature instanceof EAttribute;
	}
}
