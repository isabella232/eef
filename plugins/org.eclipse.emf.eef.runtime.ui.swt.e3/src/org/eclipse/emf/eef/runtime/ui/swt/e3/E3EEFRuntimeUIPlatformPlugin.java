package org.eclipse.emf.eef.runtime.ui.swt.e3;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class E3EEFRuntimeUIPlatformPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "runtime.ui.platform"; //$NON-NLS-1$

	// The shared instance
	private static E3EEFRuntimeUIPlatformPlugin plugin;

	private AdapterFactory adapterFactory;
	private ServiceTracker eefServiceRegistryTracker;
	private ServiceTracker editingContextFactoryProviderTracker;
	private ServiceTracker emfServiceProviderTracker;

	
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
		eefServiceRegistryTracker = new ServiceTracker(context, EEFServiceRegistry.class.getName(), null);
		eefServiceRegistryTracker.open();
		editingContextFactoryProviderTracker = new ServiceTracker(context, EditingContextFactoryProvider.class.getName(), null);
		editingContextFactoryProviderTracker.open();
		emfServiceProviderTracker = new ServiceTracker(context, EMFServiceProvider.class.getName(), null);
		emfServiceProviderTracker.open();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		eefServiceRegistryTracker.close();
		editingContextFactoryProviderTracker.close();
		emfServiceProviderTracker.close();
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
	public EEFServiceRegistry getServiceRegistry() {
		return (EEFServiceRegistry) eefServiceRegistryTracker.getService();
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
	public EMFServiceProvider getEMFServiceProvider() {
		return (EMFServiceProvider) emfServiceProviderTracker.getService();
	}
	
	public AdapterFactory getRegistryAdapterFactory() {
		if (adapterFactory == null) {
			adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		}
		return adapterFactory;
	}
}
