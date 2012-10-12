/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.notify;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
import org.eclipse.emf.eef.runtime.notify.ModelChangesNotifierImpl;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.emf.EMFService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ModelChangesNotificationManagerImpl implements ModelChangesNotificationManager {

	private BundleContext bundleContext;
	private EventAdmin eventAdmin;
	private EEFComponentRegistry componentRegistry;
	
	private Map<PropertiesEditingComponent,ServiceRegistration> serviceRegistrations;
	
	/**
	 * Default constructor.
	 */
	public ModelChangesNotificationManagerImpl() {
		serviceRegistrations = Maps.newHashMap();
	}
	
	/**
	 * Activation method for this component.
	 * @param context {@link ComponentContext} of this component.
	 */
	public void activate(ComponentContext context) {
		bundleContext = context.getBundleContext();
	}

	/**
	 * @param eventAdmin the eventAdmin to set
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#setComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
	 */
	public void setComponentRegistry(EEFComponentRegistry componentRegistry) {
		this.componentRegistry = componentRegistry;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#unsetComponentRegistry(org.eclipse.emf.eef.runtime.services.impl.EEFComponentRegistryImpl)
	 */
	public void unsetComponentRegistry(EEFComponentRegistry componentRegistry) {
		if (componentRegistry == this.componentRegistry) {
			this.componentRegistry = null;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#registerEditingComponentAsEventHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
		Dictionary<String, Object> properties = new Hashtable<String, Object>(); 
		properties.put(EventConstants.EVENT_TOPIC, ModelChangesNotifier.EEF_EVENT_BASE_TOPIC + ModelChangesNotifier.EEF_ECLASS_NOTIFICATION_TOPIC + editingComponent.getEObject().eClass().getEPackage().getName() + "_" + editingComponent.getEObject().eClass().getName());		
		ServiceRegistration registerService = bundleContext.registerService(EventHandler.class.getName(), editingComponent, properties);
		serviceRegistrations.put(editingComponent, registerService);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#unregisterEditingComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
	 */
	public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
		if (serviceRegistrations.get(editingComponent) != null) {
			bundleContext.ungetService(serviceRegistrations.get(editingComponent).getReference());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#initModelChangesNotifierIfNeeded(EObject)
	 */
	public void initModelChangesNotifierIfNeeded(EObject source) {
		EMFService emfService = (EMFService) componentRegistry.getHighestProvider(EMFService.class, source.eClass().getEPackage());
		Notifier highestNotifier = emfService.highestNotifier(source);
		Adapter existingAdapter = EcoreUtil.getExistingAdapter(highestNotifier, ModelChangesNotifier.class);
		if (existingAdapter == null) {
			highestNotifier.eAdapters().add(new ModelChangesNotifierImpl(eventAdmin));
		}
	}
}
