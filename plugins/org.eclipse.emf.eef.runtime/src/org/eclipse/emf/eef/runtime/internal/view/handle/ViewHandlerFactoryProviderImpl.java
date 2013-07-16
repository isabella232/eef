/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.view.handle;

import org.eclipse.emf.eef.runtime.editingModel.View;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactory;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactoryProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewHandlerFactoryProviderImpl extends EEFServiceProviderImpl<View, ViewHandlerFactory<?>> implements ViewHandlerFactoryProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.handle.ViewHandlerFactoryProvider#getHandlerFactory(org.eclipse.emf.eef.runtime.editingModel.View)
	 */
	public ViewHandlerFactory<?> getHandlerFactory(View view) {
		return getService(view);
	}

}
