package org.eclipse.emf.eef.runtime.util.impl;
/**
 * 
 */


import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.util.EMFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFServiceCustom implements EMFService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EPackage element) {
		return element == EcorePackage.eINSTANCE;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFService#equals(org.eclipse.emf.ecore.EClass, org.eclipse.emf.ecore.EClass)
	 */
	public boolean equals(EClass eClass1, EClass eClass2) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFService#equals(org.eclipse.emf.ecore.EPackage, org.eclipse.emf.ecore.EPackage)
	 */
	public boolean equals(EPackage ePack1, EPackage ePack2) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.util.EMFService#mapFeature(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
	 */
	public EStructuralFeature mapFeature(EObject editedObject, EStructuralFeature feature) {
		return null;
	}	
	

}
