/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.widgets;

import org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FormEReferenceEditor extends EReferenceEditor {

	private FormToolkit toolkit;

	/**
	 * @param toolkit
	 * @param parent
	 * @param style
	 */
	public FormEReferenceEditor(FormToolkit toolkit, Composite parent, int style) {
		super(parent, style);
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor#createControlComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createControlComposite(Composite parent) {
		return toolkit.createComposite(parent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EReferenceEditor#createButton(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Button createButton(Composite control) {
		return toolkit.createButton(control, "", SWT.PUSH);
	}
	
}
