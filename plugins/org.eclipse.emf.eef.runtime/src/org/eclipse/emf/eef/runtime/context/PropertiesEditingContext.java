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
package org.eclipse.emf.eef.runtime.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public interface PropertiesEditingContext {

	/**
	 * @return the {@link EMFServiceProvider} of the current
	 *         {@link PropertiesEditingContext}.
	 */
	EMFServiceProvider getEMFServiceProvider();

	/**
	 * @return the {@link EEFEditingServiceProvider} of the current
	 *         {@link PropertiesEditingContext}.
	 */
	EEFEditingServiceProvider getEEFEditingServiceProvider();

	/**
	 * @return the {@link BindingHandlerProvider} to use in the current context.
	 */
	BindingHandlerProvider getBindingManagerProvider();

	/**
	 * @return the {@link EditingContextFactoryProvider} to use in the current
	 *         context.
	 */
	EditingContextFactoryProvider getContextFactoryProvider();

	/**
	 * @return the {@link ViewHandlerProvider} to use in the current context.
	 */
	ViewHandlerProvider getViewHandlerProvider();

	/**
	 * @return the parent context of the current
	 *         {@link PropertiesEditingContext}.
	 */
	PropertiesEditingContext getParentContext();

	/**
	 * Returns a {@link PropertiesEditingComponent} binding the edited model
	 * element.
	 * 
	 * @return a {@link PropertiesEditingComponent} binded on the edited model
	 *         element.
	 */
	PropertiesEditingComponent getEditingComponent();

	/**
	 * @return the {@link AdapterFactory} of the context.
	 */
	AdapterFactory getAdapterFactory();

	/**
	 * @return the {@link ContextOptions} object of this context.
	 */
	ContextOptions getOptions();

	/**
	 * @param editingComponent
	 *            the {@link PropertiesEditingComponent} to dispose.
	 */
	void disposeComponent(PropertiesEditingComponent editingComponent);

	/**
	 * Dispose the current context.
	 */
	void dispose();

}
