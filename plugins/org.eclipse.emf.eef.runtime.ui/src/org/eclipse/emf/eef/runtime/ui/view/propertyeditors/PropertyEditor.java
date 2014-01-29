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

import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditor {

	/**
	 * Initialize the PropertyEditor for the given feature.
	 * @param propertyBinding {@link PropertyBinding} describing the binding for the current editor.
	 */
	void init(PropertyBinding propertyBinding);
	
	
	/**
	 * @return the {@link PropertyEditorViewer} responsible for building the UI part 
	 * of this editor.
	 */
	PropertyEditorViewer<?> getPropertyEditorViewer();
}
