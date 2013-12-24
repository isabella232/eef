/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor;

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
public class SingleContainmentFormPropertyEditor extends StandardFormPropertyEditor<SingleLinePropertyViewer> {

	private EditUIProvidersFactory editUIProvidersFactory;
	private ImageManager imageManager;
	
	private FormSingleLinePropertyViewer singleContainmentEditor;

	/**
	 * @param editUIProvidersFactory
	 * @param imageManager
	 * @param view
	 * @param elementEditor
	 */
	public SingleContainmentFormPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public FormSingleLinePropertyViewer getViewer() {
		return singleContainmentEditor;
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.impl.StandardFormPropertyEditor#createEditorContents(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(FormToolkit toolkit, Composite parent) {
		singleContainmentEditor = new FormSingleLinePropertyViewer(toolkit, parent, SWT.BORDER);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		singleContainmentEditor.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		singleContainmentEditor.setImageManager(imageManager);
		singleContainmentEditor.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		singleContainmentEditor.getControl().setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormPropertyEditor#lock()
	 */
	@Override
	public void lock() {
		singleContainmentEditor.setLocked(true);
	}

	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormPropertyEditor#unlock()
	 */
	@Override
	public void unlock() {
		singleContainmentEditor.setLocked(false);
	}

}
