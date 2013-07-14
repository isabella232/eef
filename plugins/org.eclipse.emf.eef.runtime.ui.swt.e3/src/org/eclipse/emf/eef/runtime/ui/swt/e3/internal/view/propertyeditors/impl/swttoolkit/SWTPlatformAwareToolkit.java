/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.combo.ComboPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.group.GroupPlatformAwareContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.hbox.HBoxPlatformAwareContainerFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.text.TextPlatformAwarePropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.swttoolkit.textarea.TextareaPlatformAwarePropertyEditorFactory;
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
	 *  org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl.getPropertyEditor(PropertyEditorContext)) 
	 *  don't have an ordering system between the different editorProviders.
	 */
	public SWTPlatformAwareToolkit() {
		clearEditorProviders();
		addPropertyEditorFactory(new TextPlatformAwarePropertyEditorFactory())
		.addPropertyEditorFactory(new CheckboxPlatformAwarePropertyEditorFactory())
		.addPropertyEditorFactory(new GroupPlatformAwareContainerFactory())
		.addPropertyEditorFactory(new HBoxPlatformAwareContainerFactory())
		.addPropertyEditorFactory(new TextareaPlatformAwarePropertyEditorFactory())
		.addPropertyEditorFactory(new ComboPlatformAwarePropertyEditorFactory(this));
	}

}
