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
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewHelper {
	
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
	 * Set the toolkit to use for creating widgets
	 * @param toolkit widget factory.
	 */
	void setToolkit(FormToolkit toolkit);
	
	/**
	 * Return the label text for a given editor.
	 * @param editor key of editor to process
	 * @param alternate altenative text
	 * @return the text
	 */
	String getDescription(Object editor, String alternate);

	/**
	 * Instantiate a new label to display the given feature.
	 * @param parent label container.
	 * @param editor feature to display.
	 * @param alternate alternative text.
	 * @return created label.
	 */
	Label createLabel(Composite parent, Object editor, String alternate);
	
	/**
	 * Instantiate a help button for the given feature.
	 * @param parent button container.
	 * @param editor feature to process.
	 * @param alternate alternative text.
	 * @param helpID help ID
	 * @return created control.
	 */
	Control createHelpButton(final Composite parent, Object editor);

	/**
	 * Return the ID of a widget
	 * 
	 * @param widget
	 *            the widget to inspect
	 * @return the ID of the widget
	 */
	String getEEFType(Control widget);

	/**
	 * Set the EEF type of widget.
	 * 
	 * @param widget
	 *            the widget where put the ID
	 * @param value
	 *            the type of the widget
	 */
	void setEEFtype(Control widget, String value);

	/**
	 * Return the ID of a widget?
	 * 
	 * @param widget
	 *            the widget to inspect
	 * @return the ID of the widget
	 */
	Object getID(Control widget);

	/**
	 * Set an id to a given widget.
	 * 
	 * @param widget
	 *            the widget where put the ID
	 * @param value
	 *            the ID to put
	 */
	void setID(Control widget, Object value);
	
	/**
	 * Compute the 'best' input from the given source. Searching the {@link Resource} or
	 * the {@link ResourceSet} if it's an {@link EObject}.
	 * @param sourceInput source.
	 * @return the best input.
	 */
	Object getBestInput(Object sourceInput);

	/**
	 *	Search the editingDomain for the given WorkbenchPart.
	 * @param part {@link IWorkbenchPart} editing the EObject.
	 * @return {@link EditingDomain} to use to edit the current {@link EObject}.
	 */
	EditingDomain getEditingDomain(IWorkbenchPart part);

}
