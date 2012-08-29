/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.binding;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;
import org.osgi.service.component.ComponentContext;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingProviderRegistryImpl extends EEFServiceRegistry<EPackage, PropertiesEditingProvider> implements PropertiesEditingProviderRegistry {

	private EMFServiceProvider emfServiceProvider;
	private ViewHandlerProviderRegistry viewHandlerProviderRegistry;
	private ComponentContext context;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#setEMFServiceProvider(EMFServiceRegistry)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#unsetEMFServiceRegistry(org.eclipse.emf.eef.runtime.services.emf.EMFServiceRegistry)
	 */
	public void unsetEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		if (emfServiceProvider == this.emfServiceProvider) {
			this.emfServiceProvider = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#setViewHandlerProviderRegistry(ViewHandlerProviderRegistry)
	 */
	public void setViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		this.viewHandlerProviderRegistry = viewHandlerProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#unsetViewHandlerProviderRegistry(org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry)
	 */
	public void unsetViewHandlerProviderRegistry(ViewHandlerProviderRegistry viewHandlerProviderRegistry) {
		if (viewHandlerProviderRegistry == this.viewHandlerProviderRegistry) {
			this.viewHandlerProviderRegistry = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProvider#activate(org.osgi.service.component.ComponentContext)
	 */
	public void activate(ComponentContext context) {
		this.context = context;
		for (PropertiesEditingProvider provider : customServices) {
			provider.setBundleContext(context.getBundleContext());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceRegistry#addService(org.eclipse.emf.eef.runtime.services.EEFService)
	 */
	public synchronized void addService(PropertiesEditingProvider service) {
		if (context != null) {
			service.setBundleContext(context.getBundleContext());
		}
		service.setEMFServiceProvider(emfServiceProvider);
		service.setViewHandlerProviderRegistry(viewHandlerProviderRegistry);
		super.addService(service);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry#getPropertiesEditingProvider(org.eclipse.emf.ecore.EPackage)
	 */
	public PropertiesEditingProvider getPropertiesEditingProvider(EPackage ePackage) {
		return getServiceForElement(ePackage);
	}
	
	

}
