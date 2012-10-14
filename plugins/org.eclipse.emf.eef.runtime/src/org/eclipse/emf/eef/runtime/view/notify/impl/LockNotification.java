/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class LockNotification implements EEFNotification {

	private String message;
	
	public LockNotification(String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getKind()
	 */
	public int getKind() {
		return EEFNotification.LOCK;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getMessage()
	 */
	public String getMessage() {
		return message;
	}

}
