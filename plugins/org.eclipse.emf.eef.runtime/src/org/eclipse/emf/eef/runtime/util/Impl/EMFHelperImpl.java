/**
 * 
 */
package org.eclipse.emf.eef.runtime.util.Impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.eef.runtime.util.EMFHelper;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFHelperImpl implements EMFHelper {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFHelper#equals(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EClass)
	 */
	public boolean equals(EClass eClass1, EClass eClass2) {
		if (eClass1.equals(eClass2)) {
			return true;
		}
		if (eClass1.eResource().getURI().isPlatform() && !eClass2.eResource().getURI().isPlatform()) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(eClass1.getEPackage().getNsURI());
			if (ePackage != null) {
				EClassifier mappedEClass1 = ePackage.getEClassifier(eClass1.getName());
				if (eClass2.equals(mappedEClass1)) {
					return true;
				}
			}
		}
		if (!eClass1.eResource().getURI().isPlatform() && eClass2.eResource().getURI().isPlatform()) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(eClass2.getEPackage().getNsURI());
			if (ePackage != null) {
				EClassifier mappedEClass2 = ePackage.getEClassifier(eClass2.getName());
				if (eClass1.equals(mappedEClass2)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFHelper#mapFeature(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public EStructuralFeature mapFeature(EObject editedObject, EStructuralFeature feature) {
		if (feature != null) {
			if (editedObject.eClass().getEAllStructuralFeatures().contains(feature)) {
				// If the EObject contains the feature then we return this feature
				return feature;
			} else {
				if (feature.eResource().getURI().isPlatform() && !editedObject.eClass().eResource().getURI().isPlatform()) {
					// This is a case managed by this helper: the editingModel refers to the Ecore file and the EObject is created
					// frome the registered metamodel
					for (EStructuralFeature srcFeature : editedObject.eClass().getEAllStructuralFeatures()) {
						if (srcFeature.getName().equals(feature.getName())) {
							return srcFeature;
						}
					}
				}
			}
		}
		return null;
	}

}
