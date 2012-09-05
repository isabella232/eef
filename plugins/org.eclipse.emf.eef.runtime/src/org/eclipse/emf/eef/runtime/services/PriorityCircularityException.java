/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class PriorityCircularityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5875085027608246401L;
	private Object handler;
	
	public PriorityCircularityException(Object provider) {
		this.handler = provider;
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
