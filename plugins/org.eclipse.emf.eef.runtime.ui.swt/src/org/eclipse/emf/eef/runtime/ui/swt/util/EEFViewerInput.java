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
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EEFViewerInput {

	private EEFEditingServiceProvider eefEditingServiceProvider;
	private PropertiesEditingContext editingContext;
	private EObject editedObject;
	private ElementEditor elementEditor;

	/**
	 * @param eefEditingServiceProvider
	 * @param editingContext
	 * @param elementEditor
	 */
	public EEFViewerInput(EEFEditingServiceProvider eefEditingServiceProvider, PropertiesEditingContext editingContext, ElementEditor elementEditor) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
		this.editingContext = editingContext;
		this.elementEditor = elementEditor;
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
	 * @return
	 */
	public Object getCurrentValue() {
		return eefEditingServiceProvider.getEditingService(getEditedObject()).getValue(getEditingContext(), getEditedObject(), elementEditor);
	}
	
}
