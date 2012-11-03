/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferencePropertiesEditingPolicy implements PropertiesEditingPolicy {

	private SemanticPropertiesEditingContext context;
	
	/**
	 * @param context the {@link PropertiesEditingContext} which have generated the current policy.
	 */
	public EReferencePropertiesEditingPolicy(SemanticPropertiesEditingContext context) {
		this.context = context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public void execute() {
		EReference reference = getEditedReference();
		if (reference.getEReferenceType() != null && !reference.getEReferenceType().isAbstract()) {
			EObject createdEObject = EcoreUtil.create(reference.getEReferenceType());
			context.getEditingComponent().getEObject().eSet(reference, createdEObject);
		} else {
			EMFService emfService = context.getEMFService();
			Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, context.getEditingComponent().getEObject(), reference);
			if (listOfInstanciableType.size() > 0) {
				EObject createdEObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
				context.getEditingComponent().getEObject().eSet(reference, createdEObject);
			} else {
				//TODO: logging ?
			}
		}
	}
	
	private EReference getEditedReference() {
		return (EReference) context.getEditingComponent().getBinding().feature(context.getEditingEvent().getAffectedEditor(), context.getOptions().autowire());
	}

}
