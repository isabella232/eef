/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.eclipse.emf.eef.runtime.internal.services.DefaultService;
import org.eclipse.emf.eef.runtime.services.EEFComponent;
import org.eclipse.emf.eef.runtime.services.EEFComponentRegistry;
import org.eclipse.emf.eef.runtime.services.EEFService;

import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EEFComponentRegistryImpl implements Cloneable, EEFComponentRegistry {
	
	private Map<String, EEFServiceStorage<?>> storages;
	private BiMap<String, Node> services;
	
	public EEFComponentRegistryImpl() {
		services = HashBiMap.create();
		storages = Maps.newHashMap();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFComponentRegistry#getService(java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public final EEFService<?> getService(Class<?> type, Object element) {
		List<EEFService<?>> availableProviders = Lists.newArrayList();
		@SuppressWarnings("rawtypes")
		EEFServiceStorage storage = storages.get(type.getName());
		if (storage != null) {
			availableProviders = storage.getServicesFor(element);
		}
		if (availableProviders.size() == 0) {			
			// If no registered provider can handle the view, we return null 
			return null;
		} else if (availableProviders.size() == 1) {
			// If only one provider can handle the view, we return it
			return availableProviders.get(0);
		} else {
			// otherwise we return one of those which have the highest priority
			return greatest(availableProviders).iterator().next();
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFComponentRegistry#addComponent(org.eclipse.emf.eef.runtime.services.EEFComponent, java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes" })
	public final synchronized void addComponent(EEFComponent component, Map<String, ?> properties) throws PriorityCircularityException {
		try {
			if (component instanceof EEFService<?>) {
				EEFComponentRegistryImpl clone = (EEFComponentRegistryImpl) this.clone();
				Node addedNode = clone.addNode((String) properties.get("component.name"), (EEFService<?>) component);
				List<String> prioritiesOver = extractPriorities(properties.get("priority.over"));
				for (String hasPriorityOver : prioritiesOver) {
					clone.addEdge(addedNode, clone.getOrCreateNode(hasPriorityOver));

				}
				if (clone.isAcyclic()) {
					Node newNode = addNode((String) properties.get("component.name"), (EEFService<?>) component);				
					for (String hasPriorityOver : prioritiesOver) {
						addEdge(newNode, getOrCreateNode(hasPriorityOver));

					}
					for (String serviceType : component.providedServices()) {
						EEFServiceStorage storage = storages.get(serviceType);
						if (storage == null) {
							storage = new EEFServiceStorage();
							storages.put(serviceType, storage);
						}
						storage.addService(component);
					}
					component.setComponentRegistry(this);
				} else {
					throw new PriorityCircularityException(component);
				}
			}
		} catch (CloneNotSupportedException e) {
			// Can't happen, I'm cloneable!
		}	
		
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFComponentRegistry#removeComponent(org.eclipse.emf.eef.runtime.services.EEFComponent)
	 */
	public final synchronized void removeComponent(final EEFComponent component) {
		services.inverse().remove(component);
		for (String serviceType : component.providedServices()) {
			@SuppressWarnings("rawtypes")
			EEFServiceStorage storage = storages.get(serviceType);
			storage.removeService(component);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		EEFComponentRegistryImpl result = new EEFComponentRegistryImpl();
		Map<Node, Node> mapping = Maps.newHashMap();
		for (Entry<String, Node> node : services.entrySet()) {
			Node addedNode = result.addNode(node.getKey(), node.getValue().getTarget());
			mapping.put(node.getValue(), addedNode);
		}
		for (Node node : services.values()) {
			for (Edge edge : node.getOutgoingEdges()) {
				result.addEdge(mapping.get(node), mapping.get(edge.getTarget()));
			}
		}
		return result;
	}

	private Node addNode(String id, EEFService<?> provider) {
		Node result = null;
		if (services.get(id) != null) {
			result = services.get(id);
			if (result.isProxy()) {
				result.resolve(provider);
			}
		} else {
			result = new Node(provider);
			services.put(id, result);
		}
		return result;
	}
	
	
	private Edge addEdge(Node node, Node target) {
		Edge result = new Edge(target);
		node.getOutgoingEdges().add(result);
		return result;
	}

	private List<String> extractPriorities(Object priorityProperty) {
		if (priorityProperty instanceof String) {
			if (((String)priorityProperty).length() > 0) {
				List<String> result = Lists.newArrayList();
				StringTokenizer st = new StringTokenizer((String)priorityProperty, ",");
				while (st.hasMoreElements()) {
					String nextPriority = (String) st.nextElement();
					result.add(nextPriority.trim());
				}
				return result;
			}
		}
		return Collections.emptyList();
	}

	private Node getOrCreateNode(String hasPriorityOver) {
		if (services.get(hasPriorityOver) != null) {
			return services.get(hasPriorityOver);
		} else {
			Node result = new Node(null);
			services.put(hasPriorityOver, result);
			return result;
		}
	}
	
	private boolean isAcyclic() {
		try {
			EEFComponentRegistryImpl clone = (EEFComponentRegistryImpl) clone();
			while (clone.getNodes().size() > 0) {
				if (clone.leafNodes().size() > 0) {
					List<Node> nodesToRemove = Lists.newArrayList(clone.leafNodes());
					for (Node leafNode : nodesToRemove) {
						clone.removeNode(leafNode);
					}
				} else {
					return false;
				}
			}
			return true;
			
		} catch (CloneNotSupportedException e) {
			//Not possible
		}
		return true;
	}
	
	private Collection<Node> getNodes() {
		return services.values();
	}

	private Collection<Node> leafNodes() {
		return Collections2.filter(services.values(), new Predicate<Node>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			public boolean apply(Node input) {
				return input.getOutgoingEdges().size() == 0;
			}
			
		});
	}
	
	private void removeNode(Node toRemove) {
		for (Node node : services.values()) {
			List<Edge> edgeToRemove = Lists.newArrayList();
			for (Edge edge : node.getOutgoingEdges()) {
				if (edge.getTarget() == toRemove) {
					edgeToRemove.add(edge);
				}
			}
			if (!edgeToRemove.isEmpty()) {
				node.removeAllEdges(edgeToRemove);
			}
		}
		services.values().remove(toRemove);
	}
	
	/**
	 * Returns the {@link VHPMetadata}s with the highest priority passed in input.
	 * @param input list of {@link VHPMetadata} to process.
	 * @return the {@link VHPMetadata}s with the highest priority.
	 */
	private Collection<EEFService<?>> greatest(Collection<EEFService<?>> input) {
		if (input.size() == 0 || input.size() == 1) {
			return input;
		}
		List<EEFService<?>> result = Lists.newArrayList(input);
		for (EEFService<?> vhpMetadata : input) {
			result.removeAll(connexity(vhpMetadata));
		}
		return result;
	}
	
	private Collection<EEFService<?>> connexity(EEFService<?> provider) {
		Node node = getAssociatedNode(provider);
		if (node != null) {
			List<EEFService<?>> result = Lists.newArrayList();
			for (Edge edge : node.getOutgoingEdges()) {
				result.add(edge.getTarget().getTarget());
				result.addAll(connexity(edge.getTarget().getTarget()));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
		
	}
	
	private Node getAssociatedNode(EEFService<?> provider) {
		for (Node node : services.values()) {
			if (node.getTarget() == provider) {
				return node;
			}
		}
		return null;
	}
	
	private static final class Node {
		
		EEFService<?> target;
		private List<Edge> outgoingEdges;

		public Node(EEFService<?> target) {
			this.target = target;
			outgoingEdges = Lists.newArrayList();
		}
		
		public EEFService<?> getTarget() {
			return target;
		}

		public void resolve(EEFService<?> target) {
			this.target = target;
		}

		public boolean isProxy() {
			return target == null;
		}

		public void removeAllEdges(List<Edge> edges) {
			outgoingEdges.removeAll(edges);
		}

		/**
		 * @return the outgoingEdges
		 */
		public List<Edge> getOutgoingEdges() {
			return outgoingEdges;
		}
		
	}
	
	private static final class Edge {
		Node target;

		public Edge(Node target) {
			this.target = target;
		}

		public Node getTarget() {
			return target;
		}
	}
	
	private static class EEFServiceStorage<T> {
		
		private List<EEFComponent> services;
		private DefaultService defaultService;
		
		/**
		 * 
		 */
		public EEFServiceStorage() {
			this.services = Lists.newArrayList();
			this.defaultService = null;
		}
		
		/**
		 * Adds a {@link EEFService} in the current storage. If this service implements {@link DefaultService}, it's stored in a separate way.
		 * @param service the {@link EEFService} to store.
		 */
		public void addService(EEFComponent service) {
			if (service instanceof DefaultService) {
				defaultService = (DefaultService) service;
			} else {
				services.add(service);
			}
		}
		
		/**
		 * Removes a {@link EEFService} in the current storage. 
		 * @param service the {@link EEFService} to store.
		 */
		public void removeService(EEFComponent service) {
			services.remove(service);
			if (defaultService == service) {
				defaultService = null;
			}
		}
		
		/**
		 * @param element
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<EEFService<T>> getServicesFor(T element) {
			List<EEFService<T>> result = Lists.newArrayList();
			for (EEFComponent service : services) {
				if (((EEFService<T>)service).serviceFor(element)) {
					result.add((EEFService<T>) service);
				}
			}
			if (result.size() == 0 && defaultService != null) {
				result.add((EEFService<T>) defaultService);
			}
			return result;
		}
		
	}
	
}
