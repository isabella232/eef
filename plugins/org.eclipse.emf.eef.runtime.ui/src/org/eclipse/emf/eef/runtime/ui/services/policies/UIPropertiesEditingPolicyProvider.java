/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.services.policies;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.internal.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
import org.eclipse.emf.eef.runtime.services.editing.EEFEditingService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.eclipse.emf.eef.runtime.ui.internal.policies.eobject.EObjectBatchWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.eobject.EObjectLiveWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceBatchWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceDirectWizardEditingPolicy;
import org.eclipse.emf.eef.runtime.ui.internal.policies.ereference.EReferenceLiveWizardEditingPolicy;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UIPropertiesEditingPolicyProvider extends AbstractEEFService<PropertiesEditingContext> implements PropertiesEditingPolicyProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(PropertiesEditingContext element) {
		if (element instanceof SemanticPropertiesEditingContext) {
			EEFEditingService editingService = getServiceRegistry().getService(EEFEditingService.class, element.getEditingComponent().getEObject());
			return editingService.isAddingInContainmentEvent(element, ((SemanticPropertiesEditingContext) element).getEditingEvent()) || element instanceof SemanticDomainPropertiesEditingContext;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
	 */
	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
		PropertiesEditingEvent editingEvent = ((SemanticPropertiesEditingContext) context).getEditingEvent();
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
			}
		}
		return null;
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectBatchEditingPolicy(PropertiesEditingContext context) {
		return new EObjectBatchWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEObjectLiveEditingPolicy(PropertiesEditingContext context) {
		return new EObjectLiveWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceDirectEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceDirectWizardEditingPolicy((SemanticPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceBatchEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceBatchWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}

	/**
	 * @param context
	 * @return
	 */
	public PropertiesEditingPolicy createEReferenceLiveEditingPolicy(PropertiesEditingContext context) {
		return new EReferenceLiveWizardEditingPolicy((SemanticDomainPropertiesEditingContext) context);
	}


}