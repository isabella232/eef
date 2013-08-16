/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceDirectWizardEditingPolicyRequest extends EReferenceWizardEditingPolicyRequestFactory {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request.EReferenceWizardEditingPolicy#attachToResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void attachToResource(PropertiesEditingContext editingContext, Resource resource, EObject createdEObject) {
		resource.getContents().add(createdEObject);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.policies.request.EReferenceWizardEditingPolicy#detachFromResource(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
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
