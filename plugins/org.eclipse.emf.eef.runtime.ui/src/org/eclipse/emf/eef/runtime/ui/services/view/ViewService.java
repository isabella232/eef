/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.ui.services.view;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewService extends EEFService<View> {
	
	/**
	 * Defines the constant for an unknown EEF representation type.
	 */
	String UNKNOW_EEF_TYPE = "unknown eef type";

	/**
	 * Defines the key for the EEF widgets id.
	 */
	String EEF_WIDGET_ID_KEY = "org.eclipse.emf.eef.widgets.id";

	/**
	 * Defines the key for the EEF widgets type.
	 */
	String EEF_WIDGET_TYPE_KEY = "org.eclipse.emf.eef.widgets.type";

	/**
	 * @return the {@link PropertiesEditingComponent} of this helper.
	 */
	PropertiesEditingComponent getEditingComponent();
	
	/**
	 * Sets the {@link PropertiesEditingComponent} for this helper.
	 * @param editingComponent {@link PropertiesEditingModel} of the helper.
	 */
	void setEditingComponent(PropertiesEditingComponent editingComponent);
	
	/**
	 * Returns the label text for a given editor.
	 * @param editor key of editor to process
	 * @param alternate alternative text
	 * @return the text
	 */
	String getDescription(Object editor, String alternate);

	/**
	 * Instantiates a new label to display the given feature.
	 * @param parent label container.
	 * @param editor feature to display.
	 * @param alternate alternative text.
	 * @return created label.
	 */
	Label createLabel(Composite parent, Object editor, String alternate);
	
	/**
	 * Instantiates a help button for the given feature.
	 * @param parent button container.
	 * @param editor feature to process.
	 * @return created control.
	 */
	Control createHelpButton(final Composite parent, Object editor);
	
	/**
	 * Creates a Text with smart scrollbars.
	 * @param parent the {@link Composite} where to create the widget.
	 * @param styles widget styles.
	 * @return the created Text.
	 */
	Text createScrollableText(Composite parent, int styles);

	/**
	 * Returns documentation about the feature binded to the given editor. There is two strategies for getting this documentation:
	 * 	- getting the property description of the {@link GenFeature} associated
	 *  - getting the ecore documentation of the feature
	 * The choice of strategy is defined by the {@link EditingOptions} of the {@link PropertiesEditingModel}:
	 * 	- if the options are null or the {@link FeatureDocumentationProvider#GENMODEL_PROPERTY_DESCRIPTION} value is set, the first strategy is chosen
	 *  - if the {@link FeatureDocumentationProvider#ECORE_DOCUMENTATION} value is set, the second strategy is chosen
	 * @param editor which to get the documentation.
	 * @return the found documentation if exists, <code>null</code> otherwise.
	 */
	String getHelpContent(Object editor);
	
	/**
	 * Returns the type of a widget
	 * 
	 * @param widget the widget to inspect
	 * @return the EEF type of the widget
	 */
	String getEEFType(Control widget);

	/**
	 * Sets the EEF type of widget.
	 * 
	 * @param widget the widget where put the ID
	 * @param value the type of the widget
	 */
	void setEEFtype(Control widget, String value);

	/**
	 * Returns the ID of a widget
	 * 
	 * @param widget the widget to inspect
	 * @return the ID of the widget
	 */
	Object getID(Control widget);

	/**
	 * Sets an id to a given widget.
	 * 
	 * @param widget the widget where put the ID
	 * @param value the ID to put
	 */
	void setID(Control widget, Object value);
	
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
	
	/**
	 * Executes the given job in the best Thread UI in a synchronous way.
	 * @param display a {@link Display} where to execute the job.
	 * @param job the Job to execute.
	 */
	void executeSyncUIRunnable(Display display, Runnable job);

	/**
	 * Executes the given job in the best Thread UI in a asynchronous way.
	 * @param display a {@link Display} where to execute the job.
	 * @param job the Job to execute.
	 */
	void executeAsyncUIRunnable(Display display, Runnable job);
}
