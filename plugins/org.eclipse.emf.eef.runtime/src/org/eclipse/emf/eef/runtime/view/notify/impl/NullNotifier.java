/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotifier;

/**
 * A null implementation of the {@link EEFNotifier} interface.
 * 
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class NullNotifier implements EEFNotifier, DefaultService {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.services.EEFService#serviceFor(java.lang.Object)
	 */
	public boolean serviceFor(Object element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#notify(java.lang.Object, org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
	 */
	public void notify(Object view, EEFNotification notification) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearViewNotification(java.lang.Object)
	 */
	public void clearViewNotification(Object view) {
		// Do nothing		
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotifier#clearEditorNotification(java.lang.Object, java.lang.Object)
	 */
	public void clearEditorNotification(Object view, Object editor) {
		// Do nothing
	}


	
}
