/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFPropertyLock extends EEFLock {

	/**
	 * @return the locked editor.
	 */
	Object getEditor();
	
}
