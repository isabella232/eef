/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.handler.exceptions;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ViewConstructionException extends Exception {

	/**
	 * Serializtion ID
	 */
	private static final long serialVersionUID = 5957853568765322237L;

	public ViewConstructionException() {
		super();
	}

	public ViewConstructionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ViewConstructionException(String arg0) {
		super(arg0);
	}

	public ViewConstructionException(Throwable arg0) {
		super(arg0);
	}	
	
}
