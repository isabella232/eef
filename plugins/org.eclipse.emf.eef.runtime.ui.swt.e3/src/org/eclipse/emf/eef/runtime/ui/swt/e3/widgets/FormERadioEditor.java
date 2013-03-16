/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.widgets;

import org.eclipse.emf.eef.runtime.ui.swt.widgets.ERadioEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FormERadioEditor extends ERadioEditor {

	private FormToolkit toolkit;

	/**
	 * @param toolkit
	 * @param parent
	 * @param styles
	 */
	public FormERadioEditor(FormToolkit toolkit, Composite parent, int styles) {
		super(parent, styles);
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.ERadioEditor#createControlComposite(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Composite createControlComposite(Composite parent) {
		return toolkit.createComposite(parent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.ERadioEditor#createRadioButton(org.eclipse.swt.widgets.Composite, java.lang.String)
	 */
	@Override
	protected Button createRadioButton(Composite control, String text) {
		return toolkit.createButton(control, text, SWT.RADIO);
	}
	
	
	
}