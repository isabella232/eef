/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FXPropertyEditorViewer<CONTROL extends Node> extends PropertyEditorViewer<CONTROL> {
	
	/**
	 * Build the PropertyEditor in the given container.
	 * @param parent {@link Pane} where to build the PropertyEditor.
	 */
	void build(Pane parent);

}
