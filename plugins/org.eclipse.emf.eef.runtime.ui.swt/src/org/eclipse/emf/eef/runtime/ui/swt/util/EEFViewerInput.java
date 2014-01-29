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
package org.eclipse.emf.eef.runtime.ui.swt.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EEFViewerInput {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private PropertiesEditingContext editingContext;
	private EObject editedObject;
	private PropertyBinding propertyBinding;

	/**
	 * @param editingContext
	 * @param propertyBinding
	 */
	public EEFViewerInput(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingContext editingContext, PropertyBinding propertyBinding) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editingContext = editingContext;
		this.propertyBinding = propertyBinding;
	}

	/**
	 * @param editingContext
	 * @param editedObject
	 * @param editedFeature
	 */
	public EEFViewerInput(PropertiesEditingContext editingContext, EObject editedObject, PropertyBinding propertyBinding) {
		this.editingContext = editingContext;
		this.editedObject = editedObject;
		this.propertyBinding = propertyBinding;
	}

	/**
	 * @return the editingContext
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * @return the editedObject
	 */
	public EObject getEditedObject() {
		if (editedObject != null) {
			return editedObject;
		} else if (editingContext != null) {
			return editingContext.getEditingComponent().getEObject();
		} else {
			return null;
		}
 	}

	/**
	 * @return the propertyBinding
	 */
	public PropertyBinding getPropertyBinding() {
		return propertyBinding;
	}
	
	/**
	 * @return
	 */
	public Object getCurrentValue() {
		return eefEditingServiceProvider.getEditingService(getEditedObject()).getValue(getEditingContext(), getEditedObject(), getPropertyBinding());
	}
	
}
