/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingHandler;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BindingHandlerProviderImpl extends EEFServiceProviderImpl<EObject, PropertiesBindingHandler> implements BindingHandlerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider#getBindingManager(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesBindingHandler getBindingManager(EObject target) {
		return getService(target);
	}

}
