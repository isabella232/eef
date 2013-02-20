/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.undefined;

import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.undefined.editor.UndefinedPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.undefined.UndefinedEditorsToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UndefinedEditorsPlatformAwareToolkit extends UndefinedEditorsToolkit {

	/**
	 * 
	 */
	public UndefinedEditorsPlatformAwareToolkit() {
		addPropertyEditorProvider(new UndefinedPlatformAwarePropertyEditorProvider());
	}

}
