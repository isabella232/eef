/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.emf;

import org.eclipse.emf.ecore.EPackage;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EMFServiceProvider {
	
	/**
	 * Returns the most appropriate EMFService for the given {@link EPackage}.
	 * @param ePackage {@link EPackage} to process.
	 * @return an appropriate {@link EMFService}.
	 */
	EMFService getEMFService(EPackage ePackage);
	
}
