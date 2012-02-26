package org.eclipse.emf.eef.runtime;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;

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

		private Diagnostician diagnostician;

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

			diagnostician = new Diagnostician() {

				/**
				 * {@inheritDoc}
				 * 
				 * @see org.eclipse.emf.ecore.util.Diagnostician#doValidateContents(org.eclipse.emf.ecore.EObject,
				 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
				 */
				@Override
				protected boolean doValidateContents(EObject eObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
					return true;
				}

			};
		}

		/**
		 * @return the diagnostician
		 */
		public Diagnostician getEEFValidator() {
			return diagnostician;
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

	}
}