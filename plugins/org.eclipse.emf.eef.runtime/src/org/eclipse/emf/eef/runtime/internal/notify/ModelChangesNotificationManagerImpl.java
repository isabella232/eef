///**
// * 
// */
//package org.eclipse.emf.eef.runtime.internal.notify;
//
//import java.util.List;
//
//import org.eclipse.emf.common.notify.Adapter;
//import org.eclipse.emf.common.notify.Notification;
//import org.eclipse.emf.common.notify.Notifier;
//import org.eclipse.emf.ecore.util.EcoreUtil;
//import org.eclipse.emf.eef.runtime.binding.PropertiesBindingManager;
//import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
//import org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager;
//import org.eclipse.emf.eef.runtime.notify.ModelChangesNotifierImpl;
//import org.osgi.service.event.Event;
//import org.osgi.service.event.EventAdmin;
//import org.osgi.service.event.EventHandler;
//
//import com.google.common.collect.Lists;
//
///**
// * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
// *
// */
//public class ModelChangesNotificationManagerImpl implements ModelChangesNotificationManager, EventHandler {
//
//	private EventAdmin eventAdmin;
//	private PropertiesBindingManager bindingManager;
//	
//	private List<PropertiesEditingComponent> editingComponents;
//	
//	/**
//	 * Default constructor.
//	 */
//	public ModelChangesNotificationManagerImpl() {
//		editingComponents = Lists.newArrayList();
//	}
//	
//	/**
//	 * @param eventAdmin the eventAdmin to set
//	 */
//	public void setEventAdmin(EventAdmin eventAdmin) {
//		this.eventAdmin = eventAdmin;
//	}
//
//	/**
//	 * @param bindingManager the bindingManager to set
//	 */
//	public void setBindingManager(PropertiesBindingManager bindingManager) {
//		this.bindingManager = bindingManager;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#registerEditingComponentAsEventHandler(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
//	 */
//	public void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent) {
//		editingComponents.add(editingComponent);
//	}
//	
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#unregisterEditingComponent(org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent)
//	 */
//	public void unregisterEditingComponent(PropertiesEditingComponent editingComponent) {
//		editingComponents.remove(editingComponent);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.Event)
//	 * @processing
//	 */
//	public void handleEvent(Event event) {
//		if (event.getProperty("notification") instanceof Notification) {
//			Notification notification = (Notification) event.getProperty("notification"); 
//			for (PropertiesEditingComponent editingComponent : editingComponents) {
//				if (editingComponent.isAffectingEvent(notification)) {
//					bindingManager.notifyChanged(editingComponent, notification);
//				}
//			}
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 * @see org.eclipse.emf.eef.runtime.notify.ModelChangesNotificationManager#initModelChangesNotifierIfNeeded(org.eclipse.emf.common.notify.Notifier)
//	 */
//	public void initModelChangesNotifierIfNeeded(Notifier notifier) {
//		Adapter existingAdapter = EcoreUtil.getExistingAdapter(notifier, ModelChangesNotifier.class);
//		if (existingAdapter == null) {
//			notifier.eAdapters().add(new ModelChangesNotifierImpl(eventAdmin));
//		}
//		
//	}
//
//}
