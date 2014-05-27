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

import java.util.Collection;

import org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify.EEFListener;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class MultiLinePropertyViewerListener extends EEFListener<MultiLinePropertyViewer> {

	public MultiLinePropertyViewerListener(PropertyEditor propertyEditor, PropertiesEditingView<Composite> view, ElementEditor elementEditor, MultiLinePropertyViewer viewer) {
		super(propertyEditor, view, elementEditor, viewer);
	}

	/**
	 * Handle a "add" request.
	 */
	public abstract void add();

	/**
	 * Handle a "edit" request.
	 * 
	 * @param editedElement
	 *            Edited Element.
	 */
	public abstract void edit(Object editedElement);

	/**
	 * Handle a "remove" request.
	 * 
	 * @param removedElement
	 *            Removed Element.
	 */
	public abstract void remove(Object removedElement);

	/**
	 * Handle a "remove" request for several elements.
	 * 
	 * @param removedElements
	 *            Removed Elements.
	 */
	public abstract void removeAll(Collection<?> removedElements);

	/**
	 * Handle a "move up" request.
	 * 
	 * @param movedElement
	 *            Moved Element.
	 */
	public abstract void moveUp(Object movedElement);

	/**
	 * Handle a "move down" request.
	 * 
	 * @param movedElement
	 *            Moved Element.
	 */
	public abstract void moveDown(Object movedElement);

}

