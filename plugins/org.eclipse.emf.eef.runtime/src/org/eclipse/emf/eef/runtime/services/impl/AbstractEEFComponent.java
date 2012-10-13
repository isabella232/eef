/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;

import org.eclipse.emf.eef.runtime.services.EEFComponent;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.osgi.service.component.ComponentContext;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class AbstractEEFComponent implements EEFComponent {

	private Collection<String> providedServices;
	private EEFComponentRegistry componentRegistry;
	
	public AbstractEEFComponent() {
		providedServices = Lists.newArrayList();
	}

	/**
	 * @return the componentRegistry
	 */
	public EEFComponentRegistry getComponentRegistry() {
		return componentRegistry;
	}

	/**
	 * @param componentRegistry the componentRegistry to set
	 */
	public void setComponentRegistry(EEFComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
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
