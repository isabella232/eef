/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import org.eclipse.emf.ecore.EPackage;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EPackageService {

	/**
	 * Specifies (via the {@link EPackage}) metamodels on which apply the service.
	 * @return the {@link EPackage} on which apply this service. Can be 
	 * {@link EPackageRegistryService#defaultPackage} to specify that it is a generic service.
	 * 
	 */
	EPackage serviceForPackage();
}
