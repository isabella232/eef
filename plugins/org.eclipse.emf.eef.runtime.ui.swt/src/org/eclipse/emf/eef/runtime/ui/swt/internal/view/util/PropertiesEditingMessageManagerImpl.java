package org.eclipse.emf.eef.runtime.ui.swt.internal.view.util;

import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;
import org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager;
import org.eclipse.swt.widgets.Display;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertiesEditingMessageManagerImpl implements PropertiesEditingMessageManager {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager#processMessage(org.eclipse.emf.eef.runtime.view.notify.EEFNotification)
	 */
	public void processMessage(final EEFNotification notification) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if (notification.getKind() == EEFNotification.ERROR) {
					updateError(notification.getMessage());
				} else if (notification.getKind() == EEFNotification.WARNING) {
					updateWarning(notification.getMessage());
				} else if (notification.getKind() == EEFNotification.LOCK) {
					updateLock(notification.getMessage());
				} else {
					updateStatus(notification.getMessage());
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.PropertiesEditingMessageManager#clearMessage()
	 */
	public void clearMessage() {
		updateStatus("");
	}

	protected abstract void updateStatus(final String message);

	protected void updateError(final String message) {
		updateStatus(message);
	}

	protected void updateWarning(final String message) {
		updateStatus(message);
	}

	protected void updateLock(final String message) {
		updateStatus(message);
	}
}
