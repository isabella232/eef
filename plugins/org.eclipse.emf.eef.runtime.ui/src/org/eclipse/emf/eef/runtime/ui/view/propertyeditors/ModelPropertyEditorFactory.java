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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ModelPropertyEditorFactory<T extends EObject,U> extends PropertyEditorFactory<U> {

	/**
	 * @return the model for this provider.
	 */
	T getModel();
	
	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);

}
