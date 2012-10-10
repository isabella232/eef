/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors;

import org.eclipse.emf.eef.runtime.view.lock.EEFPropertyLock;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface PropertyEditorViewer<VIEWER extends Viewer> {

	/**
	 * @return the main {@link Viewer} of this editor.
	 */
	VIEWER getViewer();

	/**
	 * Locks the current editor towards the given {@link EEFPropertyLock}.
	 * @param lock lock configuration.
	 */
	void lock(EEFPropertyLock lock);
	
	/**
	 * Unlocks the current editor.
	 */
	void unlock();
}
