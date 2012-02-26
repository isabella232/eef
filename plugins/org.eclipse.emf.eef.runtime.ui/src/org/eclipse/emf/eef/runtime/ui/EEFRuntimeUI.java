package org.eclipse.emf.eef.runtime.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * The activator class controls the plug-in life cycle
 */
public class EEFRuntimeUI extends EMFPlugin {

	public static final String PLUGIN_ID = "org.eclipse.emf.eef.runtime.ui";	
	
	/**
	 * Keep track of the singleton.
	 */
	public static final EEFRuntimeUI INSTANCE = new EEFRuntimeUI();

	/**
	 * Keep track of the singleton.
	 */
	private static Plugin plugin;

	/**
	 * Create the instance.
	 */
	public EEFRuntimeUI() {
		super
		  (new ResourceLocator [] {
		   });
	}
	
	/**
	 * @return this instance as {@link ResourceLocator}.
	 */
	public static ResourceLocator getResourceLocator() {
		return INSTANCE;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * @return the singleton instance.
	 */
	@Override
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
	 */
	public static class Plugin extends EclipsePlugin {

		private AdapterFactory adapterFactory;

		/**
		 * Creates an instance.
		 */
		public Plugin() {
			super();

			// Remember the static instance.
			//
			plugin = this;
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
		 * Return an embedded image of the runtime.
		 * @param key ID of the image
		 * @return the associated image.
		 */
		public Image getRuntimeImage(String key)  {
			Object image = getResourceLocator().getImage(key);
			return ExtendedImageRegistry.getInstance().getImage(image);
		}
		
		public AdapterFactory getRegistryAdapterFactory() {
			if (adapterFactory == null) {
				adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			}
			return adapterFactory;
		}
	}
}
