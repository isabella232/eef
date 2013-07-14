/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.ecomboeditor;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.e3.internal.widgets.FormSingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class EComboFormPropertyEditor extends StandardFormPropertyEditor<SingleLinePropertyViewer> {

	private EditUIProvidersFactory editUIProvidersFactory;
	private ImageManager imageManager;
	
	private FormSingleLinePropertyViewer eComboEditor;

	/**
	 * @param editUIProvidersFactory
	 * @param imageManager
	 * @param view
	 * @param elementEditor
	 */
	public EComboFormPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public FormSingleLinePropertyViewer getViewer() {
		return eComboEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardFormPropertyEditor#createEditorContents(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(FormToolkit toolkit, Composite parent) {
		eComboEditor = new FormSingleLinePropertyViewer(toolkit, parent, SWT.BORDER);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		eComboEditor.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		eComboEditor.setImageManager(imageManager);
		eComboEditor.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		eComboEditor.getControl().setLayoutData(layoutData);
	}


}
