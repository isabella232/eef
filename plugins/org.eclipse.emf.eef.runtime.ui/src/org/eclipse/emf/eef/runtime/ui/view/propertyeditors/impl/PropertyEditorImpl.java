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

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertyEditorImpl implements PropertyEditor {

	private PropertyEditorFactory<?> propertyEditorProvider;

	/**
	 * @param propertyEditorProvider
	 *            the propertyEditorProvider to set
	 */
	public void setPropertyEditorFactory(PropertyEditorFactory<?> propertyEditorProvider) {
		this.propertyEditorProvider = propertyEditorProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#firePropertiesChanged(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent,
	 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		propertyEditorProvider.firePropertiesChanged(editingComponent, editingEvent);
	}

}
