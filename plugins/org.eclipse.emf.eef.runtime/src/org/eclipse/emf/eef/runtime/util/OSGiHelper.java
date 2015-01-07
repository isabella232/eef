/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.eef.runtime.util;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class OSGiHelper {
	
	/**
	 * Returns a OSGi service of type serviceClass available in the given context.
	 * @param bundleContext the {@link BundleContext} to process.
	 * @param serviceClass type of searched service.
	 * @return a service of the given type if available <code>null</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getService(BundleContext bundleContext, Class<T> serviceClass) {
		ServiceReference reference = bundleContext.getServiceReference(serviceClass.getName());
		if (reference != null) {
			return (T) bundleContext.getService(reference);
		}
		return null;
	}

}
