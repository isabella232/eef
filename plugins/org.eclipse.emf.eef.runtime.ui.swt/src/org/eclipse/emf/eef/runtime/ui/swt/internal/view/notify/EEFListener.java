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
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.notify;

import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EEFListener<T extends Viewer> {

	protected final PropertyEditor propertyEditor;
	protected final PropertiesEditingView<Composite> view;
	protected final ElementEditor elementEditor;
	protected final T viewer;
	
	/**
	 * @param propertyEditor
	 * @param view
	 * @param elementEditor
	 * @param viewer
	 */
	public EEFListener(PropertyEditor propertyEditor, PropertiesEditingView<Composite> view, ElementEditor elementEditor, T viewer) {
		this.propertyEditor = propertyEditor;
		this.view = view;
		this.elementEditor = elementEditor;
		this.viewer = viewer;
	}

	private boolean enabled = true;
	
	public final void enable() {
		enabled = true;
	}
	
	public final void disable() {
		enabled = false;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the propertyEditor
	 */
	public PropertyEditor getPropertyEditor() {
		return propertyEditor;
	}

	/**
	 * @return the view
	 */
	public PropertiesEditingView<Composite> getView() {
		return view;
	}

	/**
	 * @return the elementEditor
	 */
	public ElementEditor getElementEditor() {
		return elementEditor;
	}

	/**
	 * @return the viewer
	 */
	public T getViewer() {
		return viewer;
	}
	
	
}
