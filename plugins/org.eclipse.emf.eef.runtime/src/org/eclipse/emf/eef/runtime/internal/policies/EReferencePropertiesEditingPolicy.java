/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
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
		EObject createdEObject = defineEObjectToSet(getEditedReference());
		if (createdEObject != null) {
			setToReference(createdEObject);
		}
	}

	/**
	 * @return the edited reference via the {@link PropertiesEditingEvent}.
	 */
	public final EReference getEditedReference() {
		EStructuralFeature feature = context.getEditingComponent().getBinding().feature(context.getEditingEvent().getAffectedEditor(), context.getOptions().autowire());
		EMFService service = context.getServiceRegistry().getService(EMFService.class, context.getEditingComponent().getEObject().eClass().getEPackage());
		return (EReference) service.mapFeature(context.getEditingComponent().getEObject(), feature);
	}

	/**
	 * @param editedReference {@link EReference} to edit.
	 * @return the {@link EObject} to set in the given {@link EReference}.
	 */
	protected EObject defineEObjectToSet(EReference editedReference) {
		EObject createdEObject = null;
		if (editedReference.getEReferenceType() != null && !editedReference.getEReferenceType().isAbstract()) {
			createdEObject  = EcoreUtil.create(editedReference.getEReferenceType());
		} else {
			EMFService emfService = context.getEMFService();
			Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, context.getEditingComponent().getEObject(), editedReference);
			if (listOfInstanciableType.size() > 0) {
				createdEObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
			} else {
				//TODO: logging ?
			}
		}
		return createdEObject;
	}

	/**
	 * Sets to the edited {@link EReference} the given newly created {@link EObject}.
	 * @param createdEObject the {@link EObject} to ad to the edited {@link EReference}.
	 */
	@SuppressWarnings("unchecked")
	protected void setToReference(EObject createdEObject) {
		if (getEditedReference().isMany()) {
			((List<EObject>)context.getEditingComponent().getEObject().eGet(getEditedReference())).add(createdEObject);
		} else {
			context.getEditingComponent().getEObject().eSet(getEditedReference(), createdEObject);
		}
	}
	
}
