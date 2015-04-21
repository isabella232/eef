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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.checkbox;

import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class CheckboxPlatformAwarePropertyEditorFactory extends CheckboxPropertyEditorFactory {

	/**
	 * @param toolkit
	 */
	public CheckboxPlatformAwarePropertyEditorFactory(SWTToolkit toolkit) {
		super(toolkit);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return CheckboxPropertyEditorFactory.class.getName().equals(editorContext.viewElement.getRepresentation().getImplementation()) && editorContext.view.getContents() instanceof Composite;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor.EComboPropertyEditorFactory#createPropertyEditor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	@SuppressWarnings("unchecked")
	protected PropertyEditor createPropertyEditor(PropertyEditorContext editorContext) {
		FormToolkit formtoolkit = editorContext.view.getEditingComponent().getEditingContext().getOptions().getOption(EEFSWTConstants.FORM_TOOLKIT);
		if (formtoolkit != null) {
			return new CheckboxPropertyEditor(toolkit.getEEFEditingServiceProvider(), (PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement, new CheckboxFormPropertyEditor((PropertiesEditingView<Composite>) editorContext.view,
					(ElementEditor) editorContext.viewElement));
		} else {
			return super.createPropertyEditor(editorContext);
		}
	}

}
