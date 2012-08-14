/**
 * 
 */
package org.eclipse.emf.eef.runtime.tests.core.service;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.eef.runtime.EEFRuntime;
import org.eclipse.emf.eef.runtime.util.EMFService;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;

/**
 * This class test that we can override the EMFService for a given package by using declarative services.
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class EMFServiceProvidingTests {
	
	private static final String CUSTOMPROVIDER_BUNDLE_NAME = "org.eclipse.emf.eef.runtime.tests.customproviders";

	/**
	 * This test first load a {@link EMFService} for {@link EcorePackage} and then install a bundle with a custom
	 * {@link EMFService} and check that the two services are differents. 
	 * @throws BundleException an error occured during bundles manipulation.
	 */
	@Test
	public void testCustomizedEMFService() throws BundleException {
		BundleContext bundleContext = EEFRuntime.getPlugin().getBundle().getBundleContext();
		Bundle[] bundles = bundleContext.getBundles();
		Bundle customizerBundle = null;
		for (Bundle bundle : bundles) {
			if (CUSTOMPROVIDER_BUNDLE_NAME.equals(bundle.getSymbolicName())) {
				customizerBundle = bundle;
			}
		}
		if (customizerBundle != null) {
			EMFService emfStdService = EEFRuntime.getPlugin().getEMFService(EcorePackage.eINSTANCE);
			assertTrue("Bad EMFService returned in default case", "EMFServiceImpl".equals(emfStdService.getClass().getSimpleName()));		
			customizerBundle.start();
			if (customizerBundle.getState() == Bundle.INSTALLED || customizerBundle.getState() == Bundle.ACTIVE) {
				EMFService emfCustomService = EEFRuntime.getPlugin().getEMFService(EcorePackage.eINSTANCE);
				assertNotSame("Bad EMFService returned in default case", emfStdService, emfCustomService);
				customizerBundle.stop();				
			} else {
				fail("Unable to start " + CUSTOMPROVIDER_BUNDLE_NAME + " bundle");				
			}
		} else {
			fail("Unable to locate " + CUSTOMPROVIDER_BUNDLE_NAME + " bundle");
		}
	}

}
