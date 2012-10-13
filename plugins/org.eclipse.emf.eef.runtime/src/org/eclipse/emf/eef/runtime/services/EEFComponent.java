/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import java.util.Collection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public interface EEFComponent {
	
	void setComponentRegistry(EEFComponentRegistry componentRegistry);
	
	EEFComponentRegistry getComponentRegistry();
	
	/**
	 * @return a collection of {@link Class} describing all the services provided by this component.  
	 */
	Collection<String> providedServices();

}
