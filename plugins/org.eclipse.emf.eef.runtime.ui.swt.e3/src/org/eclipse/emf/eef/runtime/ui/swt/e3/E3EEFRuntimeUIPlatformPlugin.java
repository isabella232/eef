package org.eclipse.emf.eef.runtime.ui.swt.e3;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class E3EEFRuntimeUIPlatformPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "runtime.ui.platform"; //$NON-NLS-1$

	// The shared instance
	private static E3EEFRuntimeUIPlatformPlugin plugin;
	
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
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static E3EEFRuntimeUIPlatformPlugin getPlugin() {
		return plugin;
	}

}
