/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit;

import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.emfpropertiestoolkit.ereferenceeditor.EReferenceMultiPropertyEditorProvider;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.ToolkitPropertyEditorProvider;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EMFPropertiesToolkit extends ToolkitPropertyEditorProvider {

	/**
	 * EMFProperties toolkit name.
	 */
	public static final String EMF_PROPERTIES = "EMFProperties";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit(); 
	static {
		toolkit.setName(EMF_PROPERTIES);
	}
	
	/**
	 * 
	 */
	public EMFPropertiesToolkit() {
		addPropertyEditorProvider(new EReferenceMultiPropertyEditorProvider());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorProvider#getModel()
	 */
	public Toolkit getModel() {
		return toolkit;
	}

}
