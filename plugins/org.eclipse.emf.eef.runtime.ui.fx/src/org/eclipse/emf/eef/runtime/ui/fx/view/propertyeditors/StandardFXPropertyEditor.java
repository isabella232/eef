/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors;

import java.awt.Composite;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.fx.services.view.FXViewService;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public abstract class StandardFXPropertyEditor<CONTROL extends Control> implements FXPropertyEditorViewer<CONTROL> {
	
	protected PropertiesEditingView<Pane> view;
	protected ElementEditor elementEditor;
	private int index = -1;
	
	
	/**
	 * @param view {@link PropertiesEditingView} where the PropertyEditor is built.
	 * @param viewElement {@link ElementEditor} specifying the Property Editor.
	 */
	public StandardFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		this.view = view;
		this.elementEditor = elementEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Pane parent) {
		ViewService viewService = view.getViewService();
		FXViewService fxViewService = null;
		if (viewService instanceof FXViewService) {
			fxViewService = (FXViewService) viewService;
		}
		if (fxViewService != null) {
			Label label = fxViewService.createLabel(parent, elementEditor, elementEditor.getName());
			GridPane.setConstraints(label, 1, editorRowIndex(), 1, 1, HPos.RIGHT, VPos.CENTER);
		}
		createEditorContents(parent);
		if (fxViewService != null) {
			Label button = fxViewService.createHelpButton(parent, elementEditor);
			GridPane.setConstraints(button, 3, editorRowIndex(), 1, 1, HPos.RIGHT, VPos.CENTER);
		}
	}

	protected final int editorRowIndex() {
		if (index == -1) {
			ViewService viewService = view.getViewService();
			if (viewService instanceof FXViewService) {
				index = ((FXViewService) viewService).viewElementRow(elementEditor);
			}
		}
		return index;
	}

	/**
	 * Create the contents of the property editor in the owning Composite.
	 * @param parent the owning {@link Composite}.
	 */
	protected abstract void createEditorContents(Pane parent);
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		getViewer().setDisable(true);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		getViewer().setDisable(false);		
	}
	
}
