/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.services.viewer;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import org.eclipse.emf.eef.runtime.ui.fx.viewer.FXViewer;
import org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXRelatedUIUtils implements PlatformRelatedUIUtils {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#createEEFViewer(java.lang.Object)
	 */
	public IEEFViewer createEEFViewer(Object parent) {
		FXViewer result = new FXViewer();
		if (parent instanceof BorderPane) {
			((BorderPane) parent).setCenter(result.getControl());
		} else if (parent instanceof GridPane) {
			((GridPane) parent).getChildren().add(result.getControl());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#selectModelFile(java.lang.String[])
	 */
	public String selectModelFile(String[] fileExtensions) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#openDialogBox(org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils.MessageKind, java.lang.String, java.lang.String)
	 */
	public void openDialogBox(MessageKind kind, String title, String message) {

	}

}
