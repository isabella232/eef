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
package org.eclipse.emf.eef.runtime.ui.swt;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EEFRuntimeUISWT extends EMFPlugin {

	public static final String PLUGIN_ID = "org.eclipse.emf.eef.runtime.ui.swt";	
	
	/**
	 * Keep track of the singleton.
	 */
	public static final EEFRuntimeUISWT INSTANCE = new EEFRuntimeUISWT();

	/**
	 * Keep track of the singleton.
	 */
	private static Plugin plugin;

	/**
	 * Create the instance.
	 */
	public EEFRuntimeUISWT() {
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

		/**
		 * Creates an instance.
		 */
		public Plugin() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}
		
	}
}
