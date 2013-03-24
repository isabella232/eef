/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.e4.services;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public interface PlatformRelatedUIUtils {

	/**
	 * Creates a new EEFViewer in the given parent.
	 * @param parent widget where to create the viewer.
	 * @return the created EEFViewer
	 */
	IEEFViewer createEEFViewer(Object parent);
	
	/**
	 * Opens a file selection dialog to choose a model.
	 * @param context the current {@link IEclipseContext}.
	 * @param fileExtensions filtered extension
	 * @return the path of the selected file.
	 */
	String selectModelFile(IEclipseContext context, String[] fileExtensions);
	
	/**
	 * Opens a dialogbox to display a message.
	 * @param kind the kind of message (error, warning, ...)
	 * @param title title of the dialog box.
	 * @param message the message to display.
	 */
	void openDialogBox(MessageKind kind, String title, String message);
	
	
	public enum MessageKind {
		
		ERROR,
		WARNING,
		INFO
		
	}
	
}
