/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.binding.BindingHandlerProvider;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.EditingContextFactoryProvider;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.EEFServiceProvider;
import org.eclipse.emf.eef.runtime.util.EMFServiceProvider;
import org.eclipse.emf.eef.runtime.view.handle.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextFactoryImpl implements PropertiesEditingContextFactory, DefaultService {

	private EEFServiceProvider<EObject, PropertiesEditingContextFactory> serviceProvider;
	private EMFServiceProvider emfServiceProvider;
	private BindingHandlerProvider bindingHandlerProvider;
	private ViewHandlerProvider viewHandlerProvider;

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#setProvider(org.eclipse.emf.eef.runtime.services.EEFServiceProvider)
	 */
	public void setProvider(EEFServiceProvider<EObject, PropertiesEditingContextFactory> serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#getContextFactoryProvider()
	 */
	public EditingContextFactoryProvider getContextFactoryProvider() {
		return (EditingContextFactoryProvider) serviceProvider;
	}

	/**
	 * @param emfServiceProvider the emfServiceProvider to set
	 */
	public void setEMFServiceProvider(EMFServiceProvider emfServiceProvider) {
		this.emfServiceProvider = emfServiceProvider;
	}

	/**
	 * @param bindingHandlerProvider the bindingHandlerProvider to set
	 */
	public void setBindingManagerProvider(BindingHandlerProvider bindingHandlerProvider) {
		this.bindingHandlerProvider = bindingHandlerProvider;
	}

	/**
	 * @param viewHandlerProvider the viewHandlerProvider to set
	 */
	public void setViewHandlerProvider(ViewHandlerProvider viewHandlerProvider) {
		this.viewHandlerProvider = viewHandlerProvider;
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
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createNullEditingContext()
	 */
	public PropertiesEditingContext createNullEditingContext() {
		return new NullPropertiesEditingContextImpl();
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

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createReflectivePropertiesEditingContext(org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createReflectivePropertiesEditingContext(AdapterFactory adapterFactory, EObject view) {
		ReflectivePropertiesEditingContext context = new ReflectivePropertiesEditingContext(adapterFactory, view);
		context.setEMFServiceProvider(emfServiceProvider);
		context.setBindingManagerProvider(bindingHandlerProvider);
		context.setContextFactoryProvider(getContextFactoryProvider());
		context.setViewHandlerProvider(viewHandlerProvider);
		return context;
	}

	private void configureEditingContext(EObjectPropertiesEditingContext context) {
		context.setEMFServiceProvider(emfServiceProvider);
		context.setBindingManagerProvider(bindingHandlerProvider);
		context.setContextFactoryProvider(getContextFactoryProvider());
		context.setViewHandlerProvider(viewHandlerProvider);
	}

}
