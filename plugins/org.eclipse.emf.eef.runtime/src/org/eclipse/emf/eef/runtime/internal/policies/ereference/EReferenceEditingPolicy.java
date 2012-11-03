/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.ereference;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.AbstractEditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EReferenceEditingPolicy extends AbstractEditingPolicyWithProcessor {

	private SemanticPropertiesEditingContext editingContext;
	
	/**
	 * @param context the {@link PropertiesEditingContext} which have generated the current policy.
	 */
	public EReferenceEditingPolicy(SemanticPropertiesEditingContext context) {
		this.editingContext = context;
	}

	/**
	 * @return the {@link PropertiesEditingContext} of this {@link PropertiesEditingPolicy}.
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.internal.policies.AbstractEditingPolicyWithProcessor#getPolicyProcessing()
	 */
	@Override
	protected EditingPolicyProcessing getPolicyProcessing() {
		EditingPolicyProcessing processing = new EditingPolicyProcessing();
		processing.target = editingContext.getEditingComponent().getEObject();
		processing.feature = getEditedReference();
		processing.value = defineEObjectToSet(getEditedReference());
		if (getEditedReference().isMany()) {
			processing.processingKind = ProcessingKind.ADD;
		} else {
			processing.processingKind = ProcessingKind.SET;
		}
		return processing;
	}

	/**
	 * @return the edited reference via the {@link PropertiesEditingEvent}.
	 */
	public final EReference getEditedReference() {
		EStructuralFeature feature = editingContext.getEditingComponent().getBinding().feature(editingContext.getEditingEvent().getAffectedEditor(), editingContext.getOptions().autowire());
		EMFService service = editingContext.getServiceRegistry().getService(EMFService.class, editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		return (EReference) service.mapFeature(editingContext.getEditingComponent().getEObject(), feature);
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
			EMFService emfService = editingContext.getEMFService();
			Collection<EClass> listOfInstanciableType = emfService.listOfInstanciableType(null, editingContext.getEditingComponent().getEObject(), editedReference);
			if (listOfInstanciableType.size() > 0) {
				createdEObject = EcoreUtil.create(listOfInstanciableType.iterator().next());
			} else {
				//TODO: logging ?
			}
		}
		return createdEObject;
	}

}
