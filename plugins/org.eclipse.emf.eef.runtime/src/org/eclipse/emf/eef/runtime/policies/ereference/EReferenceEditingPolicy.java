/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies.ereference;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.AbstractEditingPolicyWithProcessor;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyProcessing.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class EReferenceEditingPolicy extends AbstractEditingPolicyWithProcessor {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.AbstractEditingPolicyWithProcessor#getPolicyProcessing()
	 */
	@Override
	protected EditingPolicyProcessing getPolicyProcessing(PropertiesEditingContext editingContext) {
		EditingPolicyProcessing processing = new EditingPolicyProcessing();
		processing.target = editingContext.getEditingComponent().getEObject();
		processing.feature = getEditedReference((SemanticPropertiesEditingContext) editingContext);
		processing.value = defineEObjectToSet(editingContext, (EReference) processing.feature);
		if (processing.feature.isMany()) {
			processing.processingKind = ProcessingKind.ADD;
		} else {
			processing.processingKind = ProcessingKind.SET;
		}
		return processing;
	}

	/**
	 * @return the edited reference via the {@link PropertiesEditingEvent}.
	 */
	public final EReference getEditedReference(SemanticPropertiesEditingContext editingContext) {
		return editingContext.getServiceRegistry().getService(EEFEditingService.class, editingContext.getEditingComponent().getEObject()).getReferenceToEdit(editingContext);
	}

	/**
	 * @param editedReference {@link EReference} to edit.
	 * @return the {@link EObject} to set in thce given {@link EReference}.
	 */
	protected EObject defineEObjectToSet(PropertiesEditingContext editingContext, EReference editedReference) {
		EObject createdEObject = null;
		if (editedReference.getEReferenceType() != null && !editedReference.getEReferenceType().isAbstract()) {
			createdEObject  = EcoreUtil.create(editedReference.getEReferenceType());
		} else {
			EMFService emfService = editingContext.getEMFServiceProvider().getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
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
