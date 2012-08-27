/**
 * 
 */
package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertiesEditingProviderRegistry {
	
	/**
	 * @param ePackage
	 * @return
	 */
	PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage);

	/**
	 * Component activation method.
	 * @param context the Component context.
	 */
	void activate(ComponentContext context);
	
	/**
	 * Defines the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to use.
	 */
	void setEMFServiceProvider(EMFServiceProvider emfServiceProvider);

	/**
	 * Unsets the {@link EMFServiceProvider} to use in the current {@link PropertiesEditingProviderRegistry}.
	 * @param emfServiceProvider the {@link EMFServiceProvider} to use.
	 */
	void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider);

}
