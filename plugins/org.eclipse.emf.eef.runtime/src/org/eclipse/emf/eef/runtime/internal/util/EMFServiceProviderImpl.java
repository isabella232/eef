/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.util;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFServiceProviderImpl extends EEFServiceProviderImpl<EPackage, EMFService> implements EMFServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFServiceProvider#getEMFService(org.eclipse.emf.ecore.EPackage)
	 */
	public EMFService getEMFService(EPackage ePackage) {
		return getService(ePackage);
	}

}
