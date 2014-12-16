/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.eef.editor;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.ui.swt.EEFRuntimeUISWT;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManagerProvider;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * This is the central singleton for the Views editor plugin. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public final class EditingModelEditPlugin extends EMFPlugin {

	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public static final EditingModelEditPlugin INSTANCE = new EditingModelEditPlugin();

	/**
	 * @not-generated
	 */
	public static final String EDITOR_EXTENSION = INSTANCE.getString("_UI_EditingModelEditorFilenameExtensions");
	/**
	 * @not-generated
	 */
	public static final String PLUGIN_ID = "org.eclipse.emf.eef.editor";

	/**
	 * Keep track of the singleton. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EditingModelEditPlugin() {
		super(new ResourceLocator[] { EEFRuntimeUISWT.getResourceLocator() });
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static class Implementation extends EclipseUIPlugin {

		private ServiceTracker editingContextFactoryProviderTracker;
		private ServiceTracker eefEditingServiceTracker;
		private ServiceTracker lockManagerProviderTracker;
		private ServiceTracker imageManagerTracker;
		private ServiceTracker editingPolicyProviderTracker;

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
		 */
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			editingContextFactoryProviderTracker = new ServiceTracker(context, EditingContextFactoryProvider.class.getName(), null);
			editingContextFactoryProviderTracker.open();
			eefEditingServiceTracker = new ServiceTracker(context, EEFEditingService.class.getName(), null);
			eefEditingServiceTracker.open();
			lockManagerProviderTracker = new ServiceTracker(context, EEFLockManagerProvider.class.getName(), null);
			lockManagerProviderTracker.open();
			imageManagerTracker = new ServiceTracker(context, ImageManager.class.getName(), null);
			imageManagerTracker.open();
			editingPolicyProviderTracker = new ServiceTracker(context, PropertiesEditingPolicyProvider.class.getName(), null);
			editingPolicyProviderTracker.open();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);
			lockManagerProviderTracker.close();
			imageManagerTracker.close();
			eefEditingServiceTracker.close();
			editingPolicyProviderTracker.close();
			editingContextFactoryProviderTracker.close();
		}

		/**
		 * Creates an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		public EditingContextFactoryProvider getEditingContextFactoryProvider() {
			return (EditingContextFactoryProvider) editingContextFactoryProviderTracker.getService();
		}

		public EEFEditingService getEEFEditingService() {
			return (EEFEditingService) eefEditingServiceTracker.getService();
		}

		public EEFLockManagerProvider getLockManagerProvider() {
			return (EEFLockManagerProvider) lockManagerProviderTracker.getService();
		}

		public ImageManager getImageManager() {
			return (ImageManager) imageManagerTracker.getService();
		}

		public PropertiesEditingPolicyProvider getEditingPolicyProvider() {
			return (PropertiesEditingPolicyProvider) editingPolicyProviderTracker.getService();
		}
	}

}
