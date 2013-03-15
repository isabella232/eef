/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.text;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.StandardFXPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class TextFXPropertyEditor extends StandardFXPropertyEditor<TextField> {

	protected TextField text;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public TextFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.StandardFXPropertyEditor#createEditorContents(javafx.scene.layout.Pane)
	 */
	@Override
	protected void createEditorContents(Pane parent) {
		text = new TextField();
		GridPane.setConstraints(text, 2, editorRowIndex(), 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
		parent.getChildren().add(text);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public TextField getViewer() {
		return text;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.StandardFXPropertyEditor#lock()
	 */
	public void lock() {
		text.setEditable(false);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.StandardFXPropertyEditor#unlock()
	 */
	public void unlock() {
		text.setEditable(false);		
	}

	
}
