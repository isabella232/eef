/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EPackageRegistryTracker<SERVICE_REGISTRY> extends ServiceTracker<SERVICE_REGISTRY, SERVICE_REGISTRY> {

	/**
	 * Default constructor.
	 * @param context
	 * @param clazz
	 */
	public EPackageRegistryTracker(BundleContext context, Class<SERVICE_REGISTRY> clazz) {
		super(context, clazz, null);
	}
	
}
