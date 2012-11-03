/**
 * 
 */
package org.eclipse.emf.eef.runtime.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingPolicyProvider extends EEFService<EObject> {

	/**
	 * @param context
	 * @return
	 */
	PropertiesEditingPolicy getEditingPolicy(PropertiesEditingContext context);
	
//	/**
//	 * Provides an element focused {@link PropertiesEditingPolicy} towards the given {@link PropertiesEditingContext}.
//	 * @param context {@link PropertiesEditingContext} to process.
//	 * @return the {@link PropertiesEditingPolicy} in this context.
//	 */
//	PropertiesEditingPolicy provideEObjectEditingPolicy(PropertiesEditingContext context);
//	
//	/**
//	 * Provides a reference focused {@link PropertiesEditingPolicy} towards the given {@link PropertiesEditingContext}.
//	 * @param context {@link PropertiesEditingContext} to process.
//	 * @return the {@link PropertiesEditingPolicy} in this context.
//	 */
//	PropertiesEditingPolicy provideEReferenceEditingPolicy(PropertiesEditingContext context);
//	
//	/**
//	 * Provides an {@link EditingDomin} aware {@link PropertiesEditingPolicy} towards the given {@link PropertiesEditingContext}.
//	 * @param domain {@link EditingDomain} to process.
//	 * @param context {@link PropertiesEditingContext} to process.
//	 * @return the {@link PropertiesEditingPolicy} in this context.
//	 */
//	PropertiesEditingPolicy provideDomainEditingPolicy(EditingDomain domain, PropertiesEditingContext context);
	
}
