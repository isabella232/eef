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
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FormPropertyEditor<VIEWER extends Viewer> extends PropertyEditorViewer<VIEWER> {

	/**
	 * Build the PropertyEditor in the given composite.
	 * @param toolkit {@link FormToolkit} to use to build the PropertyEditor.
	 * @param parent {@link Composite} where to build the PropertyEditor.
	 */
	public void build(FormToolkit toolkit, Composite parent);
	
}
