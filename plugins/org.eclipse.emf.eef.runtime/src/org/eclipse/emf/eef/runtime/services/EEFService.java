/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import java.util.Collection;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFService<Element> {

	/**
	 * Specifies that the current service can apply for a given element.
	 * @param element the element to test.
	 * @return <code>true</code> if this service can proces the element.
	 */
	boolean serviceFor(Element element);
	
	/**
	 * Sets the {@link EEFServiceRegistry} referencing the current service.
	 * @param serviceRegistry the referencing service registry
	 */
	void setComponentRegistry(EEFServiceRegistry serviceRegistry);
	
	/**
	 * @return the {@link EEFServiceRegistry} referencing the current service.
	 */
	EEFServiceRegistry getComponentRegistry();
	
	/**
	 * @return a collection of {@link Class} describing all the services provided by this component.  
	 */
	Collection<String> providedServices();
	
}
