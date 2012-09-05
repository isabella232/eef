/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import org.eclipse.emf.eef.runtime.services.EEFServiceOrderedRegistry;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerProviderRegistryImpl extends EEFServiceOrderedRegistry<Object, ViewHandlerProvider> implements ViewHandlerProviderRegistry {
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry#getViewHandlerProvider(java.lang.Object)
	 */
	public ViewHandlerProvider getViewHandlerProvider(Object element) {
		return getHighestProvider(element);
	}
}
