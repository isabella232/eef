/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.checkbox.CheckboxPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.combo.ComboPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.group.GroupContainerProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.hbox.HBoxContainerProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.text.TextPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.textarea.TextareaPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTToolkit extends ToolkitPropertyEditorProvider<Composite> {

	/**
	 * SWT Toolkit name.
	 */
	public static final String SWT_TOOLKIT_NAME = "swt";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
	
	static {
		toolkit.setName(SWT_TOOLKIT_NAME);		
	}
	
	/**
	 * 
	 */
	public SWTToolkit() {
		addPropertyEditorProvider(new TextPropertyEditorProvider())
		.addPropertyEditorProvider(new CheckboxPropertyEditorProvider())
		.addPropertyEditorProvider(new GroupContainerProvider())
		.addPropertyEditorProvider(new HBoxContainerProvider())
		.addPropertyEditorProvider(new TextareaPropertyEditorProvider())
		.addPropertyEditorProvider(new ComboPropertyEditorProvider());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#getModel()
	 */
	public Toolkit getModel() {
		return toolkit;
	}

}
