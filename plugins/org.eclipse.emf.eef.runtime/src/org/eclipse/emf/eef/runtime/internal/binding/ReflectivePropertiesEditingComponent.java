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
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.eef.runtime.binding.settings.EEFBindingSettings;
import org.eclipse.emf.eef.runtime.editingModel.EClassBinding;
import org.eclipse.emf.eef.runtime.editingModel.EditingModelFactory;
import org.eclipse.emf.eef.runtime.editingModel.PropertiesEditingModel;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener;
import org.eclipse.emf.eef.runtime.notify.ViewChangeNotifier;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 * 
 */
public class ReflectivePropertiesEditingComponent<T extends EObject> extends AbstractPropertiesEditingComponent<T> {

	private List<View> descriptors;
	private EClassBinding binding;

	/**
	 * @param bindingSettings
	 * @param source
	 */
	public ReflectivePropertiesEditingComponent(EEFBindingSettings<T> bindingSettings, EObject source) {
		super(bindingSettings, source);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getEditingModel()
	 */
	public PropertiesEditingModel getEditingModel() {
		// TODO Better null return ?
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getBinding()
	 */
	public EClassBinding getBinding() {
		if (binding == null) {
			if (bindingSettings.getEEFDescription(source) instanceof View) {
				binding = EditingModelFactory.eINSTANCE.createEClassBinding();
				EAnnotation eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
				eAnnotation.setSource(EEF_EANNOTATION_SOURCE);
				eAnnotation.getDetails().put(BINDING_KIND_KIND, REFLECTIVE_BINDING_KIND);
				binding.getEAnnotations().add(eAnnotation);
				binding.getViews().add((View) bindingSettings.getEEFDescription(source));
			} else if (bindingSettings.getEEFDescription(source) instanceof EClassBinding) {
				binding = (EClassBinding) source;
			} else if (bindingSettings.getEEFDescription(source) instanceof PropertyBinding && bindingSettings.getEEFDescription(source).eContainer() instanceof EClassBinding) {
				binding = (EClassBinding) source.eContainer();
			}
		}
		return binding;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewDescriptors()
	 */
	public List<View> getViewDescriptors() {
		if (descriptors == null) {
			descriptors = Lists.newArrayList();
			if (bindingSettings.getEEFDescription(source) instanceof View) {
				descriptors.add((View) bindingSettings.getEEFDescription(source));
			} else if (bindingSettings.getEEFDescription(source) instanceof EClassBinding) {
				descriptors.addAll(((EClassBinding) bindingSettings.getEEFDescription(source)).getViews());
			} else if (bindingSettings.getEEFDescription(source) instanceof PropertyBinding && bindingSettings.getEEFDescription(source).eContainer() instanceof EClassBinding) {
				descriptors.addAll(((EClassBinding) bindingSettings.getEEFDescription(source).eContainer()).getViews());
			}
		}
		return descriptors;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#addEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void addEditingListener(PropertiesEditingListener listener) {
		// Nothing to do.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#removeEditingListener(org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener)
	 */
	public void removeEditingListener(PropertiesEditingListener listener) {
		// Nothing to do.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getLockPolicies()
	 */
	public Collection<EEFLockPolicy> getLockPolicies() {
		return Collections.emptyList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#setLockPolicies(java.util.Collection)
	 */
	public void setLockPolicies(Collection<EEFLockPolicy> lockPolicies) {
		// Nothing to do.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#enableLockPolicy(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy)
	 */
	public boolean enableLockPolicy(EEFLockPolicy lockPolicy) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#isAffectingEvent(org.eclipse.emf.common.notify.Notification)
	 */
	public boolean isAffectingEvent(Notification notification) {
		return isConcerningNotifier(notification.getNotifier());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#getViewChangeNotifier()
	 */
	public ViewChangeNotifier getViewChangeNotifier() {
		return new ViewChangeNotifier(editingContext.getBindingManagerProvider(), this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#propagateEvent(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public void propagateEvent(PropertiesEditingEvent event) {
		// Nothing to do.
	}

	// If the modified element is the displayed view or an element contained in
	// the view, then I need to be notified.
	private boolean isConcerningNotifier(Object notifier) {
		if (notifier == getEObject()) {
			return true;
		} else {
			if (notifier instanceof EObject) {
				EObject parent = ((EObject) notifier).eContainer();
				while (parent != null) {
					if (parent == getEObject()) {
						return true;
					}
					parent = parent.eContainer();
				}
			}
		}
		return false;
	}

}
