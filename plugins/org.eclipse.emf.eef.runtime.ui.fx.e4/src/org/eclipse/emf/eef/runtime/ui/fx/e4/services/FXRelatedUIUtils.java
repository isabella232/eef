/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.e4.services;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.eef.runtime.ui.fx.viewer.FXViewer;
import org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils;
import org.eclipse.emf.eef.runtime.ui.viewer.IEEFViewer;
import at.bestsolution.efxclipse.runtime.workbench.renderers.fx.DefPartRenderer.PartImpl;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public class FXRelatedUIUtils implements PlatformRelatedUIUtils {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.services.viewer.PlatformRelatedUIUtils#createEEFViewer(java.lang.Object)
	 */
	public IEEFViewer createEEFViewer(Object parent) {
		FXViewer result = new FXViewer();
		if (parent instanceof PartImpl) {
			PartImpl partImpl = (PartImpl)parent;
			BorderPane widget = partImpl.getWidget();
			widget.setCenter(result.getControl());
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.platform.e4.services.PlatformRelatedUIUtils#selectModelFile(org.eclipse.e4.core.contexts.IEclipseContext, java.lang.String[])
	 */
	public String selectModelFile(IEclipseContext context, String[] fileExtensions) {
		FileChooser fileChooser = new FileChooser();
		for (String ext : fileExtensions) {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ext + " files", ext);
			fileChooser.getExtensionFilters().add(extFilter);
			
		}
		Stage stage = context.get(Stage.class);
		if (stage!= null) {
			//Show save file dialog
			File file = fileChooser.showOpenDialog(stage);
			return file.getAbsolutePath();
		} else {
			return null;
		}
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
