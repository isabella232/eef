/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.util.impl.EMFServiceRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingProviderRegistryImpl extends EEFServiceRegistry<EPackage, PropertiesEditingProvider> implements PropertiesEditingProviderRegistry {

	private EMFServiceProvider emfServiceProvider;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#setEMFServiceProvider(EMFServiceRegistry)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#unsetEMFServiceRegistry(org.eclipse.emf.eef.runtime.util.impl.EMFServiceRegistry)
	 */
	public void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		if (emfServiceProvider == this.emfServiceProvider) {
			this.emfServiceProvider = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceRegistry#addService(org.eclipse.emf.eef.runtime.services.EEFService, java.util.Map)
	 */
	public synchronized void addService(PropertiesEditingProvider service, Map<?, ?> properties) {
		service.setEMFServiceProvider(emfServiceProvider);
		super.addService(service, properties);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#getPropertiesEditingProvider(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage) {
		return getServiceForElement(ePackage);
	}
	
	

}
