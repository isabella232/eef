/**
 * 
 */
package org.eclipse.emf.eef.runtime.view.notify.impl;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.eef.runtime.view.notify.EEFNotification;

/**
 * An {@link EEFNotification} send from a EMF Validation diagnostic.
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 */
public class ValidationBasedNotification implements EEFNotification {

	private Diagnostic sourceDiagnostic;
	
	/**
	 * @param sourceDiagnostic the {@link Diagnostic} causing this notification.
	 */
	public ValidationBasedNotification(Diagnostic sourceDiagnostic) {
		this.sourceDiagnostic = sourceDiagnostic;
	}

	/**
	 * The tips here is the mapping between the {@link EEFNotification#ERROR}, {@link EEFNotification#WARNING} fields 
	 * and the {@link Diagnostic#ERROR}, {@link Diagnostic#WARNING}.     
	 * 
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getKind()
	 */
	public final int getKind() {
		return sourceDiagnostic.getSeverity();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.view.notify.EEFNotification#getMessage()
	 */
	public final String getMessage() {
		return sourceDiagnostic.getMessage();
	}

}
