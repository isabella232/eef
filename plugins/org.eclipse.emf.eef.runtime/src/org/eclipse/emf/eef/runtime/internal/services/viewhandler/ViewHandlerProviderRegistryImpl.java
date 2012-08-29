/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;
import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class ViewHandlerProviderRegistryImpl implements ViewHandlerProviderRegistry {

	protected final BiMap<String, VHPMetadata> services; 
	
	/**
	 * Default constructor. 
	 */
	public ViewHandlerProviderRegistryImpl() {
		services = HashBiMap.create();
	}	

	public synchronized void addService(final ViewHandlerProvider handler, Map<?, ?> properties) throws PriorityCircularityException {
		String id = (String) properties.get("component.name");
		VHPMetadata newVHPMetadata = services.get(id);
		if (newVHPMetadata != null && newVHPMetadata.isProxy()) {
			newVHPMetadata.resolve(handler);
		} else {
			newVHPMetadata = new VHPMetadata(handler);
		}
		List<String> prioritiesOver = extractPriorities(properties.get("priority.over"));
		for (String priorityOver : prioritiesOver) {
			VHPMetadata vhpMetadata = services.get(priorityOver);
			if (vhpMetadata == null) {
				vhpMetadata = new VHPMetadata(null);
				services.put(priorityOver, vhpMetadata);
			}
			newVHPMetadata.addPriorityOver(vhpMetadata);
		}
		if (testCyclicity(newVHPMetadata)) {
			services.put(id, newVHPMetadata);			
		} else {
			throw new PriorityCircularityException(handler);
		}
	}

	public synchronized void removeService(final ViewHandlerProvider handler) {
		services.inverse().remove(handler);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProviderRegistry#getViewHandlerProvider(java.lang.Object)
	 */
	public ViewHandlerProvider getViewHandlerProvider(Object element) {
		List<VHPMetadata> availableProviders = Lists.newArrayList();
		for (VHPMetadata provider : services.values()) {
			if (provider.viewHandlerProvider().serviceFor(element)) {
				availableProviders.add(provider);
			}
		}
		if (availableProviders.size() == 0) {			
			// If no registered provider can handle the view, we return null 
			return null;
		} else if (availableProviders.size() == 1) {
			// If only one provider can handle the view, we return it
			return availableProviders.get(0).viewHandlerProvider();
		} else {
			// otherwise we return one of those which have the highest priority
			return toGraph().greatest(availableProviders).iterator().next().viewHandlerProvider();
		}
	}

	private PriorityGraph toGraph() {
		PriorityGraph result = new PriorityGraph();
		for (VHPMetadata vhp : services.values()) {
			result.createNode(vhp);
		}
		for (VHPMetadata vhp : services.values()) {
			for (VHPMetadata targetVHP : vhp.getHasPriorityOver()) {
				result.createEdge(vhp, targetVHP);
			}
		}
		return result;
	}
	
	private boolean testCyclicity(VHPMetadata vhp) {
		PriorityGraph graph = toGraph();
		try {
			PriorityGraph clone = (PriorityGraph) graph.clone();
			clone.createNode(vhp);
			for (VHPMetadata targetVHP : vhp.getHasPriorityOver()) {
				if (clone.isAcyclicWith(vhp, targetVHP)) {
					clone.createEdge(vhp, targetVHP);
				} else {
					return false;
				}
			}
		} catch (CloneNotSupportedException e) {
		}
		return true;
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
}
