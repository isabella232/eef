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
package org.eclipse.emf.eef.runtime.ui.swt.internal.widgets;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify.EEFListener;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class SingleLinePropertyViewerListener extends EEFListener<SingleLinePropertyViewer> {
	
	public SingleLinePropertyViewerListener(PropertyEditor propertyEditor, PropertiesEditingView<Composite> view, ElementEditor elementEditor, SingleLinePropertyViewer viewer) {
		super(propertyEditor, view, elementEditor, viewer);
	}

	/**
	 * Notifies a "Set" operation.
	 */
	public abstract void set();

	/**
	 * Notifies a "Clear" operation.
	 */
	public abstract void clear();

}

