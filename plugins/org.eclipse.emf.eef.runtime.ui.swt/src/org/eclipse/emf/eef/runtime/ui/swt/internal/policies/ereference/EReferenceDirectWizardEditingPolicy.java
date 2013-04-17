/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.processors.DirectEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceDirectWizardEditingPolicy extends EReferenceWizardEditingPolicy {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new DirectEditingPolicyProcessor();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference.EReferenceWizardEditingPolicy#attachToResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void attachToResource(PropertiesEditingContext editingContext, Resource resource, EObject createdEObject) {
		resource.getContents().equals(createdEObject);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.ereference.EReferenceWizardEditingPolicy#detachFromResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void detachFromResource(PropertiesEditingContext editingContext, EObject eObject) {
		if (eObject.eResource() != null) {
			Resource objectResource = eObject.eResource();
			if (objectResource.getContents().contains(eObject)) {
				objectResource.getContents().remove(eObject);
			}
		}
	}

}
