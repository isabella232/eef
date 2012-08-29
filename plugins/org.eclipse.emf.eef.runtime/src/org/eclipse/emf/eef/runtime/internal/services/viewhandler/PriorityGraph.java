/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class PriorityGraph implements Cloneable {
	
	private Map<VHPMetadata, Node> nodes;

	public PriorityGraph() {
		nodes = Maps.newHashMap();
	}
	
	public void createNode(VHPMetadata vhp) {
		nodes.put(vhp, new Node(vhp));
	}
		
	public void createEdge(VHPMetadata src, VHPMetadata dest) {
		nodes.get(src).addEdge(new Edge(nodes.get(dest)));
	}
	
	private Collection<Node> leafNodes() {
		return Collections2.filter(nodes.values(), new Predicate<Node>() {

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
		for (Node node : nodes.values()) {
			List<Edge> edgeToRemove = Lists.newArrayList();
			for (Edge edge : node.getOutgoingEdges()) {
				if (edge.target == toRemove) {
					edgeToRemove.add(edge);
				}
			}
			if (!edgeToRemove.isEmpty()) {
				node.removeAllEdges(edgeToRemove);
			}
		}
		nodes.remove(toRemove.target);
	}
	
	/**
	 * {@inheritDoc}
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		PriorityGraph result = new PriorityGraph();
		for (Node node : nodes.values()) {
			result.createNode(node.target);
		}
		for (Node node : nodes.values()) {
			for (Edge edge : node.getOutgoingEdges()) {
				result.createEdge(node.target, edge.target.target);
			}
		}
		return result;
	}

	/**
	 * Checks that if we add an edge between src and dest, the graph is always acyclic.
	 * @param src source Node.
	 * @param dest destination node.
	 * @return <code>true</code> if we can add this edge.
	 */
	public boolean isAcyclicWith(VHPMetadata src, VHPMetadata dest) {
		try {
			PriorityGraph clone = (PriorityGraph) clone();
			clone.createEdge(src, dest);
			while (clone.nodes.size() > 0) {
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
	

	/**
	 * Returns the {@link VHPMetadata}s with the highest priority passed in input.
	 * @param input list of {@link VHPMetadata} to process.
	 * @return the {@link VHPMetadata}s with the highest priority.
	 */
	public Collection<VHPMetadata> greatest(Collection<VHPMetadata> input) {
		if (input.size() == 0 || input.size() == 1) {
			return input;
		}
		List<VHPMetadata> result = Lists.newArrayList(input);
		for (VHPMetadata vhpMetadata : input) {
			result.removeAll(connexity(vhpMetadata));
		}
		return result;
	}
	
	
	public Collection<VHPMetadata> connexity(VHPMetadata input) {
		Node node = nodes.get(input);
		if (node != null) {
			List<VHPMetadata> result = Lists.newArrayList();
			for (Edge edge : node.getOutgoingEdges()) {
				result.add(edge.target.target);
				result.addAll(connexity(edge.target.target));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
		
	}
	

	private final class Node {
		
		VHPMetadata target;
		private List<Edge> outgoingEdges;

		public Node(VHPMetadata target) {
			this.target = target;
			outgoingEdges = Lists.newArrayList();
		}
		
		public void addEdge(Edge edge) {
			outgoingEdges.add(edge);
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


	private final class Edge {
		Node target;

		public Edge(Node target) {
			this.target = target;
		}
		
	}
}