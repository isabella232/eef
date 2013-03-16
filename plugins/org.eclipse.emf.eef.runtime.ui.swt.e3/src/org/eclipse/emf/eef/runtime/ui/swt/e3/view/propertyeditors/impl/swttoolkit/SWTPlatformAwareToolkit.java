/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.combo.ComboPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.group.GroupPlatformAwareContainerProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.hbox.HBoxPlatformAwareContainerProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.text.TextPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.textarea.TextareaPlatformAwarePropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.SWTToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTPlatformAwareToolkit extends SWTToolkit {
	
	/**
	 * TODO: Ugly pattern ... need a redesign.
	 * If we don't clear the editorProviders instanciated by the default constructor, the PlatformAware editorProviders
	 * aren't use since the editorProvider selection algorithm (method 
	 *  org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider.getPropertyEditor(PropertyEditorContext)) 
	 *  don't have an ordering system between the different editorProviders.
	 */
	public SWTPlatformAwareToolkit() {
		clearEditorProviders();
		addPropertyEditorProvider(new TextPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new CheckboxPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new GroupPlatformAwareContainerProvider())
		.addPropertyEditorProvider(new HBoxPlatformAwareContainerProvider())
		.addPropertyEditorProvider(new TextareaPlatformAwarePropertyEditorProvider())
		.addPropertyEditorProvider(new ComboPlatformAwarePropertyEditorProvider());
	}

}
