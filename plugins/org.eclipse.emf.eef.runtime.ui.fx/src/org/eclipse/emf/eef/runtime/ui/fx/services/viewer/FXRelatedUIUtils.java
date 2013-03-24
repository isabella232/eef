/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.services.viewer;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

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
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#selectModelFile(java.lang.Object, java.lang.String[])
	 */
	public String selectModelFile(Object graphicalOwner, String[] fileExtensions) {
		FileChooser fileChooser = new FileChooser();
		for (String ext : fileExtensions) {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ext + " files", ext);
			fileChooser.getExtensionFilters().add(extFilter);
			
		}
        //Show save file dialog
        File file = fileChooser.showOpenDialog((Window) graphicalOwner);
        return file.getAbsolutePath();
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#openDialogBox(org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils.MessageKind, java.lang.String, java.lang.String)
	 */
	public void openDialogBox(MessageKind kind, String title, String message) {
		openErrorDialog("The application what unable to open the conference descriptor file");		
	}

	public void openErrorDialog(String msg) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
		    children(new Text(msg), new Button("Ok.")).
		    alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.show();
	}
}
