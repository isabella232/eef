/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.eef.runtime.binding.BindingManagerProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.services.EEFServiceProviderImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class BindingManagerProviderImpl extends EEFServiceProviderImpl<PropertiesEditingComponent, PropertiesBindingManager> implements BindingManagerProvider {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.BindingManagerProvider#getBindingManager(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public PropertiesBindingManager getBindingManager(PropertiesEditingComponent component) {
		return getService(component);
	}

}
