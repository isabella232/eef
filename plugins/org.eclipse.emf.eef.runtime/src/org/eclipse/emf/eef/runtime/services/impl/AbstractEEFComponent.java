/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;

import org.eclipse.emf.eef.runtime.services.EEFComponent;
import org.osgi.service.component.ComponentContext;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEEFComponent implements EEFComponent {

	private Collection<Class<?>> providedServices;
	
	public AbstractEEFComponent() {
		providedServices = Lists.newArrayList();
	}


	public void activate(ComponentContext context) {
		Dictionary properties = context.getProperties();
		Object object = properties.get("objectClass");
		if (object != null && object instanceof String[]) {
			for (String className : ((String[])object)) {
				try {
					Class<?> clazz = Class.forName(className);
					if (clazz != null && clazz != EEFComponent.class) {
						providedServices.add(clazz);
					}
					//TODO: else an error should be logged!
				} catch (ClassNotFoundException e) {
					//TODO: an error should be logged!
				}
			}
		}
	}
	

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#providedServices()
	 */
	public Collection<Class<?>> providedServices() {
		if (providedServices == null) {
			return Collections.emptyList();
		} else {
			return providedServices;
		}
	}
	
}
