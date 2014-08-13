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
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class PropertiesEditingContextFactoryImpl implements PropertiesEditingContextFactory, DefaultService {

	private EditingContextFactoryProvider serviceProvider;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private BindingHandlerProvider bindingHandlerProvider;
	private ViewHandlerProvider viewHandlerProvider;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#setProvider(org.eclipse.emf.eef.runtime.services.EEFServiceProvider)
	 */
	public void setProvider(EditingContextFactoryProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#getContextFactoryProvider()
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return serviceProvider;
	}

	/**
	 * @param emfServiceProvider
	 *            the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider
	 *            the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * @param bindingHandlerProvider
	 *            the bindingHandlerProvider to set
	 */
	public void setBindingManagerProvider(BindingHandlerProvider bindingHandlerProvider) {
		this.bindingHandlerProvider = bindingHandlerProvider;
	}

	/**
	 * @param viewHandlerProvider
	 *            the viewHandlerProvider to set
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createNullEditingContext()
	 */
	public PropertiesEditingContext createNullEditingContext() {
		return new NullPropertiesEditingContextImpl();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(adapterFactory, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		EObjectPropertiesEditingContext context;
		if (parentContext instanceof DomainAwarePropertiesEditingContext) {
			context = new DomainPropertiesEditingContext(parentContext, eObject);
		} else if (parentContext instanceof EObjectPropertiesEditingContext) {
			context = new EObjectPropertiesEditingContext(parentContext, eObject);
		} else if (parentContext instanceof SemanticPropertiesEditingContext) {
			context = new EObjectPropertiesEditingContext(parentContext, eObject);
		} else {
			throw new IllegalArgumentException("Unable to process this context as a parent");
		}
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactoryEditingDomain domain, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.EditingDomain,
	 *      org.eclipse.emf.common.notify.AdapterFactory,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, adapterFactory, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createSemanticPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext,
	 *      org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public SemanticPropertiesEditingContext createSemanticPropertiesEditingContext(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		SemanticPropertiesEditingContext context;
		if (haveToCreateADomainAwareContext(parentContext)) {
			context = new SemanticDomainPropertiesEditingContext((DomainAwarePropertiesEditingContext) parentContext, editingEvent);
		} else {
			context = new SemanticPropertiesEditingContextImpl(parentContext, editingEvent);
		}
		return context;
	}

	/**
	 * Determines when to create a
	 * {@link SemanticDomainPropertiesEditingContext} or a
	 * {@link SemanticPropertiesEditingContext}.
	 * 
	 * A {@link SemanticPropertiesEditingContext} must be create when we are
	 * inside a wizard. The pattern to find this id the following : - My parent
	 * is a {@link DomainAwarePropertiesEditingContext} but not a
	 * {@link SemanticPropertiesEditingContext} - And my grandparent isn't a
	 * {@link SemanticPropertiesEditingContext}
	 * 
	 * @param parentContext
	 * @return
	 */
	private boolean haveToCreateADomainAwareContext(PropertiesEditingContext parentContext) {
		return parentContext instanceof DomainAwarePropertiesEditingContext && !(parentContext instanceof SemanticPropertiesEditingContext) && !(parentContext.getParentContext() instanceof SemanticPropertiesEditingContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createReflectivePropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public ReflectivePropertiesEditingContext createReflectivePropertiesEditingContext(AdapterFactory adapterFactory, EObject view) {
		ReflectivePropertiesEditingContext context = new ReflectivePropertiesEditingContext(adapterFactory, view);
		context.setEMFServiceProvider(emfServiceProvider);
		context.setEEFEditingServiceProvider(eefEditingServiceProvider);
		context.setBindingManagerProvider(bindingHandlerProvider);
		context.setContextFactoryProvider(getContextFactoryProvider());
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createReflectivePropertiesEditingContext(context,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	public ReflectivePropertiesEditingContext createReflectivePropertiesEditingContext(PropertiesEditingContext parentContext, EObject view) {
		ReflectivePropertiesEditingContext context = new ReflectivePropertiesEditingContext(parentContext, view);
		context.setEMFServiceProvider(emfServiceProvider);
		context.setEEFEditingServiceProvider(eefEditingServiceProvider);
		context.setBindingManagerProvider(bindingHandlerProvider);
		context.setContextFactoryProvider(getContextFactoryProvider());
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}

	private void configureEditingContext(EObjectPropertiesEditingContext context) {
		context.setEMFServiceProvider(emfServiceProvider);
		context.setEEFEditingServiceProvider(eefEditingServiceProvider);
		context.setBindingManagerProvider(bindingHandlerProvider);
		context.setContextFactoryProvider(getContextFactoryProvider());
		context.setViewHandlerProvider(viewHandlerProvider);
	}

}
