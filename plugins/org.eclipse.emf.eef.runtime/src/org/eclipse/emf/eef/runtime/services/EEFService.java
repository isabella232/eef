/**
 * 
 */
package org.eclipse.emf.eef.runtime.services;


/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFService<Element> {

	/**
	 * Specifies that the current service can apply for a given element.
	 * @param element the element to test.
	 * @return <code>true</code> if this service can proces the element.
	 */
	boolean serviceFor(Element element);
}
