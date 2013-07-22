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
package org.eclipse.emf.eef.runtime.view.handle;

import org.eclipse.emf.eef.runtime.editingModel.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHandlerProvider {
	
	/**
	 * Returns the most appropriate view handler for the given view.
	 * @param view the view to operate.
	 * @return the most appropriate {@link ViewHandler} for the given view. 
	 */
	ViewHandler<?> getViewHandler(View view);
	
}
