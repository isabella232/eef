/**
 * 
 */
package org.eclipse.emf.eef.runtime.internal.services.viewhandler;

import org.eclipse.emf.eef.runtime.services.viewhandler.ViewHandlerProvider;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PriorityCircularityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875085027608246401L;
	
	private ViewHandlerProvider handler;

	public PriorityCircularityException(ViewHandlerProvider handler) {
		this.handler = handler;
	}

	/**
	 * {@inheritDoc}
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return "Unable to register the handler " + handler + " due to an circular priority configuration.";
	}

	
	
}
