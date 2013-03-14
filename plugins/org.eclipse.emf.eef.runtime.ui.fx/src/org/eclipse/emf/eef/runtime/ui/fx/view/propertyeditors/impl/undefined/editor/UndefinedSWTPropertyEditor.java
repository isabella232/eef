/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.undefined.editor;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.FXPropertyEditorViewer;
import org.eclipse.emf.eef.views.ViewElement;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.Widget;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UndefinedSWTPropertyEditor implements FXPropertyEditorViewer<Label> {

	private ViewElement viewElement;

	private Label label;

	/**
	 * @param viewElement
	 */
	public UndefinedSWTPropertyEditor(ViewElement viewElement) {
		this.viewElement = viewElement;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.SWTPropertyEditor#build(org.eclipse.swt.widgets.Composite)
	 */
	public void build(Pane parent) {
		label = new Label(buildErrorMessage(viewElement));
		label.setText(buildErrorMessage(viewElement));
		GridPane.setColumnSpan(label, 3);
	}

	public Label getViewer() {
		return label;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#lock()
	 */
	public void lock() {
		//Do nothing.
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#unlock()
	 */
	public void unlock() {
		//Do nothing.
	}
	
	protected String buildErrorMessage(ViewElement element) {
		StringBuilder sb = new StringBuilder("Unable to provide a PropertyEditor for editor ").append(element.getName()).append(", ");
		if (element.getRepresentation() == null) {
			sb.append("no representation is defined");
		} else {
			Widget representation = element.getRepresentation();
			if (representation.getName() != null) {
				sb.append("no PropertyEditorProvider can handle a editor using the widget ").append(representation.getName()).append('.');
			} else {
				sb.append("no PropertyEditorProvider can handle the widget associated to this editor");
			}
			if (representation.eContainer() instanceof Toolkit) {
				Toolkit tk = (Toolkit) representation.eContainer();
				if (tk.getName() != null) {
					sb.append("(toolkit: ").append(tk.getName()).append(')');
				}
			}
		}
		sb.append('.');
		String text = sb.toString();
		return text;
	}

	
}
