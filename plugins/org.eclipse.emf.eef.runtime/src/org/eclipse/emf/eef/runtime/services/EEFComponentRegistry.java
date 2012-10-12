package org.eclipse.emf.eef.runtime.services;

import java.util.Map;

import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;

public interface EEFComponentRegistry {

	/**
	 * Returns the asked {@link EEFService} for the given element. The registry select the returned one follow these choices:
	 * <ul>
	 * 		<li>If only one service is registered for this kind of element, it is returned</li>
	 * 		<li>If several services are registered : the one prior on other services is returned</li>
	 * 		<li>If there is more than one choice of service, one is randomly selected</li>
	 * 		<li>If no service is registered for this kind of element, but a DefaultService is registered, this one is return</li>
	 * 		<li><code>null</code> otherwise</li>
	 * </ul>
	 * @param type kind of expected services.
	 * @param element the element to process by the service.
	 * @return the best {@link EEFService} for this element.
	 */
	EEFService<?> getService(Class<?> type, Object element);

	/**
	 * Adds a component in the current registry.
	 * @param component the {@link EEFComponent} to add.
	 * @param properties properties of the component.
	 * @throws PriorityCircularityException if we add this component, a circularity is introduced in the priority tree.
	 */
	void addComponent(EEFComponent component, Map<String, ?> properties) throws PriorityCircularityException;

	/**
	 * Removes a component from the current registry.
	 * @param component the component to remove.
	 */
	void removeComponent(final EEFComponent component);

}