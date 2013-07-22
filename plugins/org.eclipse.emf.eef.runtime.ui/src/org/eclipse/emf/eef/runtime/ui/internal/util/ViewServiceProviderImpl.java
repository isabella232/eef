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
package org.eclipse.emf.eef.runtime.ui.internal.util;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewServiceProviderImpl extends EEFServiceProviderImpl<View, ViewService> implements ViewServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider#getViewService(org.eclipse.emf.eef.views.View)
	 */
	public ViewService getViewService(View view) {
		return getService(view);
	}

}
