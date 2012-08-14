/**
 * 
 */
package org.eclipse.emf.eef.runtime.util.impl;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.services.EPackageRegistryService;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFServiceRegistry extends EPackageRegistryService<EMFService> implements EMFServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFServiceProvider#getEMFServiceForPackage(org.eclipse.emf.ecore.EPackage)
	 */
	public EMFService getEMFServiceForPackage(EPackage ePackage) {
		return getServiceForPackage(ePackage);
	}
	
	
}
