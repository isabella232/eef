/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.policies.eobject.EObjectBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.eobject.EObjectDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.ereference.EReferenceBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.ereference.EReferenceDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.policies.ereference.EReferenceLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.internal.services.DefaultService;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
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
					SemanticDomainPropertiesEditingContext semanticEditingContext = (SemanticDomainPropertiesEditingContext) context;
					if (semanticEditingContext.getOptions().liveMode()) {
						return new EReferenceLiveEditingPolicy(semanticEditingContext);
					} 
					if (semanticEditingContext.getOptions().batchMode()) {
						return new EReferenceBatchEditingPolicy(semanticEditingContext);
					}				
				} else {
					return new EReferenceDirectEditingPolicy((SemanticPropertiesEditingContext) context);
				}
			} else {
				if (context instanceof SemanticDomainPropertiesEditingContext) {
					SemanticDomainPropertiesEditingContext semanticEditingContext = (SemanticDomainPropertiesEditingContext) context;
					if (semanticEditingContext.getOptions().liveMode()) {
						return new EObjectLiveEditingPolicy(semanticEditingContext);
					} 
					if (semanticEditingContext.getOptions().batchMode()) {
						return new EObjectBatchEditingPolicy(semanticEditingContext);
					}				
				} else {
					return new EObjectDirectEditingPolicy((SemanticPropertiesEditingContext) context);
				}
			}
		}
		return getNullEditingPolicy();
	}

	/**
	 * @param editingEvent
	 * @param feature
	 * @return
	 */
	public boolean isAddingInContainmentEvent(PropertiesEditingEvent editingEvent, EStructuralFeature feature) {
		return feature != null && feature instanceof EReference && ((EReference)feature).isContainment() && editingEvent.getNewValue() == null && editingEvent.getEventType() == PropertiesEditingEvent.ADD;
	}

	private PropertiesEditingPolicy getNullEditingPolicy() {
		if (nullEditingPolicy == null) {
			nullEditingPolicy = new NullEditingPolicy();
		}
		return nullEditingPolicy;
	}

}
