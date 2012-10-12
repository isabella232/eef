/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.emf;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class EMFServiceRegistry extends EEFComponentRegistry implements EMFServiceProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider#getEMFServiceForPackage(org.eclipse.emf.ecore.EPackage)
	 */
	public EMFService getEMFServiceForPackage(EPackage ePackage) {
		return (EMFService) getHighestProvider(EMFService.class, ePackage);
	}
	
	
}
