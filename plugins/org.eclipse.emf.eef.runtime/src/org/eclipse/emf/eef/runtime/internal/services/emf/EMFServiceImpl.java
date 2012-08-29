/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.emf;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.eef.runtime.internal.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFServiceImpl implements EMFService, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(final EPackage element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.emf.EMFService#equals(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EClass)
	 */
	public boolean equals(final EClass eClass1, final EClass eClass2) {
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
	 * @see org.eclipse.emf.eef.runtime.services.emf.EMFService#equals(org.eclipse.emf.ecore.EPackage, org.eclipse.emf.ecore.EPackage)
	 */
	public boolean equals(final EPackage ePack1, final EPackage ePack2) {
		if (ePack1 == ePack2) {
			return true;
		} else if (ePack1.eResource().getURI().isPlatform() && !ePack2.eResource().getURI().isPlatform()) {
			EPackage ePackage1 = EPackage.Registry.INSTANCE.getEPackage(ePack1.getNsURI());
			if (ePackage1.equals(ePack2)) {
				return true;
			}
		} else if (!ePack1.eResource().getURI().isPlatform() && ePack2.eResource().getURI().isPlatform()) {
			EPackage ePackage2 = EPackage.Registry.INSTANCE.getEPackage(ePack2.getNsURI());
			if (ePackage2.equals(ePack1)) {
				return true;
			}			
		} 
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.emf.EMFService#mapFeature(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public EStructuralFeature mapFeature(final EObject editedObject, final EStructuralFeature feature) {
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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.emf.EMFService#highestNotifier(org.eclipse.emf.ecore.EObject)
	 */
	public Notifier highestNotifier(final EObject src) {
		if (src.eResource() != null) {
			Resource resource = src.eResource();
			if (resource.getResourceSet() != null) {
				return resource.getResourceSet();
			} else {
				return resource;
			}
		} else {
			EObject tmp = src;
			while (tmp.eContainer() != null) {
				tmp = tmp.eContainer();
			}
			return tmp;
		}
	}	
	
}
