package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.EEFService;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandler;
import org.eclipse.emf.eef.runtime.view.lock.EEFLockManager;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockEvent;
import org.eclipse.emf.eef.runtime.view.lock.policies.EEFLockPolicy;

import com.google.common.base.Function;

public interface PropertiesBindingManager extends EEFService<PropertiesEditingComponent> {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);
	
	/**
	 * Notifies the current component of a lock change.
	 * @param editingComponent {@link PropertiesEditingComponent} the editingComponent to lock.
	 * @param lockEvent {@link EEFLockEvent} describing the lock change.
	 */
	void fireLockChanged(PropertiesEditingComponent editingComponent, EEFLockEvent lockEvent);

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#execute(org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy, org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	void execute(PropertiesEditingComponent editingComponent, PropertiesEditingPolicy editingPolicy, PropertiesEditingContext policyEditingContext);

	/**
	 * Executes a {@link Function} on all the created {@link ViewHandler} by the current {@link PropertiesEditingComponent}.
	 * @param function the {@link Function} to execute. 
	 */
	void executeOnViewHandlers(PropertiesEditingComponent editingComponent, Function<ViewHandler<?>, Void> function);
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#notifyChanged(Notification)
	 */
	void notifyChanged(PropertiesEditingComponent editingComponent, Notification msg);
	
	/**
	 * Initializes the {@link EEFLockPolicy} to use in the given component.
	 * @param editingComponent the {@link PropertiesEditingComponent} to initialize.
	 */
	void initLockPolicies(PropertiesEditingComponent editingComponent);
	
}