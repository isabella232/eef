/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.internal.services.DefaultService;

import com.google.common.collect.Lists;

/**
 * A generic service to use as an OSGi DS. It can be used as an {@link EPackage}
 * specific service registry.
 *
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFServiceRegistry<ELEMENT, SERVICE extends EEFService<ELEMENT>> {

	protected final Collection<SERVICE> customServices; 
	protected SERVICE defaultService;
	
	/**
	 * Default constructor. 
	 */
	public EEFServiceRegistry() {
		customServices = Lists.newArrayList();
	}	

	public synchronized void addService(final SERVICE service) {
		if (service instanceof DefaultService) {
			defaultService = service;
		} else {
			customServices.add(service);
		}
	}

	public synchronized void removeService(final SERVICE service) {
		if (service instanceof DefaultService) {
			defaultService = null;
		} else {
			customServices.remove(service);
		}
	}
	
	/**
	 * Returns the service registered for this package. If no service is registered, it returns
	 * the default service.
	 * @param ePackage filtering {@link EPackage}.
	 * @return the service registered for the giver {@link EPackage}.
	 */
	public SERVICE getServiceForElement(ELEMENT element) {
		for (SERVICE service : customServices) {
			if (service.serviceFor(element)) {
				return service;
			}
		}
		return defaultService;
	}
	
}
