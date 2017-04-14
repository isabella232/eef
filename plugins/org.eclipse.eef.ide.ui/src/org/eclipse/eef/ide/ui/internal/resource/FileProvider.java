/*******************************************************************************
 * Copyright (c) 2005, 2017 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.eef.ide.ui.internal.resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.eef.ide.ui.internal.EEFIdeUiPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * To find a file in the workspace or in the plugins.
 *
 * @author cbrun
 */
public class FileProvider {

	/**
	 * The sole instance.
	 */
	private static FileProvider instance;

	/**
	 * Saves the plugin for each file.
	 */
	private Map<File, String> file2plugin = new HashMap<File, String>();

	/**
	 * Saves the relative path for each file.
	 */
	private Map<File, String> file2path = new HashMap<File, String>();

	/**
	 * Gets the sole instance.
	 *
	 * @return the sole instance
	 */
	public static FileProvider getDefault() {
		if (FileProvider.instance == null) {
			FileProvider.instance = new FileProvider();
		}
		return FileProvider.instance;
	}

	/**
	 * Gets the file for the given full path in the workspace or in the plugins.
	 *
	 * @param fullPath
	 *            is the full path of the file
	 * @return An optional containing the file found or an empty optional if none found
	 */
	public Optional<File> getFile(final IPath fullPath) {
		Optional<File> optionalFile = Optional.empty();

		if (fullPath != null && fullPath.segmentCount() > 0) {
			Optional<IFile> iFile = FileProvider.findFile(fullPath);
			if (iFile.isPresent()) {
				optionalFile = iFile.map(IFile::getLocation).map(IPath::toFile);
			} else {
				String pluginId = fullPath.segment(0);
				optionalFile = this.getFile(pluginId, fullPath.removeFirstSegments(1)); // remove '/Project'
			}

		}
		return optionalFile;
	}

	/**
	 * Gets the file for the relative path in the given plugin.
	 *
	 * @param pluginId
	 *            is the plugin
	 * @param relativePath
	 *            is the relative path in the plugin
	 * @return An optional containing the file found or an empty optional if none found
	 */
	private Optional<File> getFile(final String pluginId, final IPath relativePath) {
		return this.getFile(pluginId, relativePath, true);
	}

	/**
	 * Gets the file for the relative path in the given plugin.
	 *
	 * @param pluginId
	 *            is the plugin
	 * @param relativePath
	 *            is the relative path in the plugin
	 * @param requiredSearch
	 *            true to search in the required bundles
	 * @return An optional containing the file found or an empty optional if none found
	 */
	private Optional<File> getFile(final String pluginId, final IPath relativePath, final boolean requiredSearch) {
		return Optional.ofNullable(Platform.getBundle(pluginId)).flatMap(bundle -> {
			Optional<File> optionalFile = Optional.ofNullable(bundle.getEntry(relativePath.toString())).flatMap(url -> {
				return this.getFileFromUrl(url, pluginId, relativePath, bundle);
			});

			if (!optionalFile.isPresent() && requiredSearch) {
				optionalFile = getFileFromBundles(relativePath, bundle);
			}

			return optionalFile;
		});
	}

	/**
	 * Gets the file for the given URL in the plugin with the given pluginId.
	 *
	 * @param url
	 *            The URL
	 * @param pluginId
	 *            The pluginId
	 * @param relativePath
	 *            The relative path
	 * @param bundle
	 *            The bundle
	 * @return An optional with the file found or an empty optional if none found
	 */
	private Optional<File> getFileFromUrl(final URL url, final String pluginId, final IPath relativePath, final Bundle bundle) {
		File file = new File(FileProvider.transformToAbsolutePath(url));
		if (file.exists()) {
			if (!this.file2plugin.containsKey(file)) {
				this.file2plugin.put(file, pluginId);
				this.file2path.put(file, relativePath.toString());

				// Copy the properties in the bundle area
				Enumeration<?> allProperties = bundle.findEntries(relativePath.removeLastSegments(1).toString(), "*.properties", true); //$NON-NLS-1$
				while (allProperties != null && allProperties.hasMoreElements()) {
					// @formatter:off
					Optional.ofNullable(allProperties.nextElement())
						.filter(URL.class::isInstance)
						.map(URL.class::cast)
						.ifPresent(propertyFileURL -> {
							File propertyFile = new File(FileProvider.transformToAbsolutePath(propertyFileURL));
							if (propertyFile.exists()) {
								this.file2plugin.put(propertyFile, pluginId);
							}
						});
					// @formatter:on
				}
			}
			return Optional.of(file);
		}
		return Optional.empty();
	}

	/**
	 * Gets the file with the relative path from the given bundle.
	 * 
	 * @param relativePath
	 *            The relative path of the file
	 * @param bundle
	 *            The bundle
	 * @return An optional containing the file found or an empty optional if none found
	 */
	private Optional<File> getFileFromBundles(final IPath relativePath, final Bundle bundle) {
		String requiredBundles = bundle.getHeaders().get(Constants.REQUIRE_BUNDLE);
		if (requiredBundles != null) {
			final StringTokenizer st = new StringTokenizer(requiredBundles, ","); //$NON-NLS-1$
			while (st.hasMoreTokens()) {
				String id = st.nextToken().trim();
				int iDot = id.indexOf(';');
				if (iDot > -1) {
					id = id.substring(0, iDot).trim();
				}
				if (id.length() > 0) {
					Optional<File> optionalScriptFile = getFile(id, relativePath, false);
					if (optionalScriptFile.isPresent()) {
						return optionalScriptFile;
					}
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Finds and returns the file identified by the given path in the workspace, or null if no such file exists.
	 *
	 * @param path
	 *            is the path of the desired resource
	 * @return An optional containing the member file, or an empty optional if no such resource exists
	 */
	private static Optional<IFile> findFile(final IPath path) {
		return FileProvider.findResource(path).filter(IFile.class::isInstance).map(IFile.class::cast);
	}

	/**
	 * Finds and returns the member resource identified by the given path in the workspace, or null if no such resource
	 * exists.
	 *
	 * @param path
	 *            is the path of the desired resource
	 * @return An optional containing the member resource, or an empty optional if no such resource exists
	 */
	private static Optional<IResource> findResource(final IPath path) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace.getRoot().exists(path)) {
			return Optional.ofNullable(workspace.getRoot().findMember(path));
		}
		return Optional.empty();
	}

	/**
	 * Creates the absolute path.
	 *
	 * @param url
	 *            is the relative path
	 * @return the absolute file path
	 */
	private static String transformToAbsolutePath(final URL url) {
		String absolutePath;
		try {
			URL transformedUrl = FileLocator.toFileURL(url);
			File file = new File(transformedUrl.getFile());
			absolutePath = file.getAbsolutePath();
		} catch (final IOException e) {
			absolutePath = ""; //$NON-NLS-1$
			EEFIdeUiPlugin.getPlugin().error(e.getMessage(), e);
		}
		return absolutePath;
	}
}
