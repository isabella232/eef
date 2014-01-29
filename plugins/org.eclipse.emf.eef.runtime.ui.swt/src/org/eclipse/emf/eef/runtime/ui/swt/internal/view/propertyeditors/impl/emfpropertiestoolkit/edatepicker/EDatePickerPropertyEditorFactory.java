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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.edatepicker;

import java.util.Date;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EDatePickerPropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	public static final String EDATE_PICKER_EDITOR_WIDGET_NAME = GenericBindingSettings.EDATE_PICKER_EDITOR_WIDGET_NAME;
	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();

	static {
		widget.setName(EDATE_PICKER_EDITOR_WIDGET_NAME);
	}

	protected final EMFPropertiesToolkit toolkit;

	/**
	 * @param toolkit
	 */
	public EDatePickerPropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
		this.toolkit = emfPropertiesToolkit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#getModel()
	 */
	public Widget getModel() {
		return widget;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation() && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new EDatePackerPropertyEditor(
				toolkit.getEEFEditingServiceProvider(),
				(PropertiesEditingView<Composite>) editorContext.view, 
				(ElementEditor) editorContext.viewElement, 
				new EDatePickerSWTPropertyEditor(
						toolkit.getEditUIProvidersFactory(), 
						toolkit.getImageManager(), 
						(PropertiesEditingView<Composite>) editorContext.view,
						(ElementEditor) editorContext.viewElement
					)
				);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#canHandle(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean canHandle(EStructuralFeature feature) {
		return feature.getEType() instanceof Date;
	}

}
