/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ValidationBasedPropertyNotification extends ValidationBasedNotification implements EEFPropertyNotification {

	private Object editor;

	/**
	 * @param editor
	 * @param sourceDiagnostic
	 */
	public ValidationBasedPropertyNotification(Object editor, Diagnostic sourceDiagnostic) {
		super(sourceDiagnostic);
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFPropertyNotification#getEditor()
	 */
	public final Object getEditor() {
		return editor;
	}
	
	
}
