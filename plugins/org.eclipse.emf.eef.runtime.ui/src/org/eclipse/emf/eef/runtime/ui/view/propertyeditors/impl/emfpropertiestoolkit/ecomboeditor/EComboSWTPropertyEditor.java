/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.widgets.EComboEditor;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboSWTPropertyEditor extends StandardSWTPropertyEditor<EComboEditor> {

	private EComboEditor eComboEditor;

	/**
	 * @param view
	 * @param elementEditor
	 */
	public EComboSWTPropertyEditor(PropertiesEditingView view, ElementEditor elementEditor) {
		super(view, elementEditor);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public EComboEditor getViewer() {
		return eComboEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(Composite parent) {
		eComboEditor = new EComboEditor(parent, SWT.BORDER);
		eComboEditor.setLabelProvider(new AdapterFactoryLabelProvider(view.getEditingComponent().getEditingContext().getAdapterFactory()));
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		eComboEditor.setLayoutData(layoutData);
	}

	
	
}
