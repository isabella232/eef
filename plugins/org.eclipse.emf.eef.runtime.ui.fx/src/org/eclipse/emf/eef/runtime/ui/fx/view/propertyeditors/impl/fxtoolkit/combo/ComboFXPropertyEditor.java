/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
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
public class ComboFXPropertyEditor extends StandardFXPropertyEditor<ComboBox<Object>> implements ComboUIPropertyEditor {

	protected ComboBox<Object> combo;
	private ComboContentProvider contentProvider;

	/**
	 * @param view
	 * @param viewElement
	 */
	public ComboFXPropertyEditor(PropertiesEditingView<Pane> view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Pane parent) {
		combo = new ComboBox<Object>();
		combo.setMaxWidth(Double.MAX_VALUE);
		GridPane.setConstraints(combo, 2, editorRowIndex(), 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
		parent.getChildren().add(combo);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public ComboBox<Object> getViewer() {
		return combo;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo.ComboUIPropertyEditor#initCombo(org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo.ComboUIPropertyEditor.ComboContentProviderInput)
	 */
	public void initCombo(ComboContentProviderInput comboContentProviderInput) {
		Object[] elements = getContentProvider().getElements(comboContentProviderInput);
		combo.getItems().addAll(elements);
	}

	/**
	 * @return the contentProvider
	 */
	private ComboContentProvider getContentProvider() {
		if (contentProvider == null) {
			contentProvider = new ComboContentProvider();
		}
		return contentProvider;
	}

}
