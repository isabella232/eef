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

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.junit.After;
import org.junit.Before;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class AbstractIntegrationTest {

	/* Trackers */
	private ServiceTracker editingContextFactoryProviderTracker;
	private ServiceTracker propertiesBindingHandlerTracker;
	private ServiceTracker emfServiceProviderTraker;
	private ServiceTracker eefEditingServiceProviderTraker;
	private ServiceTracker editUIProvidersFactoryTraker;

	/* EEF Services */
	private EditingContextFactoryProvider editingContextFactoryProvider;
	private PropertiesBindingHandler propertiesBindingHandler;
	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;
	private EditUIProvidersFactory editUIProvidersFactory;

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
		emfServiceProviderTraker = new ServiceTracker(bundleContext, EMFServiceProvider.class.getName(), null);
		emfServiceProviderTraker.open();
		emfServiceProvider = (EMFServiceProvider) emfServiceProviderTraker.getService();
		eefEditingServiceProviderTraker = new ServiceTracker(bundleContext, EEFEditingServiceProvider.class.getName(), null);
		eefEditingServiceProviderTraker.open();
		eefEditingServiceProvider = (EEFEditingServiceProvider) eefEditingServiceProviderTraker.getService();
		editUIProvidersFactoryTraker = new ServiceTracker(bundleContext, EditUIProvidersFactory.class.getName(), null);
		editUIProvidersFactoryTraker.open();
		editUIProvidersFactory = (EditUIProvidersFactory) editUIProvidersFactoryTraker.getService();
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

	/**
	 * @return the emfServiceProvider
	 */
	protected EMFServiceProvider getEmfServiceProvider() {
		return emfServiceProvider;
	}

	/**
	 * @return the eefEditingServiceProvider
	 */
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return eefEditingServiceProvider;
	}

	/**
	 * @return the editUIProvidersFactory
	 */
	public EditUIProvidersFactory getEditUIProvidersFactory() {
		return editUIProvidersFactory;
	}

	/**
	 * Init PEC on EObject
	 * 
	 * @return PropertiesEditingComponent
	 */
	public PropertiesEditingComponent initPropertiesEditingComponent(EObject eObject) {
		EditingContextFactoryProvider editingContextFactoryProvider = getEditingContextFactoryProvider();
		PropertiesEditingContext editingContext = editingContextFactoryProvider.getEditingContextFactory(eObject).createPropertiesEditingContext(getEditingDomain(), getAdapterFactory(), eObject);
		PropertiesEditingComponent editingComponent = editingContext.getEditingComponent();
		editingComponent.addEditingListener(new PropertiesEditingListener() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
			 */
			@Override
			public void firePropertiesChanged(PropertiesEditingEvent event) {
				if (event instanceof PropertiesValidationEditingEvent) {
					assertEquals(BasicDiagnostic.OK, ((PropertiesValidationEditingEvent) event).getDiagnostic().getCode());
				}

			}
		});
		return editingComponent;
	}

	/**
	 * init EEF Binding settings
	 * 
	 * @param uri
	 *            String
	 */
	@SuppressWarnings("restriction")
	protected void initEEFBindingSettings(final String uri) {
		Bundle bundle = FrameworkUtil.getBundle(getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		EEFBindingSettingsImpl bindingSettings = new EEFBindingSettingsImpl() {

			/**
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsImpl#getEditingModelPath()
			 */
			@Override
			protected String getEditingModelPath() {
				return uri;
			}

		};
		bindingSettings.setEMFServiceProvider(getEmfServiceProvider());
		bundleContext.registerService(EEFBindingSettings.class.getName(), bindingSettings, null);
	}

}
