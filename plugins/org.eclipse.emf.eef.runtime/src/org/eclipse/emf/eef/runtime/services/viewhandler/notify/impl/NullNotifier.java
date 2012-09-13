/**
 * 
 */
package org.eclipse.emf.eef.runtime.services.viewhandler.notify.impl;

import org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier;

/**
 * A null implementation of the {@link EEFNotifier} interface.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullNotifier implements EEFNotifier {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#notify(org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotification)
	 */
	public void notify(EEFNotification notification) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#clearViewNotification()
	 */
	public void clearViewNotification() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.viewhandler.notify.EEFNotifier#clearEditorNotification(java.lang.Object)
	 */
	public void clearEditorNotification(Object editor) {
		// Do nothing
	}

}
