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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * @author glefur
 *
 */
public interface ViewHelper {
	
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
	public Label createLabel(Composite parent, Object editor, String alternate);
	
	/**
	 * Instantiate a help button for the given feature.
	 * @param parent button container.
	 * @param editor feature to process.
	 * @param alternate alternative text.
	 * @param helpID help ID
	 * @return created control.
	 */
	public Control createHelpButton(final Composite parent, Object editor);

}
