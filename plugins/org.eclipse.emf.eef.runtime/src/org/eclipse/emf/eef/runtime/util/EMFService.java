/**
 * 
 */
package org.eclipse.emf.eef.runtime.util;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EMFService extends EEFService<EPackage> {
	
	/**
	 * Compares two EClass by trying to use the EPackage.Registry.
	 * @param eClass1 first {@link EClass} to compare.
	 * @param eClass2 second {@link EClass} to compare.
	 * @return <code>true</code> if the two EClasses are equal.
	 */
	boolean equals(EClass eClass1, EClass eClass2);

	/**
	 * Compares two EPackage by trying to use the EPackage.Registry.
	 * @param ePack1 first {@link EClass} to compare.
	 * @param ePack2 second {@link EClass} to compare.
	 * @return <code>true</code> if the two EPackages are equal.
	 */
	boolean equals(EPackage ePack1, EPackage ePack2);

	/**
	 * Try to find a corresponding feature on the given EObject
	 * @param editedObject {@link EObject} to process.
	 * @param feature {@link EStructuralFeature} feature to map.
	 * @return the corresponding feature if founded, <code>null</code> otherwise.
	 */
	EStructuralFeature mapFeature(EObject editedObject, EStructuralFeature feature);
	
	/**
	 * Returns the highest notifier accessible from the given EObject. The result can be:
	 * 	- The {@link ResourceSet} if the EObject is contained by an {@link ResourceSet};
	 * 	- The containing {@link Resource} if exists;
	 * 	- The root {@link EObject} of the cluster containing the given {@link EObject} otherwise.
	 * @param src {@link EObject} to process.
	 * @return the highest {@link Notifier}.
	 */
	Notifier highestNotifier(EObject src);
	
}
