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
package org.eclipse.emf.eef.runtime.ui.util;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.editingModel.EditingOptions;
import org.eclipse.emf.eef.runtime.editingModel.FeatureDocumentationProvider;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.views.View;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewService extends EEFService<View> {
	
	/**
	 * Returns the label text for a given editor.
	 * @param editingComponent the current editingComponent.
	 * @param editor key of editor to process
	 * @param alternate alternative text
	 * @return the text
	 */
	String getDescription(PropertiesEditingComponent editingComponent, Object editor, String alternate);

	/**
	 * Returns documentation about the feature binded to the given editor. There is two strategies for getting this documentation:
	 * 	- getting the property description of the {@link GenFeature} associated
	 *  - getting the ecore documentation of the feature
	 * The choice of strategy is defined by the {@link EditingOptions} of the {@link PropertiesEditingModel}:
	 * 	- if the options are null or the {@link FeatureDocumentationProvider#GENMODEL_PROPERTY_DESCRIPTION} value is set, the first strategy is chosen
	 *  - if the {@link FeatureDocumentationProvider#ECORE_DOCUMENTATION} value is set, the second strategy is chosen
	 * @param editingComponent the current {@link PropertiesEditingComponent}.
	 * @param editor which to get the documentation.
	 * @return the found documentation if exists, <code>null</code> otherwise.
	 */
	String getHelpContent(PropertiesEditingComponent editingComponent, Object editor);
	
	/**
	 * Computes the 'best' input from the given source. Searching the {@link Resource} or
	 * the {@link ResourceSet} if it's an {@link EObject}.
	 * @param sourceInput source.
	 * @return the best input.
	 */
	Object getBestInput(Object sourceInput);

	/**
	 * Searches the editingDomain for the given {@link IAdaptable}.
	 * @param part {@link IAdaptable} editing an EObject.
	 * @return {@link EditingDomain} to use to edit the current {@link EObject}.
	 */
	EditingDomain getEditingDomain(IAdaptable part);
	
}
