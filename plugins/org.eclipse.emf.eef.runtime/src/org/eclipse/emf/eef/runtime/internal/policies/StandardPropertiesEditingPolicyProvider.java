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
			if (feature != null && feature instanceof EReference && ((EReference)feature).isContainment() && editingEvent.getNewValue() == null) {
				return new EReferencePropertiesEditingPolicy((SemanticPropertiesEditingContext) context);
			} else {
				if (context instanceof SemanticDomainPropertiesEditingContext) {
					SemanticDomainPropertiesEditingContext semanticEditingContext = (SemanticDomainPropertiesEditingContext) context;
					if (semanticEditingContext.getOptions().liveMode()) {
						return new LiveEObjectEditingPolicy(semanticEditingContext, semanticEditingContext.getEditingEvent());
					} 
					if (semanticEditingContext.getOptions().batchMode()) {
						return new BatchEObjectEditingPolicy(semanticEditingContext, semanticEditingContext.getEditingEvent());
					}				
				} else {
					return new EObjectDirectEditingPolicy(context, editingEvent);
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

}
