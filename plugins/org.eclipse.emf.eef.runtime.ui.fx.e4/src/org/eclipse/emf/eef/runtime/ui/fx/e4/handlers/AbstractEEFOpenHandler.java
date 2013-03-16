/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.e4.handlers;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.eef.runtime.ui.fx.e4.utils.EditingInput;
import org.eclipse.emf.eef.runtime.ui.fx.e4.utils.impl.URIEditingInput;
import org.eclipse.emf.eef.runtime.ui.fx.e4.utils.impl.VoidEditingInput;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
@SuppressWarnings("restriction")
public abstract class AbstractEEFOpenHandler extends AbstractEEFOpenViewHandler {

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.e4.handlers.AbstractEEFOpenViewHandler#getEditingInput(org.eclipse.e4.core.contexts.IEclipseContext, org.eclipse.e4.ui.model.application.ui.basic.MPart, javafx.stage.Stage)
	 */
	protected EditingInput getEditingInput(IEclipseContext context, MPart mPart, Stage stage) {
		EditingInput editingInput = null;
		FileChooser fileChooser = new FileChooser();
		for (String ext : getFilterExtensions()) {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(ext + " files", ext);
			fileChooser.getExtensionFilters().add(extFilter);
			
		}
         
        //Show save file dialog
        File file = fileChooser.showOpenDialog(stage);
        String path = file.getAbsolutePath();
		if (path != null && !path.isEmpty()) {
			URI fileURI = URI.createFileURI(path);
			AdapterFactoryEditingDomain editingDomain = getOrCreateEditingDomain(context, fileURI);
			Resource resource = editingDomain.getResourceSet().getResource(fileURI, true);
			if (resource == null || resource.getContents().isEmpty()) {
				//"Invalid conference data", 
				openErrorDialog("The application what unable to open the conference descriptor file");
				editingInput = new VoidEditingInput();
			} else {				
				editingInput = new URIEditingInput(fileURI, editingDomain);
			}
		} else {
			editingInput = new VoidEditingInput();			
		}
		context.set(EditingInput.class, editingInput);
		return editingInput;
	}

	/**
	 * Defines the filter extensions to use in the FileDialog.
	 * @return the filter extensions.
	 */
	protected String[] getFilterExtensions() {
		List<String> empty = Collections.emptyList(); 
		return (String[]) empty.toArray();
	}
	
	/**
	 * Gets (and populates if needed) an EditingDomain in the given eclipse context on the given key.
	 * @param context the {@link IEclipseContext} to populate.
	 * @param uri {@link URI} of the edited file.
	 * @return the created editingDomain.
	 */
	public AdapterFactoryEditingDomain getOrCreateEditingDomain(IEclipseContext context, URI uri) {
		EditingInput editingInput = context.get(EditingInput.class);
		AdapterFactoryEditingDomain editingDomain;
		if (editingInput != null && editingInput.getEditingDomain() instanceof AdapterFactoryEditingDomain) {
			editingDomain = (AdapterFactoryEditingDomain) editingInput.getEditingDomain();
		} else {
			editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());
		}
		return editingDomain;
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
