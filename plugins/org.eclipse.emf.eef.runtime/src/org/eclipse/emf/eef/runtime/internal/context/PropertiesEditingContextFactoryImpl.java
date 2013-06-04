/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.bindingSettings.EEFBindingSettingsProvider;
import org.eclipse.emf.eef.runtime.services.emf.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextFactoryImpl extends AbstractEEFService<EObject> implements PropertiesEditingContextFactory, DefaultService {

	private EMFServiceProvider emfServiceProvider;
	private ModelChangesNotificationManager notificationManager;
	private EEFBindingSettingsProvider bindingSettingsProvider;

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
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
	 * @param bindingSettingsProvider the bindingSettingsProvider to set
	 */
	public void setBindingSettingsProvider(EEFBindingSettingsProvider bindingSettingsProvider) {
		this.bindingSettingsProvider = bindingSettingsProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactory adapterFactory, EObject eObject) {
		EObjectPropertiesEditingContext context = new EObjectPropertiesEditingContext(adapterFactory, eObject);
		configureEditingContext(context);
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		EObjectPropertiesEditingContext context;
		if (parentContext instanceof DomainAwarePropertiesEditingContext) {
			context = new DomainPropertiesEditingContext(parentContext, eObject);
		} else if (parentContext instanceof EObjectPropertiesEditingContext) {
			context = new EObjectPropertiesEditingContext(parentContext, eObject);
		} else {
			throw new IllegalArgumentException("Unable to process this context as a parent");
		}
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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createSemanticPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public PropertiesEditingContext createSemanticPropertiesEditingContext(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		SemanticPropertiesEditingContext context;
		if (parentContext instanceof DomainAwarePropertiesEditingContext) {
			context = new SemanticDomainPropertiesEditingContext((DomainAwarePropertiesEditingContext) parentContext, editingEvent);			
		} else {
			context = new SemanticPropertiesEditingContextImpl(parentContext, editingEvent);
		}
		return context;
	}

	private void configureEditingContext(EObjectPropertiesEditingContext context) {
		context.setNotificationManager(notificationManager);
		context.setEMFServiceProvider(emfServiceProvider);
		context.setBindingSettingsProvider(bindingSettingsProvider);
	}

}
