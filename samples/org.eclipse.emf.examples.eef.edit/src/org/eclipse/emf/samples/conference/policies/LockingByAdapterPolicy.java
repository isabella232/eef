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
package org.eclipse.emf.samples.conference.policies;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.impl.EEFLockEventImpl;
import org.eclipse.emf.eef.runtime.view.lock.policies.impl.EEFPropertyLockEventImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LockingByAdapterPolicy implements EEFLockPolicy {
	
	private LockingByAdapterPolicyFactory policyFactory;
	
	/**
	 * @param policyFactory
	 */
	public LockingByAdapterPolicy(LockingByAdapterPolicyFactory policyFactory) {
		this.policyFactory = policyFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object) {
		LockAdapter existingAdapter = (LockAdapter) EcoreUtil.getExistingAdapter(object, LockAdapter.class);
		if (existingAdapter == null) {
			existingAdapter = new LockAdapter(policyFactory, editingContext.getEditingComponent());
			object.eAdapters().add(existingAdapter);
		} else {
			if (existingAdapter.editingComponent.getEditingContext() == null) {
				existingAdapter.editingComponent = editingContext.getEditingComponent();
			}
		}
		return existingAdapter.isLocked();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean isLocked(PropertiesEditingContext editingContext, EObject object, EStructuralFeature feature) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#dispose()
	 */
	public void dispose() {
	}

	public static final class LockAdapter extends AdapterImpl {

		private LockingByAdapterPolicyFactory policyFactory;
		private PropertiesEditingComponent editingComponent;
		private boolean locked = false;
		private Collection<EStructuralFeature> lockedFeatures;
		
		public LockAdapter(LockingByAdapterPolicyFactory policyFactory, PropertiesEditingComponent editingComponent) {
			this.policyFactory = policyFactory;
			this.editingComponent = editingComponent;
			lockedFeatures = new ArrayList<EStructuralFeature>();
		}

		/**
		 * {@inheritDoc}
		 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
		 */
		public boolean isAdapterForType(Object type) {
			return type == LockAdapter.class || super.isAdapterForType(type);
		}

		/**
		 * @return the locked
		 */
		public boolean isLocked() {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
				return false;
			} else {
				return locked;
			}
		}
		
		/**
		 * @return the locked
		 */
		public boolean isPropertyLocked(EStructuralFeature feature) {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
				return false;
			} else {
				return lockedFeatures.contains(feature);
			}
		}
		
		/**
		 * @param locked the locked to set
		 */
		public void setLocked(boolean locked) {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
			} else {
				this.locked = locked;
				policyFactory.fireLockChanged(editingComponent, new EEFLockEventImpl((EObject) getTarget(), this.locked?EEFLockEvent.LockState.LOCKED:EEFLockEvent.LockState.UNLOCKED));
			}
		}
		
		/**
		 * @param feature
		 * @param locked
		 */
		public void setFeatureLocked(EStructuralFeature feature, boolean locked) {
			if (editingComponent.getEditingContext() == null) {
				this.getTarget().eAdapters().remove(this);
			} else {
				if (locked) {
					lockedFeatures.add(feature);
					policyFactory.fireLockChanged(editingComponent, new EEFPropertyLockEventImpl((EObject) getTarget(), feature, EEFLockEvent.LockState.LOCKED));
				} else {
					lockedFeatures.remove(feature);
					policyFactory.fireLockChanged(editingComponent, new EEFPropertyLockEventImpl((EObject) getTarget(), feature, EEFLockEvent.LockState.UNLOCKED));
				}
			}
		}
		
	}

}
