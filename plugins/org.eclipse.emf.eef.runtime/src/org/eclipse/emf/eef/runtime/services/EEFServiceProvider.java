/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.services;

import java.util.Map;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFServiceProvider<ELEMENT_TYPE, SERVICE_TYPE extends EEFService<ELEMENT_TYPE>> {
	
	/**
	 * Adds a new service to the provider.
	 * @param service the service to add.
	 * @param properties OSGi properties of the added services. 
	 * @throws PriorityCircularityException trying to add a service introducing a cycle in the service order.
	 */
	void addService(SERVICE_TYPE service, Map<String, ?> properties) throws PriorityCircularityException;

	/**
	 * Removes a service from the provider.
	 * @param service the service to remove.
	 */
	void removeService(SERVICE_TYPE service);
	
	/**
	 * Returns the most appropriate service for the given element.
	 * The choice of the service is done according to the following algorithm:
	 * <ul>
	 * 		<li>Filtering services able to handle the given element</li>
 	 *		<li>If the filtering result is composed of several services,</li>
	 * 			<ul>
	 * 					<li>The method returns the one with the highest priority</li>
     *      			<li>If several services have the same priority, the method returns the first service registered</li>
     * 			</ul>
     * 		<li>If the filtering result is composed of only one service, the method returns this service,</li>
     * 		<li>If the filtering result is empty</li>
     * 			<ul>
     * 				<li>If there is a default service registered, the method returns this service</li>
     * 				<li>The method return <code>null</code> if there is no default service registered</li>
     * 			</ul>
     * </ul>
	 * @param element the element to process with the service.
	 * @return the most appropriated {@link EEFService} following the algorithm described in the documentation.
	 */
	<ANY_SUBTYPE_OF_ELEMENT extends ELEMENT_TYPE> SERVICE_TYPE getService(ANY_SUBTYPE_OF_ELEMENT element);
}
