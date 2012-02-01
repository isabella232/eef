/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.checkbox.CheckboxPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.swttoolkit.text.TextPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SWTToolkitPropertyEditorProvider extends ToolkitPropertyEditorProvider {

	private Toolkit toolkit;

	/**
	 * 
	 */
	public SWTToolkitPropertyEditorProvider() {
		addPropertyEditorProvider(new TextPropertyEditorProvider())
		.addPropertyEditorProvider(new CheckboxPropertyEditorProvider());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#getModel()
	 */
	public Toolkit getModel() {
		if (toolkit == null) {
			toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
			toolkit.setName("swt");
		}
		return toolkit;
	}

}
