/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.checkbox;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class CheckboxFXPropertyEditor implements FXPropertyEditorViewer<CheckBox> {

	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	protected CheckBox checkbox;
	
	/**
	 * @param view
	 * @param viewElement
	 */
	public CheckboxFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	public CheckBox getViewer() {
		return checkbox;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer#build(javafx.scene.layout.Pane)
	 */
	public void build(Pane parent) {
		ViewService viewService = view.getViewService();
		if (viewService instanceof FXViewService) {
			FXViewService fxViewService = (FXViewService)viewService;
			checkbox = new CheckBox(viewService.getDescription(elementEditor, elementEditor.getName()));
			GridPane.setConstraints(checkbox, 1, fxViewService.viewElementRow(elementEditor), 2, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
			parent.getChildren().add(checkbox);
			Control button = ((FXViewService) viewService).createHelpButton(parent, elementEditor);
			GridPane.setConstraints(button, 3, fxViewService.viewElementRow(elementEditor), 1, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		checkbox.setDisable(true);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		checkbox.setDisable(false);
	}

}
