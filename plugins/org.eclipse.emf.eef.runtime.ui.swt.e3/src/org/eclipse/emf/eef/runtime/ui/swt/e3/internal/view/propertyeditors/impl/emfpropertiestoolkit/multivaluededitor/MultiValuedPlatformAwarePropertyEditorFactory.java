/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.multivaluededitor;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.ui.swt.EEFSWTConstants;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.econtainmenteditor.EContainmentPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.mutivaluededitor.MultiValuedPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.swttoolkit.hbox.HBoxContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.emfpropertiestoolkit.EMFPropertiesToolkit;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:nathalie.lepine@obeo.fr">Nathalie Lepine</a>
 *
 */
public class MultiValuedPlatformAwarePropertyEditorFactory extends MultiValuedPropertyEditorFactory {

	/**
	 * @param toolkit
	 */
	public MultiValuedPlatformAwarePropertyEditorFactory(EMFPropertiesToolkit emfPropertiesToolkit) {
		super(emfPropertiesToolkit);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public boolean serviceFor(PropertyEditorContext editorContext) {
		return MultiValuedPropertyEditorFactory.class.getName().equals(editorContext.viewElement.getRepresentation().getImplementation()) && editorContext.view.getContents() instanceof Composite;
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
			return new EContainmentPropertyEditor(toolkit.getEEFEditingServiceProvider(), toolkit.getEditUIProvidersFactory(), (PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement, new MultiValuedFormPropertyEditor(toolkit.getImageManager(),
					(PropertiesEditingView<Composite>) editorContext.view, (ElementEditor) editorContext.viewElement));
		} else {
			return super.createPropertyEditor(editorContext);
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.WidgetPropertyEditorFactoryImpl#getModel(org.eclipse.emf.ecore.resource.Resource)
	 */
	public Widget getModel(Resource resource) {
		TreeIterator<EObject> allContents = resource.getAllContents();
		while (allContents.hasNext()) {
			EObject next = allContents.next();
			if (next instanceof Widget) {
				if (MultiValuedPropertyEditorFactory.class.getName().equals(((Widget) next).getImplementation())) {
					return (Widget) next;
				}
 			}
		}
		return null;
	}
}
