/**
 * 
 */
package org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.emfpropertiestoolkit.singlecontainmenteditor;

import org.eclipse.emf.eef.runtime.context.PropertiesEditingContext;
import org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.StandardSWTPropertyEditor;
import org.eclipse.emf.eef.runtime.ui.swt.internal.widgets.SingleLinePropertyViewer;
import org.eclipse.emf.eef.runtime.ui.swt.resources.ImageManager;
import org.eclipse.emf.eef.runtime.ui.swt.viewer.EditUIProvidersFactory;
import org.eclipse.emf.eef.runtime.ui.view.PropertiesEditingView;
import org.eclipse.emf.eef.views.ElementEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:goulwen.lefur@obeo.fr">Goulwen Le Fur</a>
 *
 */
public class SingleContainmentSWTPropertyEditor extends StandardSWTPropertyEditor<SingleLinePropertyViewer> {

	private EditUIProvidersFactory editUIProvidersFactory;
	private ImageManager imageManager;

	private SingleLinePropertyViewer singleContainmentComboEditor;

	/**
	 * @param view
	 * @param elementEditor
	 * @param imageManager
	 */
	public SingleContainmentSWTPropertyEditor(EditUIProvidersFactory editUIProvidersFactory, ImageManager imageManager, PropertiesEditingView<Composite> view, ElementEditor elementEditor) {
		super(view, elementEditor);
		this.editUIProvidersFactory = editUIProvidersFactory;
		this.imageManager = imageManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.view.propertyeditors.PropertyEditorViewer#getViewer()
	 */
	public SingleLinePropertyViewer getViewer() {
		return singleContainmentComboEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.internal.view.propertyeditors.impl.StandardSWTPropertyEditor#createEditorContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createEditorContents(Composite parent) {
		singleContainmentComboEditor = new SingleLinePropertyViewer(parent, SWT.BORDER);
		PropertiesEditingContext editingContext = view.getEditingComponent().getEditingContext();
		singleContainmentComboEditor.setLabelProvider(editUIProvidersFactory.createLabelProvider(editingContext.getAdapterFactory()));
		singleContainmentComboEditor.setImageManager(imageManager);
		singleContainmentComboEditor.createContents();
		GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
		singleContainmentComboEditor.getControl().setLayoutData(layoutData);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormPropertyEditor#lock()
	 */
	@Override
	public void lock() {
		singleContainmentComboEditor.setLocked(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.ui.swt.e3.internal.view.propertyeditors.impl.StandardFormPropertyEditor#unlock()
	 */
	@Override
	public void unlock() {
		singleContainmentComboEditor.setLocked(false);
	}
}
