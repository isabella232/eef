/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import java.util.List;

import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public final class VHPMetadata {
	
	private ViewHandlerProvider handler;
	private List<VHPMetadata> hasPriorityOver;
	
	/**
	 * Creates a {@link VHPMetadata} mapping the given {@link VHPMetadata}. This handler can be <code>null</code>, in this case
	 * this node is a proxy.
	 * @param handler
	 */
	public VHPMetadata(ViewHandlerProvider handler) {
		this.handler = handler;
		this.hasPriorityOver = Lists.newArrayList();
	}
	
	/**
	 * @return the handler of this node.
	 */
	public ViewHandlerProvider viewHandlerProvider() {
		return handler;
	}

	/**
	 * Defines that the {@link ViewHandlerProvider} mapped by this node has priority over the one
	 * mapped by this method parameter.
	 * @param vhp a {@link VHPMetadata} representing a {@link VHPMetadata} with less priority.
	 */
	public void addPriorityOver(VHPMetadata vhp) {
		hasPriorityOver.add(vhp);
	}

	/**
	 * Defines is this node is a proxy or not.
	 * @return <code>true</code> is this node doesn't actually map a real {@link ViewHandlerProvider}.
	 */
	public boolean isProxy() {
		return handler == null;
	}

	
	/**
	 * Defines the real {@link ViewHandlerProvider} of this node if it's a proxy.
	 * @param handler the real {@link ViewHandlerProvider} of this node.
	 */
	public void resolve(ViewHandlerProvider handler) {
		this.handler = handler;
	}

	/**
	 * @return the hasPriorityOver
	 */
	public List<VHPMetadata> getHasPriorityOver() {
		return hasPriorityOver;
	}
	
	
}
