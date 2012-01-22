package org.eclipse.emf.eef.runtime;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EEFRuntime extends EMFPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.emf.eef.runtime"; //$NON-NLS-1$


	/**
	 * Keep track of the singleton.
	 */
	private static Plugin plugin;

	/**
	 * Create the instance.
	 */
	public EEFRuntime() {
		super
		  (new ResourceLocator [] {
		   });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	public static Plugin getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * @generated
	 */
	public static class Plugin extends EclipsePlugin {
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Plugin() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
	}

}
