/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextFactoryImpl implements PropertiesEditingContextFactory {

	private EMFServiceProvider emfServiceProvider;
	private PropertiesEditingProviderRegistry propertiesEditingProviderRegistry;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#setEMFServiceProvider(org.eclipse.emf.eef.runtime.util.EMFServiceProvider)
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#unsetEMFServiceRegistry(org.eclipse.emf.eef.runtime.util.EMFServiceProvider)
	 */
	public void unsetEMFServiceRegistry(EMFServiceProvider emfServiceProvider) {
		if (emfServiceProvider == this.emfServiceProvider) {
			this.emfServiceProvider = null;
		}
	}

	/**
	 * @param propertiesEditingProviderRegistry the propertiesEditingProviderRegistry to set
	 */
	public void setPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry) {
		this.propertiesEditingProviderRegistry = propertiesEditingProviderRegistry;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#unsetPropertiesEditingProviderRegistry(org.eclipse.emf.eef.runtime.binding.PropertiesEditingProviderRegistry)
	 */
	public void unsetPropertiesEditingProviderRegistry(PropertiesEditingProviderRegistry propertiesEditingProviderRegistry) {
		if (propertiesEditingProviderRegistry == this.propertiesEditingProviderRegistry) {
			this.propertiesEditingProviderRegistry = null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(adapterFactory, eObject);
		context.setEmfServiceProvider(emfServiceProvider);
		context.setPropertiesEditingProviderRegistry(propertiesEditingProviderRegistry);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactoryEditingDomain domain, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, eObject);
		context.setEmfServiceProvider(emfServiceProvider);
		context.setPropertiesEditingProviderRegistry(propertiesEditingProviderRegistry);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, adapterFactory, eObject);
		context.setEmfServiceProvider(emfServiceProvider);
		context.setPropertiesEditingProviderRegistry(propertiesEditingProviderRegistry);
		return context;
	}

}
