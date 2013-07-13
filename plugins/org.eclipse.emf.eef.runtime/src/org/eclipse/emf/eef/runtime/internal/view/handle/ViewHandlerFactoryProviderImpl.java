/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.handle;

import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactoryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerFactoryProviderImpl extends EEFServiceProviderImpl<Object, ViewHandlerFactory> implements ViewHandlerFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactoryProvider#getHandlerFactory(java.lang.Object)
	 */
	public ViewHandlerFactory getHandlerFactory(Object view) {
		return getService(view);
	}

}
