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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.JavaView;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class NullPropertiesEditingComponent implements PropertiesEditingComponent {

	private static final EClassBinding ECLASS_BINDING = EditingModelFactory.eINSTANCE.createEClassBinding();
	private static final JavaView DEFAULT_VIEW = EditingModelFactory.eINSTANCE.createJavaView();
	
	private PropertiesEditingContext context;
	
	/**
	 * @param context
	 */
	public NullPropertiesEditingComponent(PropertiesEditingContext context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setViewForDescriptor(org.eclipse.emf.eef.runtime.editingModel.View, java.lang.Object)
	 */
	public void setViewForDescriptor(View descriptor, Object view) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setLockPolicies(java.util.Collection)
	 */
	public void setLockPolicies(Collection<EEFLockPolicy> lockPolicies) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public void setEditingContext(PropertiesEditingContext editingContext) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeView(java.lang.Object)
	 */
	public void removeView(Object view) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void removeEditingListener(PropertiesEditingListener listener) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#propagateEvent(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void propagateEvent(PropertiesEditingEvent event) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#isAffectingEvent(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean isAffectingEvent(Notification notification) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViews()
	 */
	public List<Object> getViews() {
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewDescriptors()
	 */
	public List<View> getViewDescriptors() {
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewChangeNotifier()
	 */
	public ViewChangeNotifier getViewChangeNotifier() {
		return new ViewChangeNotifier(context.getBindingManagerProvider(), this);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getLockPolicies()
	 */
	public Collection<EEFLockPolicy> getLockPolicies() {
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBindingSettings()
	 */
	public EEFBindingSettings getBindingSettings() {
		// TODO returning null Binding settings
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		return EditingModelFactory.eINSTANCE.createPropertiesEditingModel();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingContext()
	 */
	public PropertiesEditingContext getEditingContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEObject()
	 */
	public EObject getEObject() {
		return new DynamicEObjectImpl();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getDescriptorForView(java.lang.Object)
	 */
	public View getDescriptorForView(Object view) {
		return DEFAULT_VIEW;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBinding()
	 */
	public EClassBinding getBinding() {
		return ECLASS_BINDING;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#dispose()
	 */
	public void dispose() {
		// Do nothing
	}

	public void addEditingListener(PropertiesEditingListener listener) {
		// Do nothing
	}
}
