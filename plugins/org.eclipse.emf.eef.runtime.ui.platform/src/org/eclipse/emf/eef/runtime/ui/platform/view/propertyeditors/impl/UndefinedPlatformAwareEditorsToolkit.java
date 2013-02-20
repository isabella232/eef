/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl;

import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.undefined.editor.UndefinedPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.undefined.UndefinedEditorsToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UndefinedPlatformAwareEditorsToolkit extends UndefinedEditorsToolkit {

	/**
	 * 
	 */
	public UndefinedPlatformAwareEditorsToolkit() {
		addPropertyEditorProvider(new UndefinedPlatformAwarePropertyEditorProvider());
	}

}
