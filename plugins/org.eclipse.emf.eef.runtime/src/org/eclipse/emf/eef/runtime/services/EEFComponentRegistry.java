package org.eclipse.emf.eef.runtime.services;

import java.util.Map;

import org.eclipse.emf.eef.runtime.services.impl.PriorityCircularityException;

public interface EEFComponentRegistry {

	EEFService<?> getHighestProvider(Class<?> type, Object element);

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