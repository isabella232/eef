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
package org.eclipse.emf.eef.runtime.binding.settings;

import java.util.Collection;
import java.util.Dictionary;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.binding.PropertyBindingCustomizer;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.internal.binding.settings.AbstractEEFBindingSettings;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.osgi.framework.Bundle;
import org.osgi.service.component.ComponentContext;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EEFBindingSettingsImpl extends AbstractEEFBindingSettings {

	private EEFLogger eefLogger;
	private String editingModelPath;
	private Bundle bundle;

	/**
	 * @param eefLogger
	 *            the eefLogger to set
	 */
	public final void setEEFLogger(EEFLogger eefLogger) {
		this.eefLogger = eefLogger;
	}

	/**
	 * @param context
	 *            ComponentContext
	 */
	public void activate(ComponentContext context) {
		Dictionary properties = context.getProperties();
		bundle = context.getBundleContext().getBundle();
		editingModelPath = (String) properties.get("eef.editingModel.path");
		if (editingModelPath != null) {
			editingModelPath = "platform:/plugin/" + bundle.getSymbolicName() + "/" + editingModelPath;
		}
	}

	/**
	 * This method can be overridden by subclasses to provide their own
	 * EditingModel.
	 * 
	 * @return an URI style path to the editing model to load for this provider.
	 */
	protected String getEditingModelPath() {
		return editingModelPath;
	}

	/**
	 * This method can be overridden by subclasses to provide their own
	 * EditingModel.
	 * 
	 * @return to the editing model to load for this provider.
	 */
	protected PropertiesEditingModel getEditingModel() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.binding.settings.AbstractEEFBindingSettings#initSpecificEditingModel()
	 */
	@Override
	protected final Collection<? extends PropertiesEditingModel> initSpecificEditingModel() {
		Collection<PropertiesEditingModel> result = Lists.newArrayList();
		if (getEditingModelPath() != null && !"".equals(getEditingModelPath())) {
			URI modelURI = URI.createURI(getEditingModelPath());
			try {
				Resource resource = getEditingModelEnvironment().getResourceSet().getResource(modelURI, true);
				result.addAll(getAllEditingModels(resource));
			} catch (WrappedException e) {
				if (eefLogger != null) {
					eefLogger.logError(EEFRuntime.PLUGIN_ID, "Unable to load the EEF Settings from " + modelURI.toString() + " file.", e);
				}
			}
		} else if (getEditingModel() != null) {
			result.add(getEditingModel());
		}
		if (!result.isEmpty()) {
			return result;
		} else {
			return super.initSpecificEditingModel();
		}
	}

	// Search all instances of PropertiesEditingModel in the given resource.
	// This implementation only search in the roots the resource.
	private Collection<? extends PropertiesEditingModel> getAllEditingModels(Resource resource) {
		Collection<PropertiesEditingModel> result = Lists.newArrayList();
		for (EObject root : resource.getContents()) {
			if (root instanceof PropertiesEditingModel) {
				result.add((PropertiesEditingModel) root);
			}
		}
		return result;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings#loadClass(java.lang.String)
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public Class<PropertyBindingCustomizer> loadClass(String className) {
		Class<PropertyBindingCustomizer> result = null;
		try {
			return (Class<PropertyBindingCustomizer>) bundle.loadClass(className);
		} catch (ClassNotFoundException e) {
			EEFRuntime.getPlugin().getLog().log(new Status(Status.ERROR, EEFRuntime.PLUGIN_ID, "Error when loading binding customizer class : " + className, e));
			return null;
		}
	}

}
