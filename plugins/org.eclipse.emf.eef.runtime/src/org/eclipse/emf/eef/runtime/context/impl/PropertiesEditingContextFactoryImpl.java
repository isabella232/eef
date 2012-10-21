/**
 * 
 */
package org.eclipse.emf.eef.runtime.context.impl;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.services.EEFServiceRegistry;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextFactoryImpl implements PropertiesEditingContextFactory {

	private EEFServiceRegistry componentRegistry;
	private ModelChangesNotificationManager notificationManager;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl)
	 */
	public void setComponentRegistry(EEFServiceRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#unsetComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFServiceRegistryImpl)
	 */
	public void unsetComponentRegistry(EEFServiceRegistry componentRegistry) {
		if (componentRegistry == this.componentRegistry) {
			this.componentRegistry = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#setNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void setNotificationManager(ModelChangesNotificationManager notificationManager) {
		this.notificationManager = notificationManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#unsetNotificationManager(org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager)
	 */
	public void unsetNotificationManager(ModelChangesNotificationManager notificationManager) {
		if (notificationManager == this.notificationManager) {
			this.notificationManager = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		PropertiesEditingContext context = new EObjectPropertiesEditingContext(adapterFactory, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactoryEditingDomain domain, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject) {
		DomainPropertiesEditingContext context = new DomainPropertiesEditingContext(domain, adapterFactory, eObject);
		configureEditingContext(context);
		return context;
	}

	private void configureEditingContext(PropertiesEditingContext context) {
		context.setServiceRegistry(componentRegistry);
		context.setNotificationManager(notificationManager);
	}

}
