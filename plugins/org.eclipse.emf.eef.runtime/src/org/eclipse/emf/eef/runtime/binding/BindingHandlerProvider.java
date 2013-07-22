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
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.ecore.EObject;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface BindingHandlerProvider {

	/**
	 * Returns a {@link PropertiesBindingHandler} able to handle the given type of EObject.
	 * @param target the {@link EObject} to manage.
	 * @return the most appropriate {@link PropertiesBindingHandler}.
	 */
	PropertiesBindingHandler getBindingManager(EObject target);
	
}
