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
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.change.util.ChangeRecorder;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.impl.ContextOptions;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class DelegatingPropertiesEditingContext implements PropertiesEditingContext {

	protected PropertiesEditingContext delegatingContext;

	/**
	 * @param delegatingContext
	 *            {@link PropertiesEditingContext} to use for delegation.
	 */
	public DelegatingPropertiesEditingContext(PropertiesEditingContext delegatingContext) {
		this.delegatingContext = delegatingContext;
	}

	/**
	 * @return the delegatingContext
	 */
	public final PropertiesEditingContext getDelegatingContext() {
		return delegatingContext;
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return delegatingContext.getEMFServiceProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEEFEditingServiceProvider()
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return delegatingContext.getEEFEditingServiceProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getBindingManagerProvider()
	 */
	public BindingHandlerProvider getBindingManagerProvider() {
		return delegatingContext.getBindingManagerProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getContextFactoryProvider()
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return delegatingContext.getContextFactoryProvider();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getViewHandlerProvider()
	 */
	public ViewHandlerProvider getViewHandlerProvider() {
		return delegatingContext.getViewHandlerProvider();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getParentContext()
	 */
	public PropertiesEditingContext getParentContext() {
		return delegatingContext.getParentContext();
	}

	/**
	 * @return
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		return delegatingContext.getEditingComponent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		return delegatingContext.getAdapterFactory();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		return delegatingContext.getOptions();
	}

	/**
	 * @param editingComponent
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void disposeComponent(PropertiesEditingComponent editingComponent) {
		delegatingContext.disposeComponent(editingComponent);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getChangeRecorder()
	 */
	public ChangeRecorder getChangeRecorder() {
		return delegatingContext.getChangeRecorder();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#startEditing()
	 */
	public void startEditing() {
		delegatingContext.startEditing();
	}

	/**
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#stopEditing()
	 */
	public void stopEditing() {
		delegatingContext.stopEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#cancelEditing()
	 */
	public void cancelEditing() {
		delegatingContext.cancelEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		delegatingContext.undoEditing();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {
		delegatingContext.dispose();
	}

}
