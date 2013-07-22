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
package org.eclipse.emf.eef.runtime.ui.swt.util;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface SWTViewService extends ViewService {
	
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
	 * Instantiates a new label to display the given feature.
	 * @param editingComponent the current {@link PropertiesEditingComponent}.
	 * @param parent label container.
	 * @param editor feature to display.
	 * @param alternate alternative text.
	 * @return created label.
	 */
	Label createLabel(PropertiesEditingComponent editingComponent, Composite parent, Object editor, String alternate);
	
	/**
	 * Instantiates a help button for the given feature.
	 * @param parent button container.
	 * @param editor feature to process.
	 * @return created control.
	 */
	Control createHelpButton(PropertiesEditingComponent editingComponent, final Composite parent, Object editor);
	
	/**
	 * Creates a Text with smart scrollbars.
	 * @param parent the {@link Composite} where to create the widget.
	 * @param styles widget styles.
	 * @return the created Text.
	 */
	Text createScrollableText(Composite parent, int styles);

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
