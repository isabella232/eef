package org.eclipse.emf.eef.runtime.binding;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.services.EEFService;

public interface PropertiesBindingManager extends EEFService<PropertiesEditingComponent> {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.notify.PropertiesEditingListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent)
	 */
	void firePropertiesChanged(PropertiesEditingComponent editingComponent, PropertiesEditingEvent editingEvent);

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#execute(org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy, org.eclipse.emf.eef.runtime.context.SemanticPropertiesEditingContext)
	 */
	void execute(PropertiesEditingComponent editingComponent, PropertiesEditingPolicy editingPolicy, PropertiesEditingContext policyEditingContext);

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.binding.PropertiesEditingComponent#notifyChanged(Notification)
	 */
	void notifyChanged(PropertiesEditingComponent editingComponent, Notification msg);

}