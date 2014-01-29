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
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertyBindingLabelProvider extends AdapterFactoryLabelProvider {

	private EEFEditingServiceProvider eefEditingServiceProvider;	
	private PropertiesEditingContext editingContext;
	private PropertyBinding propertyBinding;



	public PropertyBindingLabelProvider(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingContext editingContext, PropertyBinding propertyBinding) {
		super(editingContext.getAdapterFactory());
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editingContext = editingContext;
		this.propertyBinding = propertyBinding;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provid er.AdapterFactoryLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	@Override
	public Image getColumnImage(Object object, int columnIndex) {
		if (object instanceof EObject && propertyBinding.getSubPropertyBindings().size() > columnIndex) {
			EObject element= (EObject) object;
			Object target = eefEditingServiceProvider.getEditingService(element).getValue(editingContext, element, propertyBinding.getSubPropertyBindings().get(columnIndex));
			return getImage(target);
		}
		return super.getColumnImage(object, columnIndex);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getColumnText(java.lang.Object, int)
	 */
	@Override
	public String getColumnText(Object object, int columnIndex) {
		if (object instanceof EObject && propertyBinding.getSubPropertyBindings().size() > columnIndex) {
			EObject element= (EObject) object;
			Object target = eefEditingServiceProvider.getEditingService(element).getValue(editingContext, element, propertyBinding.getSubPropertyBindings().get(columnIndex));
			return getText(target);
		}
		return super.getColumnText(object, columnIndex);
	}

}
