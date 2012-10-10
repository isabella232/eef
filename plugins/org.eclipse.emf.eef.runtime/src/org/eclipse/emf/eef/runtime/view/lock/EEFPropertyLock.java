/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.lock;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface EEFPropertyLock extends EEFLock {

	/**
	 * @return the locked feature.
	 */
	EStructuralFeature getLockedFeature();
	
}
