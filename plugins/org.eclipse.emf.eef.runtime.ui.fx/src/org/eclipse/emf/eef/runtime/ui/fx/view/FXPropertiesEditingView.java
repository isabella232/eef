/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface FXPropertiesEditingView {

	/**
	 * Creates the contents of the view in the given container.
	 * @param container owning {@link BorderPane}
	 */
	void createContents(Pane container);
	
}
