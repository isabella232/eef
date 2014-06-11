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
package org.eclipse.emf.eef.runtime.tests.integration;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class AbstractIntegrationTest {

	/* Trackers */ 
	private ServiceTracker editingContextFactoryProviderTracker;
	private ServiceTracker propertiesBindingHandlerTracker;

	/* EEF Services */
	private EditingContextFactoryProvider editingContextFactoryProvider;
	private PropertiesBindingHandler propertiesBindingHandler;
	
	/* Editing artifacts */
	private AdapterFactory adapterFactory;
	private EditingDomain editingDomain;
	
	@Before
	public void setUp() throws BundleException {
		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		editingContextFactoryProviderTracker = new ServiceTracker(bundleContext, EditingContextFactoryProvider.class.getName(), null);
		editingContextFactoryProviderTracker.open();
		editingContextFactoryProvider = (EditingContextFactoryProvider) editingContextFactoryProviderTracker.getService();
		propertiesBindingHandlerTracker = new ServiceTracker(bundleContext, PropertiesBindingHandler.class.getName(), null);
		propertiesBindingHandlerTracker.open();
		propertiesBindingHandler = (PropertiesBindingHandler) propertiesBindingHandlerTracker.getService();
	}
	
	@After
	public void tearDown() {
		editingContextFactoryProviderTracker.close();
		propertiesBindingHandlerTracker.close();
	}
	
	/**
	 * @return
	 */
	protected final AdapterFactory getAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return adapterFactory;
	}
	
	/**
	 * @return
	 */
	protected final EditingDomain getEditingDomain() {
		if (editingDomain == null) {
			editingDomain = new AdapterFactoryEditingDomain(getAdapterFactory(), new BasicCommandStack());
		}
		return editingDomain;
	}

	/**
	 * @return the propertiesEditingContextFactory
	 */
	protected final EditingContextFactoryProvider getEditingContextFactoryProvider() {
		return editingContextFactoryProvider;
	}

	/**
	 * @return the propertiesBindingHandler
	 */
	protected final PropertiesBindingHandler getPropertiesBindingHandler() {
		return propertiesBindingHandler;
	}
	
}
