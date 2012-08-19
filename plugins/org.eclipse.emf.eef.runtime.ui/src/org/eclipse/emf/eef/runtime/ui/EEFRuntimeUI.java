package org.eclipse.emf.eef.runtime.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistryTracker;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.services.ViewServiceRegistry;
import org.eclipse.emf.eef.views.View;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.BundleContext;

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
		private EEFServiceRegistryTracker<ViewServiceRegistry> tracker;

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
		 * {@inheritDoc}
		 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
		 */
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			tracker = new EEFServiceRegistryTracker<ViewServiceRegistry>(context, ViewServiceRegistry.class);
			tracker.open();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);
			tracker.close();
		}
		
		/**
		 * Return the {@link ViewService} associated to the given view.
		 * @param view context {@link View}.
		 * @return the {@link ViewService} to use.
		 */
		public ViewService getViewService(View view) {
			return tracker.getService().getServiceForView(view);
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
