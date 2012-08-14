/**
 * 
 */
package org.eclipse.emf.eef.runtime.util;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.util.impl.EMFServiceImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EMFServiceProvider {
	
	/**
	 * Returns the {@link EMFService} registered for this package. If no service is registered, 
	 * it returns the {@link EMFServiceImpl} service.
	 * @param ePackage filtering {@link EPackage}.
	 * @return {@link EMFService}.
	 */
EMFService getEMFServiceForPackage(EPackage ePackage);

}
