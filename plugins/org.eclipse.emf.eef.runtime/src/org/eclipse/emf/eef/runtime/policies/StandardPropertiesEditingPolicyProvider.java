/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContextImpl;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.eobject.EObjectLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceBatchEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceDirectEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.ereference.EReferenceLiveEditingPolicy;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class StandardPropertiesEditingPolicyProvider extends AbstractEEFService<PropertiesEditingContext> implements PropertiesEditingPolicyProvider, DefaultService {

	private PropertiesEditingPolicy nullEditingPolicy;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
		if (context instanceof SemanticPropertiesEditingContextImpl) {
			PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContextImpl) context).getEditingEvent();
			EEFEditingService editingService = getServiceRegistry().getService(EEFEditingService.class, context.getEditingComponent().getEObject());
			if (editingService.isAddingInContainmentEvent(context, editingEvent)) {
				if (context instanceof SemanticDomainPropertiesEditingContext) {
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().liveMode()) {
						return createEReferenceLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					} 
					if (((SemanticDomainPropertiesEditingContext) context).getOptions().batchMode()) {
						return createEReferenceBatchEditingPolicy((SemanticDomainPropertiesEditingContext) context);
					}				
				} else {
					return createEReferenceDirectEditingPolicy((SemanticPropertiesEditingContextImpl) context);
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
					return createEObjectDirectEditingPolicy((SemanticPropertiesEditingContextImpl) context);
				}
			}
		}
		return getNullEditingPolicy();
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
		return new EObjectDirectEditingPolicy();
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectBatchEditingPolicy(PropertiesEditingContext context) {
		return new EObjectBatchEditingPolicy();
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectLiveEditingPolicy(PropertiesEditingContext context) {
		return new EObjectLiveEditingPolicy();
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceDirectEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceDirectEditingPolicy();
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceBatchEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceBatchEditingPolicy();
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceLiveEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceLiveEditingPolicy();
	}

}
