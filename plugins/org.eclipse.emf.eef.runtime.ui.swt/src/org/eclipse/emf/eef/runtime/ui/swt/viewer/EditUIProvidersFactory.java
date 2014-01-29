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
package org.eclipse.emf.eef.runtime.ui.swt.viewer;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EditUIProvidersFactory {
	
	/**
	 * @return an instance of an {@link AdapterFactory}.
	 */
	AdapterFactory getAdapterFactory();

	/**
	 * Creates an {@link IContentProvider} for EEF viewers using the given {@link AdapterFactory}. If this adapterFactory is <code>null</code>, 
	 * getAdapterFactory() method is used.
	 * @param adapterFactory a contextual {@link AdapterFactory}.
	 * @return the content provider to use in EEF viewers.
	 */
	IContentProvider createContentProvider(AdapterFactory adapterFactory);

	/**
	 * Create an {@link ILabelProvider} for EEF viewers using the given {@link AdapterFactory}. If this adapterFactory is <code>null</code>, 
	 * getAdapterFactory() method is used.
	 * @param adapterFactory a contextual {@link AdapterFactory}.
	 * @return the label provider to use in EEF viewers.
	 */
	ILabelProvider createLabelProvider(AdapterFactory adapterFactory);

	/**
	 * Create an {@link ILabelProvider} for EEF viewers using the given {@link PropertiesEditingContext}.
	 * @param editingContext the current {@link PropertiesEditingContext}.
	 * @return the label provider to use in EEF viewers.
	 */
	ILabelProvider createPropertyBindingLabelProvider(PropertiesEditingContext editingContext, PropertyBinding binding);
	
}
