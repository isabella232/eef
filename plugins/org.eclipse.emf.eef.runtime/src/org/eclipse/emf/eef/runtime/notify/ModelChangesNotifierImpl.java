/**
 * 
 */
package org.eclipse.emf.eef.runtime.notify;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
import org.eclipse.emf.eef.runtime.internal.notify.ModelChangesNotifier;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class ModelChangesNotifierImpl extends EContentAdapter implements ModelChangesNotifier {

	/**
	 * {@link EventAdmin} used to communicate with {@link PropertiesEditingComponent}s. 
	 */
	private final EventAdmin eventAdmin;

	/**
	 * @param eventAdmin
	 */
	public ModelChangesNotifierImpl(final EventAdmin eventAdmin) {
		super();
		this.eventAdmin = eventAdmin;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#isAdapterForType(java.lang.Object)
	 */
	@Override
	public boolean isAdapterForType(Object type) {
		return type == ModelChangesNotifier.class || super.isAdapterForType(type);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		String topic;
		if (notification.getNotifier() instanceof EObject) {
			EObject notifier = (EObject)notification.getNotifier();
			topic = EEF_EVENT_BASE_TOPIC + EEF_ECLASS_NOTIFICATION_TOPIC + notifier.eClass().getEPackage().getName() + "_" + notifier.eClass().getName();
		} else { 
			topic = EEF_EVENT_BASE_TOPIC + EEF_GLOBAL_NOTIFICATION_TOPIC;
		}
		Map<String, Object> properties = Maps.newHashMap();
		properties.put("notification", notification);
		Event event = new Event(topic, properties);
		eventAdmin.postEvent(event);
	}
	
	
	
}
