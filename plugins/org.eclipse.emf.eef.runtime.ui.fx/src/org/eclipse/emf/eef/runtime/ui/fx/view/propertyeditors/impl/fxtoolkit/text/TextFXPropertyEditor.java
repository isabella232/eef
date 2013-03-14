/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.text;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextFXPropertyEditor implements FXPropertyEditorViewer<TextField> {

	private PropertiesEditingView<Pane> view;
	private ElementEditor elementEditor;

	protected TextField text;

	/**
	 * @param view
	 * @param viewElement
	 */
	public TextFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer#build(javafx.scene.layout.Pane)
	 */
	public void build(Pane parent) {
		//TODO: trouver l'index ligne.
		ViewService viewService = view.getViewService();
		FXViewService swtViewService = null;
		if (viewService instanceof FXViewService) {
			swtViewService = (FXViewService) viewService;
		}
		if (swtViewService != null) {
			Label label = swtViewService.createLabel(parent, elementEditor, elementEditor.getName());
			GridPane.setColumnIndex(label, 1);
		}
		text = new TextField();
		GridPane.setColumnIndex(text, 2);
		parent.getChildren().add(text);
		if (swtViewService != null) {
			Control button = swtViewService.createHelpButton(parent, elementEditor);
			GridPane.setColumnIndex(button, 3);
		}
	}

	public TextField getViewer() {
		return text;
	}

	public void lock() {
		text.setEditable(false);
	}

	public void unlock() {
		text.setEditable(false);		
	}

	
}
