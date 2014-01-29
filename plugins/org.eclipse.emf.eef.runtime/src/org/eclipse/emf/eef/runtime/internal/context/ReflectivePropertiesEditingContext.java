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
import org.eclipse.emf.ecore.EObject;
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
public class ReflectivePropertiesEditingContext implements PropertiesEditingContext {

	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private BindingHandlerProvider bindingHandlerProvider;
	private EditingContextFactoryProvider contextFactoryProvider;
	private ViewHandlerProvider viewHandlerProvider;

	private AdapterFactory adapterFactory;
	private EObject eObject;
	private ContextOptions options;
	private PropertiesEditingContext parentContext;

	private PropertiesEditingComponent component;

	/**
	 * @param adapterFactory the {@link AdapterFactory} to use in the current context.
	 * @param eObject the edited {@link EObject}.
	 */
	ReflectivePropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		this.adapterFactory = adapterFactory;
		this.eObject = eObject;
		this.options = new ContextOptions();
	}

	/**
	 * @param parentContext the parent {@link PropertiesEditingContext}.
	 * @param eObject the edited {@link EObject}.
	 */
	ReflectivePropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		this.eObject = eObject;
		this.options = new ContextOptions(parentContext.getOptions());
		this.parentContext = parentContext;
	}
	
	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param bindingHandlerProvider the bindingHandlerProvider to set
	 */
	public void setBindingManagerProvider(BindingHandlerProvider bindingHandlerProvider) {
		this.bindingHandlerProvider = bindingHandlerProvider;
	}

	/**
	 * @param contextFactoryProvider the contextFactoryProvider to set
	 */
	public void setContextFactoryProvider(EditingContextFactoryProvider contextFactoryProvider) {
		this.contextFactoryProvider = contextFactoryProvider;
	}

	/**
	 * @param viewHandlerProvider the viewHandlerProvider to set
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEMFServiceProvider()
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		if (emfServiceProvider != null) {
			return emfServiceProvider;
		} else {
			if (parentContext != null) {
				return parentContext.getEMFServiceProvider();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEEFEditingServiceProvider()
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return eefEditingServiceProvider;
	}

	/**
	 * @return the bindingHandlerProvider
	 */
	public BindingHandlerProvider getBindingManagerProvider() {
		return bindingHandlerProvider;
	}

	/**
	 * @return the contextFactoryProvider
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return contextFactoryProvider;
	}

	/**
	 * @return the viewHandlerProvider
	 */
	public ViewHandlerProvider getViewHandlerProvider() {
		return viewHandlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getParentContext()
	 */
	public PropertiesEditingContext getParentContext() {
		return parentContext;
	}

	/**
	 * @return the eObject
	 */
	public EObject getEObject() {
		return eObject;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getEditingComponent()
	 */
	public PropertiesEditingComponent getEditingComponent() {
		if (component == null) {
			component = bindingHandlerProvider.getBindingManager(eObject).createComponent(this);
		}
		return component;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		if (adapterFactory != null) {
			return adapterFactory;
		} else {
			if (parentContext != null) {
				return parentContext.getAdapterFactory();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#getOptions()
	 */
	public ContextOptions getOptions() {
		if (options != null) {
			return options;
		} else {
			if (parentContext != null) {
				return parentContext.getOptions();
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#disposeComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void disposeComponent(PropertiesEditingComponent editingComponent) {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#stopEditing()
	 */
	public void stopEditing() {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#cancelEditing()
	 */
	public void cancelEditing() {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#undoEditing()
	 */
	public void undoEditing() {
		// nothing to do
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContext#dispose()
	 */
	public void dispose() {

	}

}
