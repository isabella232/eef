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
import java.util.Dictionary;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.WidgetPropertyEditorFactory;
import org.eclipse.emf.eef.views.toolkits.Widget;
import org.osgi.service.component.ComponentContext;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class EEFToolkitImpl<T> implements EEFToolkit<T> {

	private List<WidgetPropertyEditorFactory<T>> widgetProviders;
	private String toolkitModelPath;
	private List<String> cache;

	/**
	 * 
	 */
	public EEFToolkitImpl() {
		widgetProviders = Lists.newArrayList();
		cache = Lists.newArrayList();
	}

	/**
	 * @param context
	 *            ComponentContext
	 */
	public void activate(ComponentContext context) {
		Dictionary properties = context.getProperties();
		String toolkitPath = (String) properties.get(EEF_TOOLKIT_MODEL_PATH_KEY);
		initToolkitPath(toolkitPath);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit#initToolkitPath(java.lang.String)
	 */
	public void initToolkitPath(String toolkitPath) {
		toolkitModelPath = toolkitPath;
		ResourceSet rs = new ResourceSetImpl();
		Resource resourceImpl = rs.getResource(URI.createURI(toolkitModelPath), true);
		TreeIterator<EObject> allContents = resourceImpl.getAllContents();
		while (allContents.hasNext()) {
			EObject next = allContents.next();
			if (next instanceof Widget) {
				cache.add(((Widget) next).getName());
			}
		}
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
		return this;
	}

	/**
	 * @param editingComponent
	 * @param editingEvent
	 */
	public final void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent) {
		editingComponent.getEditingContext().getBindingManagerProvider().getBindingHandler(editingComponent.getEObject()).firePropertiesChanged(editingComponent, editingEvent);
	}

	/**
	 * Clears the widgetProviders list.
	 */
	protected final void clearEditorProviders() {
		widgetProviders.clear();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit#getAllWidgetsFor(org.eclipse.emf.ecore.resource.ResourceSet, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public Collection<Widget> getAllWidgetsFor(ResourceSet resourceSet, EStructuralFeature eStructuralFeature) {
		Collection<Widget> widgets = new ArrayList<Widget>();
		for (WidgetPropertyEditorFactory<?> widgetPropertyEditorFactory : getWidgetProviders()) {
			if (widgetPropertyEditorFactory.canHandle(eStructuralFeature)) {
				Resource resource = resourceSet.getResource(URI.createURI(toolkitModelPath), true);
				widgets.add(widgetPropertyEditorFactory.getModel(resource));
			}
		}
		return widgets;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkit#getWidgetByName(org.eclipse.emf.ecore.resource.ResourceSet, java.lang.String)
	 */
	public Widget getWidgetByName(ResourceSet resourceSet, String name) {
		if (cache.contains(name)) {
			Resource resource = resourceSet.getResource(URI.createURI(toolkitModelPath), true);
			return (Widget) resource.getEObject(name);
		}
		return null;
	}

}
