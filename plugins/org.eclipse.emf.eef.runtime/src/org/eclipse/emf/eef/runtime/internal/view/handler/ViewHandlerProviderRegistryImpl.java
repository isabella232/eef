/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.handler;

import java.util.List;

import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProviderRegistry;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerProviderRegistryImpl extends EEFServiceRegistry<Object, ViewHandlerProvider> implements ViewHandlerProviderRegistry {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handler.ViewHandlerProviderRegistry#getViewHandlerProviders()
	 */
	public List<ViewHandlerProvider> getViewHandlerProviders() {
		if (customServices instanceof List) {
			return (List<ViewHandlerProvider>) customServices;
		} else {
			return Lists.newArrayList(customServices);
		}
	}

}
