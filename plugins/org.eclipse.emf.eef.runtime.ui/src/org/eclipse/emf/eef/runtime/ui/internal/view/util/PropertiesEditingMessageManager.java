package org.eclipse.emf.eef.runtime.ui.internal.view.util;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.eef.runtime.notify.PropertiesEditingEvent;
import org.eclipse.emf.eef.runtime.notify.PropertiesValidationEditingEvent;
import org.eclipse.swt.widgets.Display;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class PropertiesEditingMessageManager {

	public void processMessage(PropertiesEditingEvent event) {
		if (event instanceof PropertiesValidationEditingEvent) {
			final Diagnostic diag = ((PropertiesValidationEditingEvent)event).getDiagnostic();
			if (diag != null) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						String message = computeMessage(diag);
						if (diag.getSeverity() == Diagnostic.ERROR) {
							updateError(message);
						} else if (diag.getSeverity() == Diagnostic.WARNING) {
							updateWarning(message);
						} else {
							updateStatus(message);
						}
					}
				});
			} else {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						updateStatus(null);
					}
				});
			}
		}
	}

	protected abstract void updateStatus(final String message);

	protected void updateError(final String message) {
		updateStatus(message);
	}

	protected void updateWarning(final String message) {
		updateStatus(message);
	}

	private String computeMessage(Diagnostic diag) {
		if (diag.getSeverity() == Diagnostic.OK) {
			return "";
		} else {
			for (Diagnostic child : diag.getChildren()) {
				if (child.getSeverity() != Diagnostic.OK) {
					if (child.getChildren().isEmpty()) {
						return child.getMessage();
					}
					return computeMessage(child);
				}
			}
		}
		return diag.getMessage();
	}

}
