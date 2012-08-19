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
public class EEFServiceRegistryTracker<SERVICE_REGISTRY> extends ServiceTracker<SERVICE_REGISTRY, SERVICE_REGISTRY> {

	/**
	 * Default constructor.
	 * @param context
	 * @param clazz
	 */
	public EEFServiceRegistryTracker(BundleContext context, Class<SERVICE_REGISTRY> clazz) {
		super(context, clazz, null);
	}
	
}
