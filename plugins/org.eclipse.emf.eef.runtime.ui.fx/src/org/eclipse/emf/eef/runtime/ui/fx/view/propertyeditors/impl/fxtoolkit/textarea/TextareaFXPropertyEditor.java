/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.textarea;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextArea;
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
public class TextareaFXPropertyEditor extends StandardFXPropertyEditor<TextArea> {

	protected TextArea text;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public TextareaFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.StandardFXPropertyEditor#createEditorContents(javafx.scene.layout.Pane)
	 */
	@Override
	protected void createEditorContents(Pane parent) {
		text = new TextArea();
		GridPane.setConstraints(text, 2, editorRowIndex(), 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
		parent.getChildren().add(text);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public TextArea getViewer() {
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
