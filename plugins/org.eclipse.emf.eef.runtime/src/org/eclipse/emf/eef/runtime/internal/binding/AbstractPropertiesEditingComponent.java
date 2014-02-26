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
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.View;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public abstract class AbstractPropertiesEditingComponent<T extends EObject> implements PropertiesEditingComponent {

	protected PropertiesEditingContext editingContext;
	protected EEFBindingSettings<T> bindingSettings;
	protected EObject source;
	protected final BiMap<View, Object> descriptorsToViews;

	/**
	 * @param bindingSettings
	 * @param source
	 */
	protected AbstractPropertiesEditingComponent(EEFBindingSettings<T> bindingSettings, EObject source) {
		this.bindingSettings = bindingSettings;
		this.source = source;
		this.descriptorsToViews = HashBiMap.create();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEObject()
	 * @state
	 */
	public EObject getEObject() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingContext()
	 * @state
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 * @state
	 */
	public void setEditingContext(PropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
		// editingContext
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBindingSettings()
	 */
	public EEFBindingSettings<T> getBindingSettings() {
		return bindingSettings;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setViewForDescriptor(java.lang.Object,
	 *      org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public void setViewForDescriptor(View descriptor, Object view) {
		descriptorsToViews.put(descriptor, view);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getDescriptorForView(java.lang.Object)
	 */
	public View getDescriptorForView(Object view) {
		return descriptorsToViews.inverse().get(view);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViews()
	 */
	public List<Object> getViews() {
		List<Object> result = Lists.newArrayList();
		for (Object descriptor : getViewDescriptors()) {
			result.add(descriptorsToViews.get(descriptor));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeView(java.lang.Object)
	 */
	public void removeView(Object view) {
		this.descriptorsToViews.remove(view);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#dispose()
	 * @state
	 */
	public void dispose() {
		editingContext.disposeComponent(this);

		// Making a blank component to be sure to not reuse it!
		bindingSettings = null;
		source = null;
		editingContext = null;
	}

}
