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
import java.util.Map;

import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.ui.internal.view.util.ViewSettingsImpl;
import org.eclipse.emf.eef.runtime.ui.util.ViewService;
import org.eclipse.emf.eef.runtime.ui.util.ViewServiceProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.EEFToolkitProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor;
import org.eclipse.emf.eef.views.View;
import org.eclipse.emf.eef.views.ViewElement;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractPropertiesEditingView<T> implements PropertiesEditingView<T> {
	
	private ViewServiceProvider viewServiceProvider;
	protected EEFToolkitProvider eefToolkitProvider;
	
	protected PropertiesEditingComponent editingComponent;
	protected View viewDescriptor;
	
	protected Map<ViewElement, PropertyEditor> propertyEditors;
	protected T contentsComposite;
	protected ViewService service;
	
	/**
	 * Non-parameterized constructor for {@link SectionPropertiesEditingView} purpose.
	 * Mustn't be use otherwise.
	 */
	public AbstractPropertiesEditingView() { }
	
	/**
	 * @param editingComponent {@link PropertiesEditingComponent} managing the view.
	 */
	public AbstractPropertiesEditingView(PropertiesEditingComponent editingComponent, View viewDescriptor) {
		this.viewDescriptor = viewDescriptor;
		this.editingComponent = editingComponent;
		this.propertyEditors = Maps.newHashMap();
		editingComponent.addEditingListener(this);
	}

	/**
	 * @param viewServiceProvider the viewServiceProvider to set
	 */
	public void setViewServiceProvider(ViewServiceProvider viewServiceProvider) {
		this.viewServiceProvider = viewServiceProvider;
	}

	/**
	 * @param eefToolkitProvider the eefToolkitProvider to set
	 */
	public void setToolkitPropertyEditorFactory(EEFToolkitProvider eefToolkitProvider) {
		this.eefToolkitProvider = eefToolkitProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewModel()
	 */
	public final View getViewModel() {
		return viewDescriptor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getEditingComponent()
	 */
	public final PropertiesEditingComponent getEditingComponent() {
		return editingComponent;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getPropertyEditor(org.eclipse.emf.eef.views.ViewElement)
	 */
	public final PropertyEditor getPropertyEditor(ViewElement editor) {
		return propertyEditors.get(editor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getAllPropertyEditors()
	 */
	public Collection<PropertyEditor> getAllPropertyEditors() {
		return propertyEditors.values();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewService()
	 */
	public final ViewService getViewService() {
		if (service == null) {
			service = viewServiceProvider.getViewService(viewDescriptor);
		}
		return service;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getViewSettings()
	 */
	public final ViewSettings getViewSettings() {
		return new ViewSettingsImpl();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView#getContents()
	 */
	public final T getContents() {
		return contentsComposite;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public final void firePropertiesChanged(PropertiesEditingEvent event) {
		// Default : Do nothing
	}
}
