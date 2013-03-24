/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e4.services;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFContentProvider;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EEFViewer;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public class SWTRelatedUIUtils implements PlatformRelatedUIUtils {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils#createEEFViewer(java.lang.Object)
	 */
	public IEEFViewer createEEFViewer(Object parent) {
		if (parent instanceof Composite) {
			EEFViewer result = new EEFViewer((Composite) parent, SWT.NONE);
			result.setContentProvider(new EEFContentProvider());
			return result;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils#selectModelFile(org.eclipse.e4.core.contexts.IEclipseContext, java.lang.String[])
	 */
	public String selectModelFile(IEclipseContext context, String[] fileExtensions) {
		Shell shell;
		if (context.get(Shell.class) != null) {
			shell = context.get(Shell.class);
		} else {
			shell = new Shell();
		}
		FileDialog dialog = new FileDialog(shell);
		if (fileExtensions != null && fileExtensions.length > 0) {
			dialog.setFilterExtensions(fileExtensions);
		}
		return dialog.open();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils#openDialogBox(org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils.MessageKind, java.lang.String)
	 */
	public void openDialogBox(MessageKind kind, String title, String message) {
		switch (kind) {
		case ERROR:
			MessageDialog.openError(new Shell(), title, message);
			break;
		case WARNING:
			MessageDialog.openWarning(new Shell(), title, message);
			break;
		case INFO:
			MessageDialog.openInformation(new Shell(), title, message);
			break;
		default:
			break;
		}
	}

}
