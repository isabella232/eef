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
package org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.editingModel.EStructuralFeatureBinding;
import org.eclipse.emf.eef.runtime.editingModel.PropertyBinding;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFEditAwareLockPolicy implements EEFLockPolicy {
	
	private EMFServiceProvider emfServiceProvider;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject, org.eclipse.emf.eef.runtime.editingModel.PropertyBinding)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object, PropertyBinding propertyBinding) {
		if (propertyBinding instanceof EStructuralFeatureBinding) {
			EStructuralFeature bindedFeature = ((EStructuralFeatureBinding) propertyBinding).getFeature();
			EStructuralFeature feature = emfServiceProvider.getEMFService(object.eClass().getEPackage()).mapFeature(object, bindedFeature);
			if (feature != null) {
				IItemPropertySource propertySource = (IItemPropertySource) editingContext.getAdapterFactory().adapt(object, IItemPropertySource.class);
				if (propertySource != null) {
					return (propertySource.getPropertyDescriptor(object, feature) != null) && (!propertySource.getPropertyDescriptor(object, feature).canSetProperty(object));
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#dispose()
	 */
	public void dispose() {
		// Nothing to do: this policy doesn't send lock event.		
	}

}
