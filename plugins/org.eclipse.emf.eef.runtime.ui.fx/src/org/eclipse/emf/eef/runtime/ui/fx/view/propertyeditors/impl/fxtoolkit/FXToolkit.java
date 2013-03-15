/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit;

import javafx.scene.layout.Pane;

import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.checkbox.CheckboxPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.combo.ComboPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.fx.view.propertyeditors.impl.fxtoolkit.text.TextPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FXToolkit extends ToolkitPropertyEditorProvider<Pane> {

	/**
	 * SWT Toolkit name.
	 */
	public static final String FX_TOOLKIT_NAME = "fx";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
	
	static {
		toolkit.setName(FX_TOOLKIT_NAME);		
	}
	
	/**
	 * 
	 */
	public FXToolkit() {
		addPropertyEditorProvider(new TextPropertyEditorProvider())
		.addPropertyEditorProvider(new CheckboxPropertyEditorProvider())
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
