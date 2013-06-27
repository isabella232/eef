/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BindingManagerProviderImpl extends EEFServiceProviderImpl<EObject, PropertiesBindingManager> implements BindingManagerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.BindingManagerProvider#getBindingManager(org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesBindingManager getBindingManager(EObject target) {
		return getService(target);
	}

}
