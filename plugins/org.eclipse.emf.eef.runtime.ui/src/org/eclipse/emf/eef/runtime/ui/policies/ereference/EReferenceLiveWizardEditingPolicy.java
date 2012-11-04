/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.policies.ereference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessor;
import org.eclipse.emf.eef.runtime.policies.processors.LiveEditingPolicyProcessor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceLiveWizardEditingPolicy extends EReferenceDomainWizardEditingPolicy {

	/**
	 * @param context
	 */
	public EReferenceLiveWizardEditingPolicy(SemanticDomainPropertiesEditingContext context) {
		super(context);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyWithProcessor#getProcessor()
	 */
	public EditingPolicyProcessor getProcessor() {
		return new LiveEditingPolicyProcessor(getEditingContext());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.policies.ereference.EReferenceWizardEditingPolicy#attachToResource(org.eclipse.emf.ecore.resource.Resource, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void attachToResource(Resource resource, EObject createdEObject) {
		resource.getContents().add(createdEObject);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.policies.ereference.EReferenceWizardEditingPolicy#detachFromResource(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void detachFromResource(EObject eObject) {
		if (eObject.eResource() != null) {
			Resource objectResource = eObject.eResource();
			if (objectResource.getContents().contains(eObject)) {
				objectResource.getContents().remove(eObject);
			}
		}
	}

}
