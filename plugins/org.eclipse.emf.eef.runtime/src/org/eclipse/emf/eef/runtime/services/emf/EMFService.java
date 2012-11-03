/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.emf;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.services.EEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EMFService extends EEFService<EPackage> {
	
	/**
	 * Compares two {@link EPackage} by trying to use the EPackage.Registry.
	 * @param ePack1 first {@link EPackage} to compare.
	 * @param ePack2 second {@link EPackage} to compare.
	 * @return <code>true</code> if the two packages are equals.
	 */
	boolean equals(EPackage ePack1, EPackage ePack2);

	/**
	 * Compares two {@link EClass} by trying to use the EPackage.Registry.
	 * @param eClass1 first {@link EClass} to compare.
	 * @param eClass2 second {@link EClass} to compare.
	 * @return <code>true</code> if the two classes are equals.
	 */
	boolean equals(EClass eClass1, EClass eClass2);

	/**
	 * Compares two {@link EStructuralFeature} by trying to use the EPackage.Registry.
	 * @param esf1 first {@link EStructuralFeature} to compare.
	 * @param esf2 second {@link EStructuralFeature} to compare.
	 * @return <code>true</code> if the two features are equals.
	 */
	boolean equals(EStructuralFeature esf1, EStructuralFeature esf2);

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

	/**
	 * Returns all EClass compatible with the {@link EReference} type. 
	 * @param domain the editingDomain owning the editedObject (can be <code>null</code>).
	 * @param editedObject the edited Object
	 * @param eReference the edited reference
	 * @return a {@link Collection} of {@link EClass} compatible with the {@link EReference} type.
	 */
	Collection<EClass> listOfInstanciableType(EditingDomain domain, EObject editedObject, EReference eReference);
	
}
