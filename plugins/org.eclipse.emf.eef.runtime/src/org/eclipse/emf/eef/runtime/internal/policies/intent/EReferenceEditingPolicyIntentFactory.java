/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies.intent;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntent.ProcessingKind;
import org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EReferenceEditingPolicyIntentFactory extends AbstractEEFService<PropertiesEditingContext> implements EditingPolicyIntentFactory {

	private EMFServiceProvider emfServiceProvider;
	private EEFEditingServiceProvider eefEditingServiceProvider;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param eefEditingServiceProvider the eefEditingServiceProvider to set
	 */
	public void setEEFEditingServiceProvider(EEFEditingServiceProvider eefEditingServiceProvider) {
		this.eefEditingServiceProvider = eefEditingServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext editingContext) {
		if (editingContext instanceof SemanticPropertiesEditingContext) {
			EEFEditingService editingService = eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject());
			return editingService.isAddingInContainmentEvent(editingContext, ((SemanticPropertiesEditingContext) editingContext).getEditingEvent());
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.EditingPolicyIntentFactory#createProcessing(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public EditingPolicyIntent createProcessing(PropertiesEditingContext editingContext) {
		EditingPolicyIntent processing = new EditingPolicyIntent();
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
		return eefEditingServiceProvider.getEditingService(editingContext.getEditingComponent().getEObject()).getReferenceToEdit(editingContext);
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
			EMFService emfService = emfServiceProvider.getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
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
