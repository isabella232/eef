/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactoryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerFactoryProviderImpl extends EEFServiceProviderImpl<Object, ViewHandlerFactory> implements ViewHandlerFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerFactoryProvider#getHandlerFactory(java.lang.Object)
	 */
	public ViewHandlerFactory getHandlerFactory(Object view) {
		return getService(view);
	}

}
