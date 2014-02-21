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
package org.eclipse.emf.eef.runtime.ui.view;

import java.util.Collection;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewElement;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingView<T> extends PropertiesEditingListener {
	
	/**
	 * @return a {@link View} describing the current view.
	 */
	View getViewModel();
	
	/**
	 * @return the {@link PropertiesEditingComponent} managing the view.
	 */
	PropertiesEditingComponent getEditingComponent();
	
	/**
	 * Returns the {@link PropertyEditor} managing the given editor element.
	 * @param editor element to process.
	 * @return the {@link PropertyEditor} managing the given editor element if exists, <code>null</code> otherwise.
	 */
	PropertyEditor getPropertyEditor(ViewElement editor);
	
	/**
	 * Returns all the {@link PropertyEditor} owned by the current view.
	 * @return a collection of {@link PropertyEditor} owned by the view.
	 */
	Collection<PropertyEditor> getAllPropertyEditors();
	
	/**
	 * @return the {@link ViewService} for the view.
	 */
	ViewService getViewService();
	
	/**
	 * @return the {@link ViewSettings} for the view.
	 */
	ViewSettings getViewSettings();
	
	/**
	 * @return the {@link Composite} containing view contents.
	 */
	T getContents();
	
	/**
	 * Disposes the contents of the view.
	 */
	void disposeContents();

}
