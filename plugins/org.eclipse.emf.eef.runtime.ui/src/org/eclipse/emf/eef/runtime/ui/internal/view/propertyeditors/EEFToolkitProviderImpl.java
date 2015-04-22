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
package org.eclipse.emf.eef.runtime.ui.internal.view.propertyeditors;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.logging.EEFLogger;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class EEFToolkitProviderImpl extends EEFServiceProviderImpl<PropertyEditorContext, EEFToolkit<?>> implements EEFToolkitProvider {

	private EEFLogger eefLogger;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider#getToolkit(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 *      WARNING: a unchecked cast is done in this method, client must ensure
	 *      that the result of this method is affected to the good type of
	 *      element!
	 */
	@SuppressWarnings("unchecked")
	public <T> EEFToolkit<T> getToolkit(PropertyEditorContext editorContext) {
		return (EEFToolkit<T>) getService(editorContext);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider
	 *      #getAllToolkits()
	 */
	public Collection<EEFToolkit<?>> getAllToolkits() {
		return getAllServices();
	}

	/**
	 * @param eefLogger
	 *            the eefLogger to set
	 */
	public void setEEFLogger(EEFLogger eefLogger) {
		this.eefLogger = eefLogger;
	}
	
	/**
	 * Returns all widgets which can display the feature.
	 * 
	 * @param eStructuralFeature
	 *            EStructuralFeature
	 */
	public Collection<Widget> getAllWidgetsFor(ResourceSet resourceSet, EStructuralFeature eStructuralFeature) {
		Collection<Widget> widgets = new ArrayList<Widget>();
		Collection<EEFToolkit<?>> allToolkits = getAllToolkits();
		for (EEFToolkit<?> toolkit : allToolkits) {
			widgets.addAll(toolkit.getAllWidgetsFor(resourceSet, eStructuralFeature));
		}
		return widgets;
	}

	/**
	 * @param name
	 *            String
	 * @return the first widget named "name" in toolkits.
	 */
	public Widget getWidgetByName(ResourceSet resourceSet, String name) {
		for (EEFToolkit<?> toolkit : getAllToolkits()) {
			Widget widget = toolkit.getWidgetByName(resourceSet, name);
			if (widget != null) {
				return widget;
			}
		}
		return null;
	}

}
