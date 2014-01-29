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

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.ui.swt.internal.binding.settings.GenericBindingSettings;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.MultiLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EReferencePropertyEditorFactory extends WidgetPropertyEditorFactoryImpl<Composite> {

	public static final String EREFERENCE_EDITOR_WIDGET_NAME = GenericBindingSettings.EREFERENCE_EDITOR_WIDGET_NAME;
	private static final Widget widget = ToolkitsFactory.eINSTANCE.createWidget();

	static {
		widget.setName(EREFERENCE_EDITOR_WIDGET_NAME);
	}

	protected final EMFPropertiesToolkit toolkit;

	/**
	 * @param toolkit
	 */
	public EReferencePropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return getModel() == editorContext.viewElement.getRepresentation() && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		return new EReferencePropertyEditor(
				toolkit.getEMFServiceProvider(),
				toolkit.getEEFEditingServiceProvider(),
				toolkit.getEditUIProvidersFactory(), 
				toolkit.getImageManager(), 
				toolkit.getViewerFilterBuilderProvider(),
				(PropertiesEditingView<Composite>) editorContext.view, 
				editorContext.propertyBinding, 
				(ElementEditor) editorContext.viewElement,
				(PropertyEditorViewer<MultiLinePropertyViewer>) 
				new EReferenceSWTPropertyEditor(
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
		return feature.isMany() && feature instanceof EReference && !((EReference) feature).isContainment();
	}
}
