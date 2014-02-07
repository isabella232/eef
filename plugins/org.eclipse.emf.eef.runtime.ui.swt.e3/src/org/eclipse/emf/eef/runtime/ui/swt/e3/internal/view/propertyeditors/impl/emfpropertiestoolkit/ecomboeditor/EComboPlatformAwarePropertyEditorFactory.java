
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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboSWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboPlatformAwarePropertyEditorFactory extends EComboPropertyEditorFactory {

	/**
	 * @param toolkit
	 */
	public EComboPlatformAwarePropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
		super(emfPropertiesToolkit);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditorFactory#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit formtoolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		if (formtoolkit != null) {
			return new EComboE3PropertyEditor(
					toolkit.getEMFServiceProvider(),
					toolkit.getEEFEditingServiceProvider(),
					toolkit.getEditUIProvidersFactory(), 
					toolkit.getImageManager(), 
					toolkit.getViewerFilterBuilderProvider(), 
					(PropertiesEditingView<Composite>) editorContext.view, 
					(ElementEditor) editorContext.viewElement, 
					new EComboFormPropertyEditor(
							toolkit.getEditUIProvidersFactory(), 
							toolkit.getImageManager(), 
							(PropertiesEditingView<Composite>) editorContext.view, 
							(ElementEditor) editorContext.viewElement
						)
				);
		} else {
			return new EComboE3PropertyEditor(
					toolkit.getEMFServiceProvider(),
					toolkit.getEEFEditingServiceProvider(),
					toolkit.getEditUIProvidersFactory(), 
					toolkit.getImageManager(), 
					toolkit.getViewerFilterBuilderProvider(), 
					(PropertiesEditingView<Composite>) editorContext.view, 
					(ElementEditor) editorContext.viewElement, 
					new EComboSWTPropertyEditor(
							toolkit.getEditUIProvidersFactory(), 
							toolkit.getImageManager(), 
							(PropertiesEditingView<Composite>) editorContext.view, 
							(ElementEditor) editorContext.viewElement
						)
				);
		}
	}

}
