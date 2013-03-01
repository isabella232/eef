/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.platform.widgets;

import org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class FormEComboEditor extends EComboEditor {

	private FormToolkit toolkit;

	/**
	 * @param toolkit
	 * @param parent
	 * @param styles
	 */
	public FormEComboEditor(FormToolkit toolkit, Composite parent, int styles) {
		super(parent, styles);
		this.toolkit = toolkit;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor#createControlComposite(org.eclipse.swt.widgets.Composite)
	 */
	protected Composite createControlComposite(Composite parent) {
		return toolkit.createComposite(parent);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor#createText(org.eclipse.swt.widgets.Composite, int)
	 */
	protected Text createText(Composite control, int styles) {
		Text result = toolkit.createText(control, "", SWT.NONE);
		result.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		toolkit.paintBordersFor(control);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor#createSetButton(org.eclipse.swt.widgets.Composite)
	 */
	protected Button createSetButton(Composite control) {
		return toolkit.createButton(control, "...", SWT.PUSH);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor#createClearButton(org.eclipse.swt.widgets.Composite)
	 */
	protected Button createClearButton(Composite control) {
		return toolkit.createButton(control, "", SWT.PUSH);
	}
	
	
	
}