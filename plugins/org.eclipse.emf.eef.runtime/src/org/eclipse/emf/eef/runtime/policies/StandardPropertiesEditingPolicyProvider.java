/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.services.DefaultService;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class StandardPropertiesEditingPolicyProvider extends AbstractEEFService<EObject> implements PropertiesEditingPolicyProvider, DefaultService {

	private PropertiesEditingPolicy nullEditingPolicy;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
		if (context instanceof SemanticPropertiesEditingContext) {
			PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContext) context).getEditingEvent();
			EStructuralFeature feature = context.getEditingComponent().getBinding().feature(editingEvent.getAffectedEditor(), context.getOptions().autowire());
			if (isAddingInContainmentEvent(editingEvent, feature)) {
				if (context instanceof SemanticDomainPropertiesEditingContext) {
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().liveMode()) {
						return createEReferenceLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					} 
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().batchMode()) {
						return createEReferenceBatchEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					}				
				} else {
					return createEReferenceDirectEditingPolicy((SemanticPropertiesEditingContext) context);
				}
			} else {
				if (context instanceof SemanticDomainPropertiesEditingContext) {
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().liveMode()) {
						return createEObjectLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					} 
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().batchMode()) {
						return createEObjectBatchEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					}				
				} else {
					return createEObjectDirectEditingPolicy((SemanticPropertiesEditingContext) context);
				}
			}
		}
		return getNullEditingPolicy();
	}

	private boolean isAddingInContainmentEvent(PropertiesEditingEvent editingEvent, EStructuralFeature feature) {
		return feature != null && feature instanceof EReference && ((EReference)feature).isContainment() && editingEvent.getNewValue() == null && editingEvent.getEventType() == PropertiesEditingEvent.ADD;
	}

	private PropertiesEditingPolicy getNullEditingPolicy() {
		if (nullEditingPolicy == null) {
			nullEditingPolicy = new NullEditingPolicy();
		}
		return nullEditingPolicy;
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectDirectEditingPolicy(PropertiesEditingContext context) {
		return new EObjectDirectEditingPolicy((SemanticPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectBatchEditingPolicy(PropertiesEditingContext context) {
		return new EObjectBatchEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectLiveEditingPolicy(PropertiesEditingContext context) {
		return new EObjectLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceDirectEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceDirectEditingPolicy((SemanticPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceBatchEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceBatchEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceLiveEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

}
