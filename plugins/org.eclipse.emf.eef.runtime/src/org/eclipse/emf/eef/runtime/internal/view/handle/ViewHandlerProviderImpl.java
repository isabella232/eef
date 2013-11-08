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
package org.eclipse.emf.eef.runtime.internal.view.handle;

import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerProviderImpl extends EEFServiceProviderImpl<View, ViewHandler<?>> implements ViewHandlerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider#getViewHandler(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public ViewHandler<?> getViewHandler(View view) {
		return getService(view);
	}

}
