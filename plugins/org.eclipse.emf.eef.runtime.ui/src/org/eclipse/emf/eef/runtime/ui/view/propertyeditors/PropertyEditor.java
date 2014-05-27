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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditor {

	/**
	 * Initialize the PropertyEditor for the given feature.
	 */
	void init();
	
	
	/**
	 * @return the {@link PropertyEditorViewer} responsible for building the UI part 
	 * of this editor.
	 */
	PropertyEditorViewer<?> getPropertyEditorViewer();
	
	
	/**
	 * Notifies the current editor of an editing event in the context of the given editing component.
	 * @param editingComponent {@link PropertiesEditingComponent} the context where the editing event has been thrown.
	 * @param editingEvent the {@link PropertiesEditingEvent} to process.
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);
}
