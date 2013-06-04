/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;

import org.eclipse.emf.eef.runtime.services.EEFService;
import org.osgi.service.component.ComponentContext;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEEFService<T> implements EEFService<T> {

	private Collection<String> providedServices;
	
	public AbstractEEFService() {
		providedServices = Lists.newArrayList();
	}

	public void activate(ComponentContext context) {
		@SuppressWarnings("rawtypes")
		Dictionary properties = context.getProperties();
		Object object = properties.get("objectClass");
		if (object != null && object instanceof String[]) {
			providedServices.addAll(Arrays.asList((String[])object));
		}
	}
	

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#providedServices()
	 */
	public Collection<String> providedServices() {
		return providedServices;
	}
	
}
