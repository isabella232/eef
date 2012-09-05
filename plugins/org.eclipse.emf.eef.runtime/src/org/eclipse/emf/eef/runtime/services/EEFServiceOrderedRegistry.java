/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import com.google.common.base.Function;
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
public class EEFServiceOrderedRegistry<ELEMENT, SERVICE extends EEFService<ELEMENT>> implements Cloneable {
	
	private BiMap<String, Node<ELEMENT, SERVICE>> services;
	
	public EEFServiceOrderedRegistry() {
		services = HashBiMap.create();
	}

	public final SERVICE getHighestProvider(ELEMENT element) {
		List<SERVICE> availableProviders = Lists.newArrayList();
		for (SERVICE provider : Collections2.transform(services.values(), new Function<Node<ELEMENT, SERVICE>, SERVICE>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Function#apply(java.lang.Object)
			 */
			public SERVICE apply(Node<ELEMENT, SERVICE> input) {
				return input.getTarget();
			}
			
		})) {
			if (provider.serviceFor(element)) {
				availableProviders.add(provider);
			}
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
	 * Adds a service in the registry.
	 * @param provider the service to add.
	 * @param properties properties of the service.
	 * @throws PriorityCircularityException if we add this service, a circularity is introduced in the priority tree.
	 */
	public final synchronized void addService(SERVICE provider, Map<String, ?> properties) throws PriorityCircularityException {
		try {
			@SuppressWarnings("unchecked")
			EEFServiceOrderedRegistry<ELEMENT, SERVICE> clone = (EEFServiceOrderedRegistry<ELEMENT, SERVICE>) this.clone();
			Node<ELEMENT, SERVICE> addedNode = clone.addNode((String) properties.get("component.name"), provider);
			List<String> prioritiesOver = extractPriorities(properties.get("priority.over"));
			for (String hasPriorityOver : prioritiesOver) {
				clone.addEdge(addedNode, clone.getOrCreateNode(hasPriorityOver));
				
			}
			if (clone.isAcyclic()) {
				Node<ELEMENT, SERVICE> newNode = addNode((String) properties.get("component.name"), provider);				
				for (String hasPriorityOver : prioritiesOver) {
					addEdge(newNode, getOrCreateNode(hasPriorityOver));
					
				}
			} else {
				throw new PriorityCircularityException(provider);
			}
		} catch (CloneNotSupportedException e) {
			// Can't append, I'm cloneable!
		}
		
	}
	
	/**
	 * Removes a service from the registry.
	 * @param service the service to remove.
	 */
	public final synchronized void removeService(final SERVICE service) {
		services.inverse().remove(service);
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		EEFServiceOrderedRegistry<ELEMENT, SERVICE> result = new EEFServiceOrderedRegistry<ELEMENT, SERVICE>();
		Map<Node<ELEMENT, SERVICE>, Node<ELEMENT, SERVICE>> mapping = Maps.newHashMap();
		for (Entry<String, Node<ELEMENT, SERVICE>> node : services.entrySet()) {
			Node<ELEMENT, SERVICE> addedNode = result.addNode(node.getKey(), node.getValue().getTarget());
			mapping.put(node.getValue(), addedNode);
		}
		for (Node<ELEMENT, SERVICE> node : services.values()) {
			for (Edge<ELEMENT, SERVICE> edge : node.getOutgoingEdges()) {
				result.addEdge(mapping.get(node), mapping.get(edge.getTarget()));
			}
		}
		return result;
	}

	private Node<ELEMENT, SERVICE> addNode(String id, SERVICE provider) {
		Node<ELEMENT, SERVICE> result = null;
		if (services.get(id) != null) {
			result = services.get(id);
			if (result.isProxy()) {
				result.resolve(provider);
			}
		} else {
			result = new Node<ELEMENT, SERVICE>(provider);
			services.put(id, result);
		}
		return result;
	}
	
	
	private Edge<ELEMENT, SERVICE> addEdge(Node<ELEMENT, SERVICE> node, Node<ELEMENT, SERVICE> target) {
		Edge<ELEMENT, SERVICE> result = new Edge<ELEMENT, SERVICE>(target);
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

	private Node<ELEMENT, SERVICE> getOrCreateNode(String hasPriorityOver) {
		if (services.get(hasPriorityOver) != null) {
			return services.get(hasPriorityOver);
		} else {
			Node<ELEMENT, SERVICE> result = new Node<ELEMENT, SERVICE>(null);
			services.put(hasPriorityOver, result);
			return result;
		}
	}
	
	private boolean isAcyclic() {
		try {
			@SuppressWarnings("unchecked")
			EEFServiceOrderedRegistry<ELEMENT, SERVICE> clone = (EEFServiceOrderedRegistry<ELEMENT, SERVICE>) clone();
			while (clone.getNodes().size() > 0) {
				if (clone.leafNodes().size() > 0) {
					List<Node<ELEMENT, SERVICE>> nodesToRemove = Lists.newArrayList(clone.leafNodes());
					for (Node<ELEMENT, SERVICE> leafNode : nodesToRemove) {
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
	
	private Collection<Node<ELEMENT, SERVICE>> getNodes() {
		return services.values();
	}

	private Collection<Node<ELEMENT, SERVICE>> leafNodes() {
		return Collections2.filter(services.values(), new Predicate<Node<ELEMENT, SERVICE>>() {

			/**
			 * {@inheritDoc}
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			public boolean apply(Node<ELEMENT, SERVICE> input) {
				return input.getOutgoingEdges().size() == 0;
			}
			
		});
	}
	
	private void removeNode(Node<ELEMENT, SERVICE> toRemove) {
		for (Node<ELEMENT, SERVICE> node : services.values()) {
			List<Edge<ELEMENT, SERVICE>> edgeToRemove = Lists.newArrayList();
			for (Edge<ELEMENT, SERVICE> edge : node.getOutgoingEdges()) {
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
	private Collection<SERVICE> greatest(Collection<SERVICE> input) {
		if (input.size() == 0 || input.size() == 1) {
			return input;
		}
		List<SERVICE> result = Lists.newArrayList(input);
		for (SERVICE vhpMetadata : input) {
			result.removeAll(connexity(vhpMetadata));
		}
		return result;
	}
	
	private Collection<SERVICE> connexity(SERVICE provider) {
		Node<ELEMENT, SERVICE> node = getAssociatedNode(provider);
		if (node != null) {
			List<SERVICE> result = Lists.newArrayList();
			for (Edge<ELEMENT, SERVICE> edge : node.getOutgoingEdges()) {
				result.add(edge.getTarget().getTarget());
				result.addAll(connexity(edge.getTarget().getTarget()));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
		
	}
	
	private Node<ELEMENT, SERVICE> getAssociatedNode(SERVICE provider) {
		for (Node<ELEMENT, SERVICE> node : services.values()) {
			if (node.getTarget() == provider) {
				return node;
			}
		}
		return null;
	}
	
	private static final class Node<ELEMENT, SERVICE extends EEFService<ELEMENT>> {
		
		SERVICE target;
		private List<Edge<ELEMENT, SERVICE>> outgoingEdges;

		public Node(SERVICE target) {
			this.target = target;
			outgoingEdges = Lists.newArrayList();
		}
		
		public SERVICE getTarget() {
			return target;
		}

		public void resolve(SERVICE target) {
			this.target = target;
		}

		public boolean isProxy() {
			return target == null;
		}

		public void removeAllEdges(List<Edge<ELEMENT, SERVICE>> edges) {
			outgoingEdges.removeAll(edges);
		}

		/**
		 * @return the outgoingEdges
		 */
		public List<Edge<ELEMENT, SERVICE>> getOutgoingEdges() {
			return outgoingEdges;
		}
		
	}
	
	private static final class Edge<ELEMENT, SERVICE extends EEFService<ELEMENT>> {
		Node<ELEMENT, SERVICE> target;

		public Edge(Node<ELEMENT, SERVICE> target) {
			this.target = target;
		}

		public Node<ELEMENT, SERVICE> getTarget() {
			return target;
		}
	}
	
}
