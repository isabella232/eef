/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.combo;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.services.view.ViewService;
import org.eclipse.emf.eef.runtime.ui.swt.services.view.SWTViewService;
import org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class ComboSWTPropertyEditor extends StandardSWTPropertyEditor<ComboViewer> implements ComboUIPropertyEditor {

	private EditUIProvidersFactory editUIProvidersFactory;
	protected ComboViewer combo;

	/**
	 * @param view
	 * @param viewElement
	 */
	public ComboSWTPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	protected void createEditorContents(Composite parent) {
		combo = new ComboViewer(parent, SWT.DROP_DOWN);
		GridData comboData = new GridData(GridData.FILL_HORIZONTAL);
		combo.getCombo().setLayoutData(comboData);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		combo.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		combo.setContentProvider(new ComboContentProvider());
		ViewService viewService = view.getViewService();
		if (viewService instanceof SWTViewService) {
			SWTViewService swtViewService = (SWTViewService)viewService;
			swtViewService.setID(combo.getCombo(), elementEditor.getQualifiedIdentifier());
			swtViewService.setEEFtype(combo.getCombo(), "eef::Combo"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public ComboViewer getViewer() {
		return combo;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor#initCombo(org.eclipse.emf.eef.runtime.ui.swt.view.propertyeditors.impl.swttoolkit.combo.ComboUIPropertyEditor.ComboContentProviderInput)
	 */
	public void initCombo(ComboContentProviderInput input) {
		combo.setInput(input);
	}

}
