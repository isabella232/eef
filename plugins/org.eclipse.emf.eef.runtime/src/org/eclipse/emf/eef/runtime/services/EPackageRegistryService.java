/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

/**
 * A generic service to use as an OSGi DS. It can be used as an {@link EPackage}
 * specific service registry.
 *
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EPackageRegistryService<SERVICE extends EPackageService> {

	/**
	 * Marker package for representing the default service instance.
	 */
	public static final EPackage defaultPackage;
	
	static {
		defaultPackage = EcoreFactory.eINSTANCE.createEPackage();
		defaultPackage.setNsURI("http://www.eclipse.org/emf/eef/defaultpackage");
	}

	private final Map<EPackage, SERVICE> customServices; 
	private SERVICE defaultService;
	
	/**
	 * Default constructor. 
	 */
	public EPackageRegistryService() {
		customServices = Maps.newHashMap();
	}	

	public synchronized void addService(final SERVICE service) {
		final EPackage serviceForPackage = service.serviceForPackage();
		if (serviceForPackage == defaultPackage) {
			defaultService = service;
		} else {
			Collection<EPackage> filter = Collections2.filter(customServices.keySet(), new EPackageEqualityPredicate(serviceForPackage));
			if (filter.size() > 0) {
				customServices.put(filter.iterator().next(), service);
			} else {
				customServices.put(serviceForPackage, service);
			}
		}
	}

	public synchronized void removeService(final SERVICE service) {
		final EPackage serviceForPackage = service.serviceForPackage();
		if (serviceForPackage == defaultPackage) {
			defaultService = null;
		} else {
			Collection<EPackage> filter = Collections2.filter(customServices.keySet(), new EPackageEqualityPredicate(serviceForPackage));
			if (filter.size() > 0) {
				customServices.remove(filter.iterator().next());
			} else {
				customServices.remove(serviceForPackage);
			}
		}
	}
	
	/**
	 * Returns the service registered for this package. If no service is registered, it returns
	 * the default service.
	 * @param ePackage filtering {@link EPackage}.
	 * @return the service registered for the giver {@link EPackage}.
	 */
	public SERVICE getServiceForPackage(EPackage ePackage) {
		Collection<EPackage> filter = Collections2.filter(customServices.keySet(), new EPackageEqualityPredicate(ePackage));
		if (filter.size() > 0) {
			return customServices.get(filter.iterator().next());
		} else {
			return defaultService;
		}
		
	}
	
	/**
	 * This method compare two {@link EPackage}s to define their equality. This equality is based on the EPackage uris in order to handle
	 * the platform/registry homogeneity.  
	 * @param ePack1 first {@link EPackage} to test.
	 * @param ePack2 second {@link EPackage} to test.
	 * @return <code>true</code> if the two packages are equals.
	 */
	protected boolean ePackagesEquality(final EPackage ePack1, final EPackage ePack2) {
		return (ePack1 == null && ePack2 == null) || ((ePack1 != null && ePack2 != null && ePack1.getNsURI().equals(ePack2.getNsURI())));
	}	

	/**
	 * This class is a Guava {@link Predicate} using the ePackageEquality method to filter list of {@link EPackage}.
	 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
	 *
	 */
	private final class EPackageEqualityPredicate implements Predicate<EPackage> {
		private final EPackage serviceForPackage;

		private EPackageEqualityPredicate(EPackage serviceForPackage) {
			this.serviceForPackage = serviceForPackage;
		}

		/**
		 * {@inheritDoc}
		 * @see com.google.common.base.Predicate#apply(java.lang.Object)
		 */
		public boolean apply(EPackage input) {
			return ePackagesEquality(serviceForPackage, input);
		}
	}
}
