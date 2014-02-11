///**
// * 
// */
//package org.eclipse.emf.example.eef.application.services.policies;
//
//import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
//import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
//import org.eclipse.emf.eef.runtime.internal.context.SemanticDomainPropertiesEditingContext;
//import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
//import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider;
//import org.eclipse.emf.eef.runtime.util.EEFEditingService;
//import org.eclipse.emf.eef.runtime.util.EEFEditingServiceProvider;
//import org.eclipse.emf.example.eef.application.policies.E4AppLiveEditingPolicy;
//import org.eclipse.emf.samples.conference.SessionSchedule;
//
///**
// * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
// *
// */
//public class E4AppPropertiesEditingPolicyProvider extends AbstractEEFService<PropertiesEditingContext> implements PropertiesEditingPolicyProvider {
//
//	private EEFEditingServiceProvider editingServiceProvider;
//	
//	/**
//	 * @param editingServiceProvider the editingServiceProvider to set
//	 */
//	public void setEEFEditingServiceProvider(EEFEditingServiceProvider editingServiceProvider) {
//		this.editingServiceProvider = editingServiceProvider;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
//	 */
//	public boolean serviceFor(PropertiesEditingContext element) {
//		if (element instanceof SemanticPropertiesEditingContext) {
//			EEFEditingService editingService = editingServiceProvider.getEditingService(element.getEditingComponent().getEObject());
//			return !editingService.isAddingInContainmentEvent(element, ((SemanticPropertiesEditingContext) element).getEditingEvent()) 
//					&& element instanceof SemanticDomainPropertiesEditingContext
//					&& ((SemanticDomainPropertiesEditingContext) element).getOptions().liveMode()
//					&& !(((SemanticDomainPropertiesEditingContext) element).getEditingEvent().getNewValue() instanceof SessionSchedule);
//		}
//		return false;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicyProvider#getEditingPolicy(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext)
//	 */
//	public PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context) {
//		return createEObjectLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
//	}
//
//	/**
//	 * @param context
//	 * @return
//	 */
//	public PropertiesEditingPolicy createEObjectLiveEditingPolicy(PropertiesEditingContext context) {
//		return new E4AppLiveEditingPolicy((SemanticDomainPropertiesEditingContext) context);
//	}
//
//}