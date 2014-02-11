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
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

import com.google.common.base.Function;

public interface PropertiesBindingHandler extends EEFService<EObject> {

	/**
	 * @return the current {@link PropertiesEditingPolicyProvider}.
	 */
	PropertiesEditingPolicyProvider getPolicyProvider();

	/**
	 * Creates a new {@link PropertiesEditingComponent}.
	 * 
	 * @param editingContext
	 *            the {@link PropertiesEditingContext} asking the component.
	 * @return the created {@link PropertiesEditingComponent}.
	 */
	PropertiesEditingComponent createComponent(PropertiesEditingContext editingContext);

	/**
	 * Executes operations to be done to dispose the given
	 * {@link PropertiesEditingComponent}.
	 * 
	 * @param component
	 *            {@link PropertiesEditingComponent} to dispose.
	 */
	void disposeComponent(PropertiesEditingComponent component);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);

	/**
	 * Notifies the current component of a lock change.
	 * 
	 * @param editingComponent
	 *            {@link PropertiesEditingComponent} the editingComponent to
	 *            lock.
	 * @param lockEvent
	 *            {@link EEFLockEvent} describing the lock change.
	 */
	void fireLockChanged(PropertiesEditingComponent editingComponent, EEFLockEvent lockEvent);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#execute(org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy,
	 *      org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	void execute(PropertiesEditingComponent editingComponent, PropertiesEditingPolicy editingPolicy, PropertiesEditingContext policyEditingContext);

	/**
	 * Executes a {@link Function} on all the created {@link ViewHandler} by the
	 * current {@link PropertiesEditingComponent}.
	 * 
	 * @param function
	 *            the {@link Function} to execute.
	 */
	void executeOnViews(PropertiesEditingComponent editingComponent, Function<Object, Void> function);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#notifyChanged(Notification)
	 */
	void notifyModelChanged(PropertiesEditingComponent editingComponent, Notification msg);

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#notifyChanged(Notification)
	 */
	void notifySettingsChanged(PropertiesEditingComponent editingComponent, Notification msg);

	/**
	 * Initializes the {@link EEFLockPolicy} to use in the given component.
	 * 
	 * @param editingComponent
	 *            the {@link PropertiesEditingComponent} to initialize.
	 */
	void initLockPolicies(PropertiesEditingComponent editingComponent);

}