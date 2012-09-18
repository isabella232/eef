/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;

/**
 * A null implementation of the {@link EEFNotifier} interface.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullNotifier implements EEFNotifier {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
	 */
	public void notify(EEFNotification notification) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification()
	 */
	public void clearViewNotification() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
	 */
	public void clearEditorNotification(Object editor) {
		// Do nothing
	}

}
