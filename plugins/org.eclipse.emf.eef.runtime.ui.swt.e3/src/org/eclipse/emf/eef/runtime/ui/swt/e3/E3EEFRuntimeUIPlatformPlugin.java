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
package org.eclipse.emf.eef.runtime.ui.swt.e3;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class E3EEFRuntimeUIPlatformPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.emf.eef.runtime.ui.swt.e3"; //$NON-NLS-1$

	// The shared instance
	private static E3EEFRuntimeUIPlatformPlugin plugin;

	private AdapterFactory adapterFactory;
	private ServiceTracker editingContextFactoryProviderTracker;
	private ServiceTracker bindingSettingsProviderTracker;
	private ServiceTracker emfServiceProviderTracker;
	private ServiceTracker eefEditingServiceProviderTracker;
	private ServiceTracker viewServiceProviderTracker;
	private ServiceTracker eefToolkitProviderTracker;

	
	/**
	 * The constructor
	 */
	public E3EEFRuntimeUIPlatformPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		editingContextFactoryProviderTracker = new ServiceTracker(context, EditingContextFactoryProvider.class.getName(), null);
		editingContextFactoryProviderTracker.open();
		bindingSettingsProviderTracker = new ServiceTracker(context, EEFBindingSettingsProvider.class.getName(), null);
		bindingSettingsProviderTracker.open();
		emfServiceProviderTracker = new ServiceTracker(context, EMFServiceProvider.class.getName(), null);
		emfServiceProviderTracker.open();
		eefEditingServiceProviderTracker = new ServiceTracker(context, EEFEditingServiceProvider.class.getName(), null);
		eefEditingServiceProviderTracker.open();
		viewServiceProviderTracker = new ServiceTracker(context, ViewServiceProvider.class.getName(), null);
		viewServiceProviderTracker.open();
		eefToolkitProviderTracker = new ServiceTracker(context, EEFToolkitProvider.class.getName(), null);
		eefToolkitProviderTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		editingContextFactoryProviderTracker.close();
		bindingSettingsProviderTracker.close();
		emfServiceProviderTracker.close();
		viewServiceProviderTracker.close();
		eefToolkitProviderTracker.close();
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static E3EEFRuntimeUIPlatformPlugin getPlugin() {
		return plugin;
	}

	/**
	 * Log an error in the plugin.
	 * @param message error message.
	 * @param e the cause exception.
	 */
	public void logError(String message, Exception e) {
		getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, message, e));
	}

	/**
	 * Log a warning in the plugin.
	 * @param message error message.
	 * @param e the cause exception.
	 */
	public void logWarning(String message, Exception e) {
		getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, message, e));
	}

	/**
	 * @return
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return (EditingContextFactoryProvider)editingContextFactoryProviderTracker.getService();
	}
	
	/**
	 * @return
	 */
	public EEFBindingSettingsProvider getBindingSettingsProvider() {
		return (EEFBindingSettingsProvider) bindingSettingsProviderTracker.getService();
	}
	
	/**
	 * @return
	 */
	public EMFServiceProvider getEMFServiceProvider() {
		return (EMFServiceProvider) emfServiceProviderTracker.getService();
	}
	
	public EEFEditingServiceProvider getEEFEditingServiceProvider() {
		return (EEFEditingServiceProvider) eefEditingServiceProviderTracker.getService();
	}
	
	public ViewServiceProvider getViewServiceProvider() {
		return (ViewServiceProvider) viewServiceProviderTracker.getService();
	}
	
	public EEFToolkitProvider getEEFToolkitProvider() {
		return (EEFToolkitProvider) eefToolkitProviderTracker.getService();
	}
	
	public AdapterFactory getRegistryAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return adapterFactory;
	}
}
