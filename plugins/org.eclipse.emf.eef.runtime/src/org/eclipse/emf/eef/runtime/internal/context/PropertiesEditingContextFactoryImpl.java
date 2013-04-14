/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.context;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.context.DomainAwarePropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.impl.AbstractEEFService;
import org.osgi.service.component.ComponentFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PropertiesEditingContextFactoryImpl extends AbstractEEFService<EObject> implements PropertiesEditingContextFactory, DefaultService {

	private final List<String> CONTEXT_ID = Lists.newArrayList(
				EObjectPropertiesEditingContext.FACTORY_ID,
				DomainPropertiesEditingContext.FACTORY_ID,
				SemanticPropertiesEditingContext.FACTORY_ID,
				SemanticDomainPropertiesEditingContext.FACTORY_ID
			);
	
	private Map<String, ComponentFactory> contextFactories;
	private ModelChangesNotificationManager notificationManager;

	/**
	 * 
	 */
	public PropertiesEditingContextFactoryImpl() {
		contextFactories = Maps.newHashMap();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(EObject element) {
		return true;
	}
	
	/**
	 * @param factory
	 * @param properties
	 */
	public synchronized void addChildFactory(ComponentFactory factory, Map<String, ?> properties) {
		String factoryID = (String) properties.get("component.factory");
		if (CONTEXT_ID.contains(factoryID)) {
			contextFactories.put(factoryID, factory);
		}
	}
	
	/**
	 * @param factory
	 * @param properties
	 */
	public synchronized void removeChildFactory(ComponentFactory factory, Map<String, ?> properties) {
		String factoryID = (String) properties.get("component.factory");
		if (CONTEXT_ID.contains(factoryID)) {
			contextFactories.remove(factoryID);
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
		ComponentFactory contextFactory = contextFactories.get(EObjectPropertiesEditingContext.FACTORY_ID);
		if (contextFactory != null) {
			Dictionary<String, Object> params = new Hashtable<String, Object>();
			params.put(PropertiesEditingContext.ADAPTERFACTORY_PARAM, adapterFactory);
			params.put(PropertiesEditingContext.EOBJECT_PARAM, eObject);
			PropertiesEditingContext context = (PropertiesEditingContext) contextFactory.newInstance(params).getInstance();
			configureEditingContext(context);
			return context;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(PropertiesEditingContext parentContext, EObject eObject) {
		PropertiesEditingContext context = null;
		Dictionary<String, Object> params = new Hashtable<String, Object>();
		params.put(PropertiesEditingContext.PARENTCONTEXT_PARAM, parentContext);
		params.put(PropertiesEditingContext.EOBJECT_PARAM, eObject);
		if (parentContext instanceof DomainAwarePropertiesEditingContext) {
			ComponentFactory contextFactory = contextFactories.get(DomainPropertiesEditingContext.FACTORY_ID);
			if (contextFactory != null) {
				context = (PropertiesEditingContext) contextFactory.newInstance(params).getInstance();
				configureEditingContext(context);
			}
		} else if (parentContext instanceof EObjectPropertiesEditingContext) {
			ComponentFactory contextFactory = contextFactories.get(EObjectPropertiesEditingContext.FACTORY_ID);
			if (contextFactory != null) {
				context = (PropertiesEditingContext) contextFactory.newInstance(params).getInstance();
				configureEditingContext(context);
			}
		} else {
			throw new IllegalArgumentException("Unable to process this context as parent");
		}
		return context;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(AdapterFactoryEditingDomain domain, EObject eObject) {
		ComponentFactory contextFactory = contextFactories.get(DomainPropertiesEditingContext.FACTORY_ID);
		if (contextFactory != null) {
			Dictionary<String, Object> params = new Hashtable<String, Object>();
			params.put(DomainAwarePropertiesEditingContext.EDITINGDOMAIN_PARAM, domain);
			params.put(PropertiesEditingContext.EOBJECT_PARAM, eObject);
			PropertiesEditingContext context = (PropertiesEditingContext) contextFactory.newInstance(params).getInstance();
			configureEditingContext(context);
			return context;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createPropertiesEditingContext(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.common.notify.AdapterFactory, org.eclipse.emf.ecore.EObject)
	 */
	public PropertiesEditingContext createPropertiesEditingContext(EditingDomain domain, AdapterFactory adapterFactory, EObject eObject) {
		ComponentFactory contextFactory = contextFactories.get(DomainPropertiesEditingContext.FACTORY_ID);
		if (contextFactory != null) {
			Dictionary<String, Object> params = new Hashtable<String, Object>();
			params.put(DomainAwarePropertiesEditingContext.EDITINGDOMAIN_PARAM, domain);
			params.put(PropertiesEditingContext.ADAPTERFACTORY_PARAM, adapterFactory);
			params.put(PropertiesEditingContext.EOBJECT_PARAM, eObject);
			PropertiesEditingContext context = (PropertiesEditingContext) contextFactory.newInstance(params).getInstance();
			configureEditingContext(context);
			return context;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.context.PropertiesEditingContextFactory#createSemanticPropertiesEditingContext(org.eclipse.emf.eef.runtime.context.PropertiesEditingContext, org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	public PropertiesEditingContext createSemanticPropertiesEditingContext(PropertiesEditingContext parentContext, PropertiesEditingEvent editingEvent) {
		SemanticPropertiesEditingContext context = null;
		if (parentContext instanceof DomainAwarePropertiesEditingContext) {
			ComponentFactory contextFactory = contextFactories.get(SemanticDomainPropertiesEditingContext.FACTORY_ID);
			if (contextFactory != null) {
				Dictionary<String, Object> params = new Hashtable<String, Object>();
				params.put(PropertiesEditingContext.PARENTCONTEXT_PARAM, parentContext);
				params.put(SemanticPropertiesEditingContext.EDITINGEVENT_PARAM, editingEvent);
				context = (SemanticPropertiesEditingContext) contextFactory.newInstance(params).getInstance();
			}
		} else {
			ComponentFactory contextFactory = contextFactories.get(SemanticPropertiesEditingContext.FACTORY_ID);
			if (contextFactory != null) {
				Dictionary<String, Object> params = new Hashtable<String, Object>();
				params.put(PropertiesEditingContext.PARENTCONTEXT_PARAM, parentContext);
				params.put(SemanticPropertiesEditingContext.EDITINGEVENT_PARAM, editingEvent);
				context = (SemanticPropertiesEditingContext) contextFactory.newInstance(params).getInstance();
			}
		}
		return context;
	}

	private void configureEditingContext(PropertiesEditingContext context) {
		context.setServiceRegistry(serviceRegistry);
//		context.setNotificationManager(notificationManager);
	}

}
