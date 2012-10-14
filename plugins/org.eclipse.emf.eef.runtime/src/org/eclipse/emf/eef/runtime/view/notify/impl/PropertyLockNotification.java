/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class PropertyLockNotification implements EEFPropertyNotification {

	private Object editor;
	private String message;

	public PropertyLockNotification(Object editor, String message) {
		this.editor = editor;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getKind()
	 */
	public int getKind() {
		return LOCK;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification#getEditor()
	 */
	public Object getEditor() {
		return editor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getMessage()
	 */
	public String getMessage() {
		return message;
	}

}
