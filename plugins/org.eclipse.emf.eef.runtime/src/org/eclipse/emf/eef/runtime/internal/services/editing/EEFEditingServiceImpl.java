/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.editing;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFEditingServiceImpl implements EEFEditingService, DefaultService {

	private EMFServiceProvider emfServiceProvider;
	
	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editing.EEFEditingService#isAddingInContainmentEvent(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public boolean isAddingInContainmentEvent(PropertiesEditingContext context, PropertiesEditingEvent editingEvent) {
		EStructuralFeature feature = context.getEditingComponent().getBinding().feature(editingEvent.getAffectedEditor(), context.getOptions().autowire());
		return feature != null && feature instanceof EReference && ((EReference)feature).isContainment() && editingEvent.getNewValue() == null && editingEvent.getEventType() == PropertiesEditingEvent.ADD;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.editing.EEFEditingService#getReferenceToEdit(org.org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	public EReference getReferenceToEdit(SemanticPropertiesEditingContext editingContext) {
		EStructuralFeature feature = editingContext.getEditingComponent().getBinding().feature(editingContext.getEditingEvent().getAffectedEditor(), editingContext.getOptions().autowire());
		EMFService service = emfServiceProvider.getEMFService(editingContext.getEditingComponent().getEObject().eClass().getEPackage());
		return (EReference) service.mapFeature(editingContext.getEditingComponent().getEObject(), feature);
	}

}
