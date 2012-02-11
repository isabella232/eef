package org.eclipse.emf.eef.runtime.ui;

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
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EEFRuntimeUI INSTANCE = new EEFRuntimeUI();

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Plugin plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Plugin getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static class Plugin extends EclipsePlugin {

		private AdapterFactory adapterFactory;

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
