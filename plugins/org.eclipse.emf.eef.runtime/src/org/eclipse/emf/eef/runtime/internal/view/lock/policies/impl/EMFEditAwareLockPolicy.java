/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.lock.policies.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFEditAwareLockPolicy implements EEFLockPolicy {
	
	private PropertiesEditingContext editingContext;
	
	/**
	 * @param editingContext the {@link PropertiesEditingContext} owning this policy.
	 */
	public EMFEditAwareLockPolicy(PropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.ecore.EObject)
	 */
	public boolean isLocked(EObject object) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#isLocked(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public boolean isLocked(EObject object, EStructuralFeature feature) {
		if (feature != null) {
			IItemPropertySource propertySource = (IItemPropertySource) editingContext.getAdapterFactory().adapt(object, IItemPropertySource.class);
			if (propertySource != null) {
				return !propertySource.getPropertyDescriptor(object, feature).canSetProperty(object);
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#setRegistry(org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicyRegistry)
	 */
	public void setRegistry(EEFLockPolicyRegistry registry) {
		// Nothing to do: this policy doesn't send lock event.
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy#dispose()
	 */
	public void dispose() {
		// Nothing to do: this policy doesn't send lock event.		
	}

}
