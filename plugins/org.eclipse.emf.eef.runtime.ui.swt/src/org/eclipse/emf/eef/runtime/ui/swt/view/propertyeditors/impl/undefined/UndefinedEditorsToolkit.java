/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.undefined;

import org.eclipse.emf.eef.runtime.services.DefaultService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.undefined.editor.UndefinedPropertyEditorFactory;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.EEFToolkitImpl;
import org.eclipse.emf.eef.views.toolkits.Toolkit;
import org.eclipse.emf.eef.views.toolkits.ToolkitsFactory;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class UndefinedEditorsToolkit extends EEFToolkitImpl<Composite> implements DefaultService {

	/**
	 * Undefined Editors Toolkit name.
	 */
	public static final String UNDEFINED_EDITORS_TOOLKIT_NAME = "undefined editors";
	
	private static final Toolkit toolkit = ToolkitsFactory.eINSTANCE.createToolkit();
	
	static {
		toolkit.setName(UNDEFINED_EDITORS_TOOLKIT_NAME);
	}

	/**
	 * 
	 */
	public UndefinedEditorsToolkit() {
		addPropertyEditorFactory(new UndefinedPropertyEditorFactory());
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.ModelPropertyEditorFactory#getModel()
	 */
	public Toolkit getModel() {
		return toolkit;
	}

}
