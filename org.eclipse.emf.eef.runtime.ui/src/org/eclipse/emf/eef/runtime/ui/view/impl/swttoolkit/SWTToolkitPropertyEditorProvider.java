/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.impl.swttoolkit;

import org.eclipse.emf.eef.runtime.ui.view.impl.swttoolkit.checkbox.CheckboxPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.impl.swttoolkit.text.TextPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.ToolkitPropertyEditorProvider;
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
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditor.providers.ModelPropertyEditorProvider#getModel()
	 */
	public Toolkit getModel() {
		if (toolkit == null) {
			toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
			toolkit.setName("swt");
		}
		return toolkit;
	}

}
