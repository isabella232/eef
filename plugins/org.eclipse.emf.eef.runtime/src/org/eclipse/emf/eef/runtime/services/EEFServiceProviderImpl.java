/*******************************************************************************
 * Copyright (c) 2013 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
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
public class EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE extends EEFService<ELEMENT_TYPE>> implements EEFServiceProvider<ELEMENT_TYPE, SERVICE_TYPE> {

	private BiMap<String, Node<ELEMENT_TYPE, SERVICE_TYPE>> services;
	private List<SERVICE_TYPE> defaultServices;

	/**
	 * 
	 */
	public EEFServiceProviderImpl() {
		services = HashBiMap.create();
		defaultServices = Lists.newArrayList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceProvider#addService(org.eclipse.emf.eef.runtime.services.EEFService,
	 *      java.util.Map)
	 */
	public final synchronized void addService(SERVICE_TYPE service, Map<String, ?> properties) throws PriorityCircularityException {
		try {
			@SuppressWarnings("unchecked")
			EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE> clone = (EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE>) this.clone();
			Node<ELEMENT_TYPE, SERVICE_TYPE> addedNode = clone.addNode((String) properties.get("component.name"), service);
			List<String> prioritiesOver = extractPriorities(properties.get("priority.over"));
			for (String hasPriorityOver : prioritiesOver) {
				clone.addEdge(addedNode, clone.getOrCreateNode(hasPriorityOver));
				
			}
			if (clone.isAcyclic()) {
				Node<ELEMENT_TYPE, SERVICE_TYPE> newNode = addNode((String) properties.get("component.name"), service);
				for (String hasPriorityOver : prioritiesOver) {
					addEdge(newNode, getOrCreateNode(hasPriorityOver));
				}
				if (service instanceof DefaultService) {
					List<SERVICE_TYPE> services = Lists.newArrayList();
					for (SERVICE_TYPE defaultService : defaultServices) {
						services.add(defaultService);
					}
					services.add(service);
					Collection<SERVICE_TYPE> greatest = greatest(services);
					if (greatest.contains(service)) {
						defaultServices.add(service);
					}
					for (SERVICE_TYPE defaultService : Lists.newArrayList(defaultServices)) {
						if (!greatest.contains(defaultService)) {
							defaultServices.remove(defaultService);
						}
					}

				}
			} else {
				throw new PriorityCircularityException(service);
			}
		} catch (CloneNotSupportedException e) {
			// Can't happen, I'm cloneable!
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceProvider#removeService(org.eclipse.emf.eef.runtime.services.EEFService)
	 */
	public void removeService(SERVICE_TYPE service) {
		services.inverse().remove(service);
		if (defaultServices.contains(service)) {
			defaultServices.remove(service);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.services.EEFServiceProvider#getService(java.lang.Object)
	 */
	public <ANY_SUBTYPE_OF_ELEMENT extends ELEMENT_TYPE> SERVICE_TYPE getService(ANY_SUBTYPE_OF_ELEMENT element) {
		List<SERVICE_TYPE> availableProviders = Lists.newArrayList();
		availableProviders = getServicesFor(element);
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
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE> result = new EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE>();
		Map<Node<ELEMENT_TYPE, SERVICE_TYPE>, Node<ELEMENT_TYPE, SERVICE_TYPE>> mapping = Maps.newHashMap();
		for (Entry<String, Node<ELEMENT_TYPE, SERVICE_TYPE>> node : services.entrySet()) {
			Node<ELEMENT_TYPE, SERVICE_TYPE> addedNode = result.addNode(node.getKey(), node.getValue().getTarget());
			mapping.put(node.getValue(), addedNode);
		}
		for (Node<ELEMENT_TYPE, SERVICE_TYPE> node : services.values()) {
			for (Edge<ELEMENT_TYPE, SERVICE_TYPE> edge : node.getOutgoingEdges()) {
				result.addEdge(mapping.get(node), mapping.get(edge.getTarget()));
			}
		}
		return result;
	}

	public List<SERVICE_TYPE> getServicesFor(ELEMENT_TYPE element) {
		List<SERVICE_TYPE> result = Lists.newArrayList();
		// First we're looking for an appropriate service which isn't a default
		// service.
		for (Node<ELEMENT_TYPE, SERVICE_TYPE> node : services.values()) {
			SERVICE_TYPE service = node.getTarget();
			if (service.serviceFor(element) && !(service instanceof DefaultService)) {
				result.add(service);
			}
		}
		// If no service is available, we're looking for a default service
		if (result.size() == 0 && !defaultServices.isEmpty()) {
			result.add(defaultServices.get(0));
		}
		return result;
	}

	protected Collection<SERVICE_TYPE> getAllServices() {
		Function<Node<ELEMENT_TYPE, SERVICE_TYPE>, SERVICE_TYPE> node2Service = new Function<Node<ELEMENT_TYPE, SERVICE_TYPE>, SERVICE_TYPE>() {
			public SERVICE_TYPE apply(Node<ELEMENT_TYPE, SERVICE_TYPE> input) {
				return input.getTarget();
			}
		};
		return Collections2.transform(services.values(), node2Service);
	}

	private Node<ELEMENT_TYPE, SERVICE_TYPE> addNode(String id, SERVICE_TYPE provider) {
		Node<ELEMENT_TYPE, SERVICE_TYPE> result = null;
		if (services.get(id) != null) {
			result = services.get(id);
			if (result.isProxy()) {
				result.resolve(provider);
			}
		} else {
			result = new Node<ELEMENT_TYPE, SERVICE_TYPE>(provider);
			services.put(id, result);
		}
		return result;
	}

	private Edge<ELEMENT_TYPE, SERVICE_TYPE> addEdge(Node<ELEMENT_TYPE, SERVICE_TYPE> node, Node<ELEMENT_TYPE, SERVICE_TYPE> target) {
		Edge<ELEMENT_TYPE, SERVICE_TYPE> result = new Edge<ELEMENT_TYPE, SERVICE_TYPE>(target);
		node.getOutgoingEdges().add(result);
		return result;
	}

	private List<String> extractPriorities(Object priorityProperty) {
		if (priorityProperty instanceof String) {
			if (((String) priorityProperty).length() > 0) {
				List<String> result = Lists.newArrayList();
				StringTokenizer st = new StringTokenizer((String) priorityProperty, ",");
				while (st.hasMoreElements()) {
					String nextPriority = (String) st.nextElement();
					result.add(nextPriority.trim());
				}
				return result;
			}
		}
		return Collections.emptyList();
	}

	private Node<ELEMENT_TYPE, SERVICE_TYPE> getOrCreateNode(String hasPriorityOver) {
		if (services.get(hasPriorityOver) != null) {
			return services.get(hasPriorityOver);
		} else {
			Node<ELEMENT_TYPE, SERVICE_TYPE> result = new Node<ELEMENT_TYPE, SERVICE_TYPE>(null);
			services.put(hasPriorityOver, result);
			return result;
		}
	}

	private boolean isAcyclic() {
		try {
			@SuppressWarnings("unchecked")
			EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE> clone = (EEFServiceProviderImpl<ELEMENT_TYPE, SERVICE_TYPE>) clone();
			while (clone.getNodes().size() > 0) {
				if (clone.leafNodes().size() > 0) {
					List<Node<ELEMENT_TYPE, SERVICE_TYPE>> nodesToRemove = Lists.newArrayList(clone.leafNodes());
					for (Node<ELEMENT_TYPE, SERVICE_TYPE> leafNode : nodesToRemove) {
						clone.removeNode(leafNode);
					}
				} else {
					return false;
				}
			}
			return true;

		} catch (CloneNotSupportedException e) {
			// Not possible
		}
		return true;
	}

	private Collection<Node<ELEMENT_TYPE, SERVICE_TYPE>> getNodes() {
		return services.values();
	}

	private Collection<Node<ELEMENT_TYPE, SERVICE_TYPE>> leafNodes() {
		return Collections2.filter(services.values(), new Predicate<Node<ELEMENT_TYPE, SERVICE_TYPE>>() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see com.google.common.base.Predicate#apply(java.lang.Object)
			 */
			public boolean apply(Node<ELEMENT_TYPE, SERVICE_TYPE> input) {
				return input.getOutgoingEdges().size() == 0;
			}

		});
	}

	private void removeNode(Node<ELEMENT_TYPE, SERVICE_TYPE> toRemove) {
		for (Node<ELEMENT_TYPE, SERVICE_TYPE> node : services.values()) {
			List<Edge<ELEMENT_TYPE, SERVICE_TYPE>> edgeToRemove = Lists.newArrayList();
			for (Edge<ELEMENT_TYPE, SERVICE_TYPE> edge : node.getOutgoingEdges()) {
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
	 * Returns the {@link VHPMetadata}s with the highest priority passed in
	 * input.
	 * 
	 * @param input
	 *            list of {@link VHPMetadata} to process.
	 * @return the {@link VHPMetadata}s with the highest priority.
	 */
	private Collection<SERVICE_TYPE> greatest(Collection<SERVICE_TYPE> input) {
		if (input.size() == 0 || input.size() == 1) {
			return input;
		}
		List<SERVICE_TYPE> result = Lists.newArrayList(input);
		for (SERVICE_TYPE service : input) {
			result.removeAll(connexity(service));
		}
		return result;
	}

	private Collection<SERVICE_TYPE> connexity(SERVICE_TYPE provider) {
		Node<ELEMENT_TYPE, SERVICE_TYPE> node = getAssociatedNode(provider);
		if (node != null) {
			List<SERVICE_TYPE> result = Lists.newArrayList();
			for (Edge<ELEMENT_TYPE, SERVICE_TYPE> edge : node.getOutgoingEdges()) {
				result.add(edge.getTarget().getTarget());
				result.addAll(connexity(edge.getTarget().getTarget()));
			}
			return result;
		} else {
			return Collections.emptyList();
		}

	}

	private Node<ELEMENT_TYPE, SERVICE_TYPE> getAssociatedNode(EEFService<?> provider) {
		for (Node<ELEMENT_TYPE, SERVICE_TYPE> node : services.values()) {
			if (node.getTarget() == provider) {
				return node;
			}
		}
		return null;
	}

	private static final class Node<ELEMENT_TYPE, SERVICE_TYPE extends EEFService<ELEMENT_TYPE>> {

		SERVICE_TYPE target;
		private List<Edge<ELEMENT_TYPE, SERVICE_TYPE>> outgoingEdges;

		public Node(SERVICE_TYPE target) {
			this.target = target;
			outgoingEdges = Lists.newArrayList();
		}

		public SERVICE_TYPE getTarget() {
			return target;
		}

		public void resolve(SERVICE_TYPE target) {
			this.target = target;
		}

		public boolean isProxy() {
			return target == null;
		}

		public void removeAllEdges(List<Edge<ELEMENT_TYPE, SERVICE_TYPE>> edges) {
			outgoingEdges.removeAll(edges);
		}

		/**
		 * @return the outgoingEdges
		 */
		public List<Edge<ELEMENT_TYPE, SERVICE_TYPE>> getOutgoingEdges() {
			return outgoingEdges;
		}

	}

	private static final class Edge<ELEMENT_TYPE, SERVICE_TYPE extends EEFService<ELEMENT_TYPE>> {
		Node<ELEMENT_TYPE, SERVICE_TYPE> target;

		public Edge(Node<ELEMENT_TYPE, SERVICE_TYPE> target) {
			this.target = target;
		}

		public Node<ELEMENT_TYPE, SERVICE_TYPE> getTarget() {
			return target;
		}
	}

}
