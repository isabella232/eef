/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.service;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.tests.util.EEFTestEnvironmentBuilder;
import org.junit.Test;
import org.osgi.framework.BundleException;

/**
 * This class test that we can override the EMFService for a given package by using declarative services.
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EEFServiceRegistriesTests {
	
	/**
	 * This test first load a {@link EMFService} for {@link EcorePackage} and then install a bundle with a custom
	 * {@link EMFService} and check that the two services are differents. 
	 * @throws BundleException an error occured during bundles manipulation.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCustomizedEMFService() throws BundleException {
		EEFTestEnvironmentBuilder builder = new EEFTestEnvironmentBuilder();
		EMFServiceProvider emfServiceProvider = builder.createEMFServiceProvider(builder.createEMFServices());
		assertTrue("Bad type of EMFServiceRegistry.", emfServiceProvider instanceof EEFServiceRegistry);
		EMFService defaultEMFService = emfServiceProvider.getEMFServiceForPackage(EcorePackage.eINSTANCE);
		((EEFServiceRegistry<EPackage, EMFService>)emfServiceProvider).addService(new CustomEMFService());
		EMFService customEMFService = emfServiceProvider.getEMFServiceForPackage(EcorePackage.eINSTANCE);
		assertNotSame("Bad type of EMFService returned.", defaultEMFService, customEMFService);
	}

	private final class CustomEMFService implements EMFService {
		public boolean serviceFor(EPackage element) {
			return element == EcorePackage.eINSTANCE;
		}

		public EStructuralFeature mapFeature(EObject editedObject, EStructuralFeature feature) {
			return null;
		}

		public Notifier highestNotifier(EObject src) {
			return null;
		}

		public boolean equals(EPackage ePack1, EPackage ePack2) {
			return false;
		}

		public boolean equals(EClass eClass1, EClass eClass2) {
			return false;
		}
	}

}
