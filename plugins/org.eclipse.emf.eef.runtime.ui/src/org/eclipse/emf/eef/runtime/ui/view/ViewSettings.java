/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view;

import org.eclipse.emf.eef.runtime.ui.widgets.util.SelectionMode;
import org.eclipse.swt.graphics.Image;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public interface ViewSettings {

	/**
	 * @return the default MultiEditor height
	 */
	int getMultiEditorHeight();
	
	/**
	 * @return the default EEF selection dialogs height 
	 */
	int getEEFSelectionDialogHeight();
	
	/**
	 * @return the default EEF selection dialogs width 
	 */
	int getEEFSelectionDialogWidth();

	/**
	 * @return the {@link SelectionMode}.
	 */
	SelectionMode getSelectionMode();
	
	/**
	 * @return the default decorator positioning.
	 */
	int getDecoratorPositioning();
	
	/**
	 * @return the {@link Image} to display as error decorator.
	 */
	Image getErrorDecorationImage();

	/**
	 * @return the {@link Image} to display as warning decorator.
	 */
	Image getWarningDecorationImage();

}
