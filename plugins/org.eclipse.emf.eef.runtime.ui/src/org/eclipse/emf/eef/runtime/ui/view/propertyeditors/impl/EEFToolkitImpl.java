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
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class EEFToolkitImpl<T> implements EEFToolkit<T> {

	private List<WidgetPropertyEditorFactory<T>> widgetProviders;

	/**
	 * 
	 */
	public EEFToolkitImpl() {
		widgetProviders = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#serviceFor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 */
	public final boolean serviceFor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorFactory<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory#getPropertyEditor(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorFactory.PropertyEditorContext)
	 *      TODO: need cache
	 */
	public final PropertyEditor getPropertyEditor(PropertyEditorContext editorContext) {
		for (WidgetPropertyEditorFactory<T> provider : widgetProviders) {
			if (provider.serviceFor(editorContext)) {
				return provider.getPropertyEditor(editorContext);
			}
		}
		return null;
	}

	/**
	 * @return the widgetProviders
	 */
	public final Collection<WidgetPropertyEditorFactory<T>> getWidgetProviders() {
		return widgetProviders;
	}

	/**
	 * @param provider
	 */
	public final EEFToolkitImpl<T> addPropertyEditorFactory(WidgetPropertyEditorFactory<T> provider) {
		widgetProviders.add(provider);
		provider.setToolkit(this);
		getModel().getWidgets().add(provider.getModel());
		return this;
	}

	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		editingComponent.getEditingContext().getBindingManagerProvider().getBindingManager(editingComponent.getEObject()).firePropertiesChanged(editingComponent, editingEvent);
	}

	/**
	 * Clears the widgetProviders list.
	 */
	protected final void clearEditorProviders() {
		widgetProviders.clear();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit#getAllWidgetsFor(org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Collection<Widget> getAllWidgetsFor(EStructuralFeature eStructuralFeature) {
		Collection<Widget> widgets = new ArrayList<Widget>();
		for (WidgetPropertyEditorFactory<?> widgetPropertyEditorFactory : getWidgetProviders()) {
			if (widgetPropertyEditorFactory.canHandle(eStructuralFeature)) {
				widgets.add(widgetPropertyEditorFactory.getModel());
			}
		}
		return widgets;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit#getWidgetByName(java.lang.String)
	 */
	public Widget getWidgetByName(String name) {
		for (WidgetPropertyEditorFactory<?> widgetPropertyEditorFactory : getWidgetProviders()) {
			if (widgetPropertyEditorFactory.getModel() != null && name != null && name.equals(widgetPropertyEditorFactory.getModel().getName())) {
				return widgetPropertyEditorFactory.getModel();
			}
		}
		return null;
	}

}
