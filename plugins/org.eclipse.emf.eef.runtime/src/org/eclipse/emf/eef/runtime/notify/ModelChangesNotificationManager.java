///**
// * 
// */
//package org.eclipse.emf.eef.runtime.notify;
//
//import org.eclipse.emf.common.notify.Notifier;
//import org.eclipse.emf.ecore.EObject;
//import org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent;
//import org.eclipse.emf.eef.runtime.internal.notify.ModelChangesNotifier;
//import org.osgi.framework.BundleContext;
//import org.osgi.service.event.EventHandler;
//
///**
// * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
// *
// */
//public interface ModelChangesNotificationManager {
//
//	/**
//	 * Registers the given {@link PropertiesEditingComponent} in the {@link BundleContext} as an {@link EventHandler}.
//	 * @param editingComponent the {@link PropertiesEditingComponent} to register.
//	 */
//	void registerEditingComponentAsEventHandler(PropertiesEditingComponent editingComponent);
//	
//	/**
//	 * Unregisters the given {@link PropertiesEditingComponent} in the {@link BundleContext}.
//	 * @param editingComponent the {@link PropertiesEditingComponent} to unregister.
//	 */
//	void unregisterEditingComponent(PropertiesEditingComponent editingComponent);
//
//	/**
//	 * Registers an {@link ModelChangesNotifier} on the highest reachable notifier from the given {@link EObject}. 
//	 * @param notifier {@link EObject} to process.
//	 */
//	void initModelChangesNotifierIfNeeded(Notifier notifier);
//
//}
