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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertyEditorImpl implements PropertyEditor {
	
	private ModelPropertyEditorFactory<? extends EObject, ?> propertyEditorProvider;
	
	/**
	 * @param propertyEditorProvider the propertyEditorProvider to set
	 */
	public void setPropertyEditorFactory(ModelPropertyEditorFactory<? extends EObject, ?> propertyEditorProvider) {
		this.propertyEditorProvider = propertyEditorProvider;
	}

	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	protected void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		propertyEditorProvider.firePropertiesChanged(editingComponent, editingEvent);
	}
	
}
