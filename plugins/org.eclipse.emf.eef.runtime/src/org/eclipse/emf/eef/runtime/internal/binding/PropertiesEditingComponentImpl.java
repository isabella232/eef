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
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.internal.binding.settings.AbstractEEFBindingSettings;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.util.EEFEditingService;
import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class PropertiesEditingComponentImpl extends AbstractPropertiesEditingComponent<PropertiesEditingModel> {

	private PropertiesEditingModel editingModel;
	private Collection<EEFLockPolicy> lockPolicies;
	private ViewChangeNotifier viewChangeNotifier;
	private List<PropertiesEditingListener> listeners;

	/**
	 * @param bindingSettings
	 *            {@link AbstractEEFBindingSettings} providing this component.
	 * @param source
	 *            the edited {@link EObject}
	 */
	public PropertiesEditingComponentImpl(EEFBindingSettings<PropertiesEditingModel> bindingSettings, EObject source) {
		super(bindingSettings, source);
		this.listeners = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		if (editingModel == null && bindingSettings != null) {
			editingModel = bindingSettings.getEEFDescription(source);
		}
		return editingModel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBinding()
	 * @processing
	 */
	public EClassBinding getBinding() {
		PropertiesEditingModel editingModel = getEditingModel();
		if (editingModel != null) {
			return editingModel.binding(source);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewDescriptors()
	 * @processing
	 */
	public List<View> getViewDescriptors() {
		PropertiesEditingModel editingModel = getEditingModel();
		if (editingModel != null) {
			return editingModel.views(source);
		}
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#addEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 * @state
	 */
	public void addEditingListener(PropertiesEditingListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 * @state
	 */
	public void removeEditingListener(PropertiesEditingListener listener) {
		listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getLockPolicies()
	 */
	public Collection<EEFLockPolicy> getLockPolicies() {
		return lockPolicies;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setLockPolicies(java.util.Collection)
	 */
	public void setLockPolicies(Collection<EEFLockPolicy> lockPolicies) {
		this.lockPolicies = lockPolicies;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#isAffectingEvent(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean isAffectingEvent(Notification notification) {
		if (notification.getNotifier() == source) {
			return true;
		} else {
			if (notification.getFeature() != null && bindingSettings != null) {
				EClassBinding binding = getBindingSettings().getEEFDescription(source).binding(source);
				EEFEditingServiceProvider eefEditingServiceProvider = getEditingContext().getEEFEditingServiceProvider();
				EEFEditingService editingService = eefEditingServiceProvider.getEditingService(getEditingContext().getEditingComponent().getEObject());
				return editingService.isAffectingEventDueToCustomization(getEditingContext(), notification);
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		if (bindingSettings != null) {
			return bindingSettings.enableLockPolicy(lockPolicy);
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewChangeNotifier()
	 * @state
	 */
	public ViewChangeNotifier getViewChangeNotifier() {
		if (viewChangeNotifier == null) {
			viewChangeNotifier = new ViewChangeNotifier(editingContext.getBindingManagerProvider(), this);
		}
		return viewChangeNotifier;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * @state
	 */
	public void propagateEvent(PropertiesEditingEvent event) {
		event.addHolder(this);
		for (PropertiesEditingListener listener : listeners) {
			if (!event.hold(listener))
				listener.firePropertiesChanged(event);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.internal.binding.AbstractPropertiesEditingComponent#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		editingModel = null;
		viewChangeNotifier = null;
		listeners.clear();
	}

}
