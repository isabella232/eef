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
package org.eclipse.emf.eef.runtime.ui.swt.e3.viewer;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.providers.PropertyBindingLabelProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class E3EditUIProvidersFactory implements EditUIProvidersFactory {

	private EMFServiceProvider emfServiceProvider;
	
	private AdapterFactory adapterFactory;
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#getAdapterFactory()
	 */
	public AdapterFactory getAdapterFactory() {
		if (this.adapterFactory == null) {
			this.adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return this.adapterFactory;
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createContentProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public IContentProvider createContentProvider(AdapterFactory adapterFactory) {
		AdapterFactory af = adapterFactory;
		if (af == null) {
			af = getAdapterFactory();
		}
		return new AdapterFactoryContentProvider(af);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createLabelProvider(org.eclipse.emf.common.notify.AdapterFactory)
	 */
	public ILabelProvider createLabelProvider(AdapterFactory adapterFactory) {
		return new AdapterFactoryLabelProvider(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.viewer.EditUIProvidersFactory#createPropertyBindingLabelProvider(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public ILabelProvider createPropertyBindingLabelProvider(AdapterFactory adapterFactory, PropertyBinding binding) {
		PropertyBindingLabelProvider propertyBindingLabelProvider = new PropertyBindingLabelProvider(adapterFactory, binding);
		propertyBindingLabelProvider.setEMFServiceProvider(emfServiceProvider);
		return propertyBindingLabelProvider;
	}

}
