/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.group.GroupPlatformAwareContainerProvider;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.hbox.HBoxPlatformAwareContainerProvider;
import org.eclipse.emf.eef.runtime.ui.platform.view.propertyeditors.impl.swttoolkit.text.TextPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.SWTToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTPlatformAwareToolkit extends SWTToolkit {
	
	/**
	 * 
	 */
	public SWTPlatformAwareToolkit() {
		addPropertyEditorProvider(new TextPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new CheckboxPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new GroupPlatformAwareContainerProvider())
		.addPropertyEditorProvider(new HBoxPlatformAwareContainerProvider());
	}

}
