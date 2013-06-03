/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.swttoolkit.combo;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.StandardFormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.services.view.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComboFormPropertyEditor extends StandardFormPropertyEditor<ComboViewer> implements ComboUIPropertyEditor {

	private EditUIProvidersFactory editUIProvidersFactory;
	
	protected ComboViewer combo;

	/**
	 * @param editUIProvidersFactory
	 * @param view
	 * @param elementEditor
	 */
	public ComboFormPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.view.propertyeditors.impl.StandardFormPropertyEditor#createEditorContents(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(FormToolkit toolkit, Composite parent) {
		Control comboControl;
		if (toolkit.getBorderStyle() == SWT.BORDER) {
			comboControl = new Combo(parent, SWT.BORDER | SWT.DROP_DOWN);
			combo = new ComboViewer((Combo)comboControl);
		} else {
			comboControl = new CCombo(parent, SWT.FLAT | SWT.DROP_DOWN);
			combo = new ComboViewer((CCombo)comboControl);
		}
		toolkit.adapt(comboControl, true, false);
		GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
		comboControl.setLayoutData(comboData);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		combo.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		combo.setContentProvider(new ComboContentProvider());
		if (view.getViewService() instanceof SWTViewService) {
			((SWTViewService) view.getViewService()).setID(comboControl, elementEditor.getQualifiedIdentifier());
			((SWTViewService) view.getViewService()).setEEFtype(comboControl, "eef::Combo"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor#initCombo(org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor.ComboContentProviderInput)
	 */
	public void initCombo(ComboContentProviderInput input) {
		combo.setInput(input);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public ComboViewer getViewer() {
		return combo;
	}

}
