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
package org.eclipse.emf.samples.conference.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SessionCustomItemProvider extends SessionItemProvider implements ITableItemLabelProvider {

	/**
	 * @param adapterFactory
	 */
	public SessionCustomItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getColumnImage(java.lang.Object, int)
	 */
	
	public Object getColumnImage(Object object, int columnIndex) {
		return getImage(object);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getColumnText(java.lang.Object, int)
	 */
	
	public String getColumnText(Object object, int columnIndex) {
		return getText(object);
	}

}
