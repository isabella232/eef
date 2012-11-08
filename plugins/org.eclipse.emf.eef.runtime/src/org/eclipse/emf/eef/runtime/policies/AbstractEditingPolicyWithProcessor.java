/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEditingPolicyWithProcessor implements EditingPolicyWithProcessor {

	protected SemanticPropertiesEditingContext editingContext;
	
	/**
	 * @param editingContext the {@link SemanticPropertiesEditingContext} to process.
	 */
	public AbstractEditingPolicyWithProcessor(SemanticPropertiesEditingContext editingContext) {
		this.editingContext = editingContext;
	}

	/**
	 * @return the editingContext
	 */
	public PropertiesEditingContext getEditingContext() {
		return editingContext;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#validateEditing()
	 */
	public EditingPolicyValidation validateEditing() {
		PropertiesEditingEvent editingEvent = editingContext.getEditingEvent();
		EObject eObject = editingContext.getEditingComponent().getEObject();
		EStructuralFeature feature = editingContext.getEditingComponent().getBinding().feature(editingEvent.getAffectedEditor(), editingContext.getOptions().autowire());
		if (feature != null) {
			if (!eObject.eClass().getEAllStructuralFeatures().contains(feature)) {
				EMFService emfService = editingContext.getServiceRegistry().getService(EMFService.class, eObject.eClass().getEPackage());
				feature = emfService.mapFeature(eObject, feature);
			}
			if (eObject.eClass().getEAllStructuralFeatures().contains(feature)) {
 				Object currentValue = eObject.eGet(feature);
 				Object newValue = null;
 				if (editingEvent.getNewValue() != null) {
 					if (feature instanceof EAttribute && editingEvent.getNewValue() instanceof String) {
 						try {
 							newValue = EcoreUtil.createFromString(((EAttribute)feature).getEAttributeType(), (String)editingEvent.getNewValue());
 						} catch (Exception e) {
 							//Silent catch
						}
 					}
 					if (newValue == null) {
 						newValue = editingEvent.getNewValue();
 					}
 				}
				return new EditingPolicyValidation(this, (currentValue == null && newValue != null)
						|| (currentValue != null && !currentValue.equals(newValue)));
			}
		}
		return new EditingPolicyValidation(this, false, "The feature doesn't seem to affected the edited element.");
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy#execute()
	 */
	public final void execute() {
		getProcessor().process(getPolicyProcessing());
	}

	/**
	 * @return the {@link EditingPolicyProcessing} to execute by this {@link PropertiesEditingPolicy}.
	 */
	protected abstract EditingPolicyProcessing getPolicyProcessing();

}
