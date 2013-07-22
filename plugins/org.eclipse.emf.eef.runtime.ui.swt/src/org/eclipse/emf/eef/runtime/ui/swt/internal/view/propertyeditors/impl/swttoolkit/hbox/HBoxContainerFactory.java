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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.hbox;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.Container;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class HBoxContainerFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();

	static {
		widget.setName("HorizontalBox");		
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
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation() && editorContext.view.getContents() instanceof Composite;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new HBoxPropertyEditor(new HBoxSWTPropertyEditor((Container) editorContext.viewElement));			 
	}

}
